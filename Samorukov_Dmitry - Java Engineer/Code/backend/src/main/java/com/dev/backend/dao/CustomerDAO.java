package com.dev.backend.dao;

import com.dev.backend.model.Customer;

import java.io.Serializable;
import java.util.List;

public interface CustomerDAO {

    public Serializable create(Customer obj);

    public void update(Customer obj);

    public void updateCurrentCredit(Serializable code, Double currentCredit);

    public void delete(Serializable code);

    public Customer get(Serializable code);

    public List<Customer> getAll();
}
