package com.dev.backend.service.impl;

import com.dev.backend.dao.CustomerDAO;
import com.dev.backend.model.Customer;
import com.dev.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Customer service implementation
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    /**
     * Save if customer exists or create if not exists
     *
     * @param obj customer
     * @return customer
     */
    @Override
    public Customer save(Customer obj) {
        Customer oldObj = customerDAO.get(obj.getCode());
        if (oldObj == null) {
            customerDAO.create(obj);
        } else {
            obj.setCurrentCredit(obj.getCurrentCredit());
            customerDAO.update(obj);
        }
        return obj;
    }

    /**
     * Delete customer by its code
     *
     * @param code customer code
     */
    @Override
    public void delete(Serializable code) {
        customerDAO.delete(code);
    }

    /**
     * Get customer by its code
     *
     * @param code customer code
     * @return cutomer
     */
    @Override
    public Customer get(Serializable code) {
        return customerDAO.get(code);
    }

    /**
     * Get all customers list
     *
     * @return list of customers
     */
    @Override
    public List<Customer> getAll() {
        return customerDAO.getAll();
    }
}
