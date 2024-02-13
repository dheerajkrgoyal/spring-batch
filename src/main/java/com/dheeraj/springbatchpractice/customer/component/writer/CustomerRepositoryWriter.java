package com.dheeraj.springbatchpractice.customer.component.writer;

import com.dheeraj.springbatchpractice.customer.entity.Customer;
import com.dheeraj.springbatchpractice.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerRepositoryWriter  implements ItemWriter<Customer> {

    private CustomerRepository customerRepository;

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        System.out.println("Current thread: " + Thread.currentThread().getName());
        customerRepository.saveAll(chunk);
    }
}
