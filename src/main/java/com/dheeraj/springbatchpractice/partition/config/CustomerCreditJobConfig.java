package com.dheeraj.springbatchpractice.partition.config;

import com.dheeraj.springbatchpractice.partition.component.processor.CustomerCreditIncreaseProcessor;
import com.dheeraj.springbatchpractice.partition.component.reader.CustomerCreditItemReader;
import com.dheeraj.springbatchpractice.partition.component.writer.CustomerCreditItemWriter;
import com.dheeraj.springbatchpractice.partition.entity.CustomerCredit;
import com.dheeraj.springbatchpractice.partition.listener.OutputFileListener;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;


@Configuration
@AllArgsConstructor
public class CustomerCreditJobConfig {
    private static final String CUSTOMER_CREDIT_LOAD_STEP_NAME = "customer_credit_load_step";
    private static final String CUSTOMER_CREDIT_MASTER_STEP_NAME = "customer_credit_master_step";
    private static final String CUSTOMER_CREDIT_JOB_NAME = "customer_credit_job";

    private CustomerCreditIncreaseProcessor customerCreditIncreaseProcessor;

    @Bean
    public Job customerCreditLoadJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder(CUSTOMER_CREDIT_JOB_NAME, jobRepository)
                .start(customerCreditMasterStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<CustomerCredit> customerCreditReader(@Value("#{stepExecutionContext[fileName]}") String fileName){
        return CustomerCreditItemReader.customerCreditFlatFileItemReader(fileName);
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<CustomerCredit> customerCreditWriter(@Value("#{stepExecutionContext[outputFile]}") String outputFile){
        return CustomerCreditItemWriter.customerCreditFlatFileItemWriter(outputFile);
    }

    @Bean
    public Step customerCreditLoadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(CUSTOMER_CREDIT_LOAD_STEP_NAME, jobRepository)
                .<CustomerCredit, CustomerCredit>chunk(1, transactionManager)
                .reader(customerCreditReader(null))
                .processor(customerCreditIncreaseProcessor)
                .writer(customerCreditWriter(null))
                .listener(outputFileListener())
                .build();
    }

    @Bean
    public StepExecutionListener outputFileListener(){
        return new OutputFileListener();
    }

    @Bean
    public Step customerCreditMasterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(CUSTOMER_CREDIT_MASTER_STEP_NAME, jobRepository)
                .partitioner(CUSTOMER_CREDIT_LOAD_STEP_NAME, customerCreditPartitioner())
                .partitionHandler(customerCreditPartitionHandler(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Partitioner customerCreditPartitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        partitioner.setKeyName("fileName");
        try {
            partitioner.setResources(resolver.getResources("partition/*.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return partitioner;
    }

    @Bean
    public PartitionHandler customerCreditPartitionHandler(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setGridSize(2);
        partitionHandler.setStep(customerCreditLoadStep(jobRepository, transactionManager));
        partitionHandler.setTaskExecutor(customerCreditTaskExecutor());
        return partitionHandler;
    }

    @Bean
    public TaskExecutor customerCreditTaskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(2);
        return taskExecutor;
    }

}
