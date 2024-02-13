package com.dheeraj.springbatchpractice.football.component.writer;

import com.dheeraj.springbatchpractice.football.entity.Player;
import com.dheeraj.springbatchpractice.football.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerItemWriter implements ItemWriter<Player> {

    private PlayerRepository playerRepository;
    @Override
    public void write(Chunk<? extends Player> chunk) throws Exception {
        playerRepository.saveAll(chunk);
    }
}
