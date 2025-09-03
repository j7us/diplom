package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutionService {
    private final JobLauncher jobLauncher;
    private final Job enterpriseCsvJob;
    private final Job enterpriseJsonJob;
    private final Job enterpriseJsonExportJob;
    private final Job enterpriseCsvExportJob;
    private final Job vehicleCsvExportJob;

    public void executeEnterpriseCsvJob(String filePath, String username) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addJobParameter("filePath", new JobParameter<>(filePath, String.class))
                .addJobParameter("username", new JobParameter<>(username, String.class))
                        .toJobParameters();

        try {
            jobLauncher.run(enterpriseCsvJob, jobParameters);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void executeEnterpriseJsonJob(String filePath, String username) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addJobParameter("filePath", new JobParameter<>(filePath, String.class))
                .addJobParameter("username", new JobParameter<>(username, String.class))
                .toJobParameters();

        try {
            jobLauncher.run(enterpriseJsonJob, jobParameters);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void exportEnterpriseJsonJob(String filePath, String username) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addJobParameter("filePath", new JobParameter<>(filePath, String.class))
                .addJobParameter("username", new JobParameter<>(username, String.class))
                .toJobParameters();

        try {
            jobLauncher.run(enterpriseJsonExportJob, jobParameters);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void exportEnterpriseCsvJob(String filePath, String username) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addJobParameter("filePath", new JobParameter<>(filePath, String.class))
                .addJobParameter("username", new JobParameter<>(username, String.class))
                .toJobParameters();

        try {
            jobLauncher.run(enterpriseCsvExportJob, jobParameters);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void exportVehicleCsvJob(String filePath, String username) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addJobParameter("filePath", new JobParameter<>(filePath, String.class))
                .addJobParameter("username", new JobParameter<>(username, String.class))
                .toJobParameters();

        try {
            jobLauncher.run(vehicleCsvExportJob, jobParameters);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
