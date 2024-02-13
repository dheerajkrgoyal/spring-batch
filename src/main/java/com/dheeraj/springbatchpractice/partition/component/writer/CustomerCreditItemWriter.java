package com.dheeraj.springbatchpractice.partition.component.writer;

import com.dheeraj.springbatchpractice.partition.entity.CustomerCredit;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

public class CustomerCreditItemWriter {
    public static FlatFileItemWriter<CustomerCredit> customerCreditFlatFileItemWriter(String outputFile){
        FlatFileItemWriter<CustomerCredit> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new FileSystemResource(outputFile));

        DelimitedLineAggregator<CustomerCredit> delimitedLineAggregator = new DelimitedLineAggregator<>();
        delimitedLineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<CustomerCredit> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"customer", "credit"});
        delimitedLineAggregator.setFieldExtractor(fieldExtractor);
        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);
        return flatFileItemWriter;
    }
}
