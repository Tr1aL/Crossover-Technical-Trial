package com.dev.backend.dao.stub;

import com.dev.backend.dao.CustomerDAO;
import com.dev.backend.model.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAOStub implements CustomerDAO {

    private Map<Serializable, Customer> DB = new HashMap<>();

    @Override
    public synchronized Serializable create(Customer obj) {
        DB.put(obj.getCode(), obj);
        return obj.getCode();
    }

    @Override
    public synchronized void update(Customer obj) {
        DB.put(obj.getCode(), obj);
    }

    @Override
    public synchronized void updateCurrentCredit(Serializable code, Double currentCredit) {
        Customer customer = DB.get(code);
        if (customer != null) {
            customer.setCurrentCredit(currentCredit);
            DB.put(code, customer);
        }
    }

    @Override
    public synchronized void delete(Serializable code) {
        DB.remove(code);
    }

    @Override
    public synchronized Customer get(Serializable code) {
        return DB.get(code);
    }

    @Override
    public synchronized List<Customer> getAll() {
        return new ArrayList<>(DB.values());
    }
}
