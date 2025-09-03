package org.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.example.dto.EnterpriseDto;
import org.example.entity.Enterprise;
import org.example.mapper.EnterpriseMapper;
import org.example.service.EnterpriseService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EnterpriseBatchConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<EnterpriseDto> enterpriseCsvReader(@Value("#{jobParameters['filePath']}") String filePath) {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("name", "city", "productionCapacity", "zone");

        BeanWrapperFieldSetMapper<EnterpriseDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EnterpriseDto.class);

        DefaultLineMapper<EnterpriseDto> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return new FlatFileItemReaderBuilder<EnterpriseDto>()
                .name("enterpriseCsvReader")
                .resource(new FileSystemResource(filePath))
                .linesToSkip(1)
                .lineMapper(lineMapper)
                .build();

    }

    @Bean
    @StepScope
    public JsonItemReader<EnterpriseDto> enterpriseJsonReader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new JsonItemReaderBuilder<EnterpriseDto>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(EnterpriseDto.class))
                .name("enterpriseJsonReader")
                .resource(new FileSystemResource(filePath))
                .build();
    }

    @Bean
    public ItemProcessor<EnterpriseDto, EnterpriseDto> enterpriseProcessor() {
        return item -> item;
    }

    @Bean
    @StepScope
    public ItemWriter<EnterpriseDto> enterpriseDbWriter(EnterpriseService enterpriseService,
                                                        @Value("#{jobParameters['username']}") String username) {
        return item -> item.forEach(
                i -> enterpriseService.saveEnterprise(i, username));
    }

    @Bean
    public Step stepFotCsv(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           ItemReader<EnterpriseDto> enterpriseCsvReader,
                           ItemProcessor<EnterpriseDto, EnterpriseDto> enterpriseProcessor,
                           ItemWriter<EnterpriseDto> enterpriseDbWriter) {
        return new StepBuilder("stepFotCsv", jobRepository)
                .<EnterpriseDto, EnterpriseDto>chunk(10, transactionManager)
                .reader(enterpriseCsvReader)
                .processor(enterpriseProcessor)
                .writer(enterpriseDbWriter)
                .build();
    }

    @Bean
    public Step stepFotJson(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            JsonItemReader<EnterpriseDto> enterpriseJsonReader,
                            ItemProcessor<EnterpriseDto, EnterpriseDto> enterpriseProcessor,
                            ItemWriter<EnterpriseDto> enterpriseDbWriter) {
        return new StepBuilder("stepFotJson", jobRepository)
                .<EnterpriseDto, EnterpriseDto>chunk(10, transactionManager)
                .reader(enterpriseJsonReader)
                .processor(enterpriseProcessor)
                .writer(enterpriseDbWriter)
                .build();
    }

    @Bean
    public Job enterpriseJsonJob(JobRepository jobRepository,
                                 Step stepFotJson) {
        return new JobBuilder("enterpriseJsonJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(stepFotJson)
                .end()
                .build();
    }

    @Bean
    public Job enterpriseCsvJob(JobRepository jobRepository,
                                Step stepFotCsv) {
        return new JobBuilder("enterpriseCsvJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(stepFotCsv)
                .end()
                .build();
    }

    @Bean
    public JpaPagingItemReader<Enterprise> enterpriseDbReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Enterprise>()
                .name("enterpriseCsvReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select e from Enterprise e")
                .build();
    }

    @Bean
    public ItemProcessor<Enterprise, EnterpriseDto> dbToDtoEnterpriseItemProcessor(EnterpriseMapper enterpriseMapper) {
        return enterpriseMapper::toDto;
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<EnterpriseDto> enterpriseJsonWriter(@Value("#{jobParameters['filePath']}") String filePath) {
        return new JsonFileItemWriterBuilder<EnterpriseDto>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(filePath))
                .name("enterpriseJsonWriter")
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<EnterpriseDto> enterpriseCsvWriter(@Value("#{jobParameters['filePath']}") String filePath) {
        FlatFileItemWriter<EnterpriseDto> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(filePath));
        writer.setHeaderCallback(writer1 -> writer1.write("id, name, city, productionCapacity, zone"));
        DelimitedLineAggregator<EnterpriseDto> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<EnterpriseDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "name", "city", "productionCapacity", "zone"});
        lineAggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    @Bean
    public Step stepFotCsvExport(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 JpaPagingItemReader<Enterprise> enterpriseDbReader,
                                 ItemProcessor<Enterprise, EnterpriseDto> dbToDtoEnterpriseItemProcessor,
                                 FlatFileItemWriter<EnterpriseDto> enterpriseCsvWriter) {
        return new StepBuilder("stepFotCsvExport", jobRepository)
                .<Enterprise, EnterpriseDto>chunk(10, transactionManager)
                .reader(enterpriseDbReader)
                .processor(dbToDtoEnterpriseItemProcessor)
                .writer(enterpriseCsvWriter)
                .build();
    }

    @Bean
    public Step stepFotJsonExport(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  JpaPagingItemReader<Enterprise> enterpriseDbReader,
                                  ItemProcessor<Enterprise, EnterpriseDto> dbToDtoEnterpriseItemProcessor,
                                  JsonFileItemWriter<EnterpriseDto> enterpriseJsonWriter) {
        return new StepBuilder("stepFotJsonExport", jobRepository)
                .<Enterprise, EnterpriseDto>chunk(10, transactionManager)
                .reader(enterpriseDbReader)
                .processor(dbToDtoEnterpriseItemProcessor)
                .writer(enterpriseJsonWriter)
                .build();
    }

    @Bean
    public Job enterpriseCsvExportJob(JobRepository jobRepository,
                                       Step stepFotCsvExport) {
        return new JobBuilder("enterpriseCsvExportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(stepFotCsvExport)
                .end()
                .build();
    }

    @Bean
    public Job enterpriseJsonExportJob(JobRepository jobRepository,
                                       Step stepFotJsonExport) {
        return new JobBuilder("enterpriseJsonExportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(stepFotJsonExport)
                .end()
                .build();
    }
}
