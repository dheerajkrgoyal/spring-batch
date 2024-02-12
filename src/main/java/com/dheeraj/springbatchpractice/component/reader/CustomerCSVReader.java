package com.dheeraj.springbatchpractice.component.reader;

import com.dheeraj.springbatchpractice.entity.Customer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class CustomerCSVReader {
    public static FlatFileItemReader<Customer> getCustomerCSVReader(){
        FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(new ClassPathResource("customers.csv")); // set resource

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); //set line tokenizer
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("id","firstName","lastName","email","gender","contactNo","country","dob");
        lineMapper.setLineTokenizer(lineTokenizer);

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>(); //set field set mapper
        fieldSetMapper.setTargetType(Customer.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        flatFileItemReader.setLineMapper(lineMapper); //set line mapper
        return flatFileItemReader;
    }
}
