package org.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.example.dto.VehicleDto;
import org.example.entity.Vehicle;
import org.example.mapper.VehicleMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class VehicleBatchConfig {

    @Bean
    public JpaPagingItemReader<Vehicle> vehicleDbReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Vehicle>()
                .name("enterpriseCsvReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("select v from Vehicle v")
                .build();
    }

    @Bean
    public ItemProcessor<Vehicle, VehicleDto> dbToDtoVehicleItemProcessor(VehicleMapper vehicleMapper) {
        return vehicleMapper::toVehicleDto;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<VehicleDto> vehicleCsvWriter(@Value("#{jobParameters['filePath']}") String filePath) {
        FlatFileItemWriter<VehicleDto> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(filePath));
        writer.setHeaderCallback(writer1 -> writer1.write("id, releaseYear, carNumber, mileage, price, brandId, activeDriverId, productionDate"));
        DelimitedLineAggregator<VehicleDto> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<VehicleDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "releaseYear", "carNumber", "mileage", "price", "brandId", "activeDriverId", "productionDate"});
        lineAggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    @Bean
    public Step vehicleStepFotCsvExport(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 JpaPagingItemReader<Vehicle> vehicleDbReader,
                                 ItemProcessor<Vehicle, VehicleDto> dbToDtoVehicleItemProcessor,
                                 FlatFileItemWriter<VehicleDto> vehicleCsvWriter) {
        return new StepBuilder("vehicleStepFotCsvExport", jobRepository)
                .<Vehicle, VehicleDto>chunk(10, transactionManager)
                .reader(vehicleDbReader)
                .processor(dbToDtoVehicleItemProcessor)
                .writer(vehicleCsvWriter)
                .build();
    }

    @Bean
    public Job vehicleCsvExportJob(JobRepository jobRepository,
                                   Step vehicleStepFotCsvExport) {
        return new JobBuilder("vehicleCsvExportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(vehicleStepFotCsvExport)
                .end()
                .build();
    }
}
