package com.dheeraj.springbatchpractice.football.component.reader;

import com.dheeraj.springbatchpractice.football.entity.Player;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class PlayerItemReader {

    public static FlatFileItemReader<Player> getPlayerItemReader(){
        FlatFileItemReader<Player> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setResource(new ClassPathResource("football/players.csv"));

        DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("id", "lastName", "firstName", "position", "birthYear", "debutYear");
        lineMapper.setLineTokenizer(lineTokenizer);

        BeanWrapperFieldSetMapper<Player> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Player.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        fileItemReader.setLineMapper(lineMapper);
        return fileItemReader;
    }
}
