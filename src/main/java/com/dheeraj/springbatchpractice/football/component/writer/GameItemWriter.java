package com.dheeraj.springbatchpractice.football.component.writer;

import com.dheeraj.springbatchpractice.football.entity.Game;
import com.dheeraj.springbatchpractice.football.entity.Player;
import com.dheeraj.springbatchpractice.football.repository.GameRepository;
import com.dheeraj.springbatchpractice.football.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameItemWriter implements ItemWriter<Game> {
    private GameRepository gameRepository;
    @Override
    public void write(Chunk<? extends Game> chunk) throws Exception {
        gameRepository.saveAll(chunk);
    }
}
