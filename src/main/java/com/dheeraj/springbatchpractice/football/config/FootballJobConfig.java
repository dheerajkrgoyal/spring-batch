package com.dheeraj.springbatchpractice.football.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@AllArgsConstructor
public class FootballJobConfig {
    private static final String JOB_NAME = "football_job";
    private static final String PLAYER_LOAD_FLOW_NAME = "player_load_flow";
    private static final String GAME_LOAD_FLOW_NAME = "game_load_flow";
    private static final String DATA_LOAD_FLOW_NAME = "data_load_flow";

    private Step playerLoadStep;
    private Step gameLoadStep;
    private Step playerSummaryStep;
    @Bean
    public Job footballJob(JobRepository jobRepository){
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(dataLoadFlow())
                .next(playerSummaryStep)
                .build()
                .build();
    }

    @Bean
    public Flow dataLoadFlow(){
        return new FlowBuilder<SimpleFlow>(DATA_LOAD_FLOW_NAME)
                .split(dataLoadTaskExecutor())
                .add(playerLoadFlow(), gameLoadFlow())
                .build();
    }

    @Bean
    public TaskExecutor dataLoadTaskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    @Bean
    public Flow playerLoadFlow(){
        return new FlowBuilder<SimpleFlow>(PLAYER_LOAD_FLOW_NAME)
                .start(playerLoadStep)
                .build();
    }

    @Bean
    public Flow gameLoadFlow(){
        return new FlowBuilder<SimpleFlow>(GAME_LOAD_FLOW_NAME)
                .start(gameLoadStep)
                .build();
    }
}
