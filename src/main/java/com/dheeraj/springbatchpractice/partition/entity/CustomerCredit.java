package com.dheeraj.springbatchpractice.partition.entity;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCredit {
    private String customer;
    private int credit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCredit that = (CustomerCredit) o;
        return getCredit() == that.getCredit() && Objects.equals(getCustomer(), that.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomer(), getCredit());
    }
}
