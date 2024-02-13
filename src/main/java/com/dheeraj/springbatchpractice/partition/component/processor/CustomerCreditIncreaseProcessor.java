package com.dheeraj.springbatchpractice.partition.component.processor;

import com.dheeraj.springbatchpractice.partition.entity.CustomerCredit;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreditIncreaseProcessor implements ItemProcessor<CustomerCredit, CustomerCredit> {

    @Override
    public CustomerCredit process(CustomerCredit item) throws Exception {
        item.setCredit(item.getCredit()+5);
        return item;
    }
}
