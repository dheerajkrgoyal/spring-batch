package com.dheeraj.springbatchpractice.football.config;

import com.dheeraj.springbatchpractice.football.component.reader.PlayerItemReader;
import com.dheeraj.springbatchpractice.football.component.writer.PlayerItemWriter;
import com.dheeraj.springbatchpractice.football.entity.Player;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class PlayerLoadStepConfig {

    private static final String STEP_NAME = "player_load_step";
    PlayerItemWriter playerItemWriter;
    @Bean
    public FlatFileItemReader<Player> playerFlatFileItemReader() {
        return PlayerItemReader.getPlayerItemReader();
    }

    @Bean
    public PlayerItemWriter playerWriter(){
        return playerItemWriter;
    }

    @Bean
    public Step playerLoadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Player, Player>chunk(10, transactionManager)
                .reader(playerFlatFileItemReader())
                .writer(playerWriter())
                .build();
    }

}
