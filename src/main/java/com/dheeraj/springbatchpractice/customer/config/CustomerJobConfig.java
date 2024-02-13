package com.dheeraj.springbatchpractice.customer.config;

import com.dheeraj.springbatchpractice.customer.component.processor.CustomerProcessor;
import com.dheeraj.springbatchpractice.customer.component.reader.CustomerCSVReader;
import com.dheeraj.springbatchpractice.customer.component.writer.CustomerRepositoryWriter;
import com.dheeraj.springbatchpractice.customer.entity.Customer;
import com.dheeraj.springbatchpractice.customer.partition.CustomerPartitioner;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class CustomerJobConfig {

    private static final String SLAVE_STEP_NAME = "customer_step";
    private static final String MASTER_STEP_NAME = "master_customer_step";
    private static final String JOB_NAME = "customer_job";

    private CustomerRepositoryWriter customerRepositoryWriter;

    @Bean
    public FlatFileItemReader<Customer> customerItemReader(){
        return CustomerCSVReader.getCustomerCSVReader();
    }

    @Bean
    public CustomerProcessor customerProcessor(){
        return new CustomerProcessor();
    }

    @Bean
    public CustomerRepositoryWriter customerItemWriter(){
        return customerRepositoryWriter;
    }

    @Bean
    public CustomerPartitioner customerPartitioner(){
        return new CustomerPartitioner();
    }

    @Bean
    public PartitionHandler partitionHandler(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setGridSize(2);
        partitionHandler.setStep(customerStep(jobRepository, transactionManager));
        partitionHandler.setTaskExecutor(taskExecutor());
        return partitionHandler;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setQueueCapacity(4);
        return taskExecutor;
    }

    @Bean
    public Step customerMasterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(MASTER_STEP_NAME, jobRepository)
                .partitioner(SLAVE_STEP_NAME, customerPartitioner())
                .partitionHandler(partitionHandler(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step customerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(SLAVE_STEP_NAME, jobRepository)
                .<Customer, Customer>chunk(500, transactionManager)
                .reader(customerItemReader())
                .processor(customerProcessor())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public Job customerJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(customerMasterStep(jobRepository, transactionManager))
                .build();
    }
}
