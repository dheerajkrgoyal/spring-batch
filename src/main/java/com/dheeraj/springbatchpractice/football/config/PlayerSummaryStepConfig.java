package com.dheeraj.springbatchpractice.football.config;

import com.dheeraj.springbatchpractice.football.component.writer.PlayerSummaryItemWriter;
import com.dheeraj.springbatchpractice.football.entity.PlayerSummary;
import com.dheeraj.springbatchpractice.football.repository.PlayerSummaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class PlayerSummaryStepConfig {

    private static final String STEP_NAME = "player_summary_step";
    PlayerSummaryItemWriter playerSummaryItemWriter;
    PlayerSummaryRepository playerSummaryRepository;

    @Bean
    public Step playerSummaryStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PlayerSummary, PlayerSummary>chunk(10, transactionManager)
                .reader(playerSummaryItemReader())
                .writer(playerSummaryItemWriter)
                .taskExecutor(playerSummaryTaskExecutor())
                .build();
    }

    @Bean
    public RepositoryItemReader<PlayerSummary> playerSummaryItemReader(){
        return new RepositoryItemReaderBuilder<PlayerSummary>()
                .name("playerSummaryItemReader")
                .repository(playerSummaryRepository)
                .methodName("getPlayerSummaryReport")
                .sorts(Map.of("playerId", Sort.Direction.ASC, "gameYear", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public TaskExecutor playerSummaryTaskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
}
