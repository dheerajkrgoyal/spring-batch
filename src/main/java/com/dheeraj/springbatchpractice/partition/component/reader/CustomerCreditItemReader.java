package com.dheeraj.springbatchpractice.partition.component.reader;

import com.dheeraj.springbatchpractice.partition.entity.CustomerCredit;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

public class CustomerCreditItemReader {
    public static FlatFileItemReader<CustomerCredit> customerCreditFlatFileItemReader(String fileName) {
        FlatFileItemReader<CustomerCredit> flatFileItemReader = new FlatFileItemReader<>();
        try {
            flatFileItemReader.setResource(new UrlResource(fileName));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        DefaultLineMapper<CustomerCredit> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("customer", "credit");
        defaultLineMapper.setLineTokenizer(lineTokenizer);

        BeanWrapperFieldSetMapper<CustomerCredit> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CustomerCredit.class);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }
}
