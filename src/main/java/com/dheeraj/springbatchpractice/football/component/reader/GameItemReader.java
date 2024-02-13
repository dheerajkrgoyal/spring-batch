package com.dheeraj.springbatchpractice.football.component.reader;

import com.dheeraj.springbatchpractice.football.component.fieldsetmapper.GameFieldSetMapper;
import com.dheeraj.springbatchpractice.football.entity.Game;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class GameItemReader {

    public static FlatFileItemReader<Game> getGameItemReader(){
        FlatFileItemReader<Game> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setResource(new ClassPathResource("football/games.csv"));

        DefaultLineMapper<Game> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("playerId", "gameYear", "team", "gameWeek", "opponent", "completes", "attempts", "passingYards", "passingTd",
                "interceptions", "rushes", "rushYards", "receptions", "receptionYards", "totalTd");
        lineMapper.setLineTokenizer(lineTokenizer);


        lineMapper.setFieldSetMapper(new GameFieldSetMapper());

        fileItemReader.setLineMapper(lineMapper);
        return fileItemReader;
    }
}
