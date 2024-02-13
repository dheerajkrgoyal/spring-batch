package com.dheeraj.springbatchpractice.football.config;

import com.dheeraj.springbatchpractice.football.component.reader.GameItemReader;
import com.dheeraj.springbatchpractice.football.component.writer.GameItemWriter;
import com.dheeraj.springbatchpractice.football.entity.Game;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
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
public class GameLoadStepConfig {
    private static final String STEP_NAME = "game_load_step";
    private GameItemWriter gameItemWriter;

    @Bean
    public FlatFileItemReader<Game> gameItemReader(){
        return GameItemReader.getGameItemReader();
    }

    @Bean
    public GameItemWriter gameRepositoryWriter(){
        return gameItemWriter;
    }

    @Bean
    public TaskExecutor gameLoadTaskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    @Bean
    public Step gameLoadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Game, Game>chunk(20, transactionManager)
                .reader(gameItemReader())
                .writer(gameRepositoryWriter())
                .taskExecutor(gameLoadTaskExecutor())
                .build();
    }
}
