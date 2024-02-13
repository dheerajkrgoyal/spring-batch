package com.dheeraj.springbatchpractice.customer.component.processor;

import com.dheeraj.springbatchpractice.customer.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        return item;
    }
}
