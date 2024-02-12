package com.dheeraj.springbatchpractice.config;

import com.dheeraj.springbatchpractice.component.processor.CustomerProcessor;
import com.dheeraj.springbatchpractice.component.reader.CustomerCSVReader;
import com.dheeraj.springbatchpractice.component.writer.CustomerRepositoryWriter;
import com.dheeraj.springbatchpractice.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class CustomerJobConfig {

    private static final String STEP_NAME = "customer_step";
    private static final String JOB_NAME = "customer_job";

    private CustomerRepositoryWriter writer;

    @Bean
    public FlatFileItemReader<Customer> reader(){
        return CustomerCSVReader.getCustomerCSVReader();
    }

    @Bean
    public CustomerProcessor processor(){
        return new CustomerProcessor();
    }

    @Bean
    public CustomerRepositoryWriter writer(){
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    @Bean
    public Step customerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job customerJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(customerStep(jobRepository, transactionManager))
                .build();
    }
}
