package com.dheeraj.springbatchpractice.customer.repository;

import com.dheeraj.springbatchpractice.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
