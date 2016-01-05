package com.dev.backend.service;

import com.dev.backend.model.Customer;

import java.io.Serializable;
import java.util.List;

public interface CustomerService {

    public Customer save(Customer obj);

    public void delete(Serializable code);

    public Customer get(Serializable code);

    public List<Customer> getAll();
}