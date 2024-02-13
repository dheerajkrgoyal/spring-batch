package com.dheeraj.springbatchpractice.football.component.writer;

import com.dheeraj.springbatchpractice.football.entity.PlayerSummary;
import com.dheeraj.springbatchpractice.football.repository.PlayerSummaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerSummaryItemWriter implements ItemWriter<PlayerSummary> {
    private PlayerSummaryRepository playerSummaryRepository;


    @Override
    public void write(Chunk<? extends PlayerSummary> chunk) throws Exception {
        playerSummaryRepository.saveAll(chunk);
    }
}
