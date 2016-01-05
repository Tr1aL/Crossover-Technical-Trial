package com.dev.backend.dao;

import com.dev.backend.config.TestConfig;
import com.dev.backend.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CustomerDAOTest {

    @Autowired
    private CustomerDAO customerDAO;

    @Test
    public void testCreate() throws Exception {
        Customer customer = new Customer();
        customer.setCode("code_1");
        customerDAO.create(customer);
        assertEquals(customerDAO.get(customer.getCode()).getCode(), "code_1");
    }

    @Test
    public void testUpdate() throws Exception {
        Customer customer = new Customer();
        customer.setCode("code_2");
        customerDAO.create(customer);
        customer = customerDAO.get(customer.getCode());
        customer.setPhone1("+79998887766");
        customerDAO.update(customer);
        assertEquals(customerDAO.get(customer.getCode()).getCode(), "code_2");
        assertEquals(customerDAO.get(customer.getCode()).getPhone1(), "+79998887766");
    }

    @Test
    public void testUpdateCurrentCredit() throws Exception {
        Customer customer = new Customer();
        customer.setCode("code_3");
        customerDAO.create(customer);
        customerDAO.updateCurrentCredit(customer.getCode(), 1000.);
        assertEquals(customerDAO.get(customer.getCode()).getCode(), "code_3");
        assertEquals(String.valueOf(customerDAO.get(customer.getCode()).getCurrentCredit()), String.valueOf(1000.));
    }

    @Test
    public void testDelete() throws Exception {
        Customer customer = new Customer();
        customer.setCode("code_4");
        customerDAO.create(customer);
        customerDAO.delete(customer.getCode());
        assertNull(customerDAO.get(customer.getCode()));
    }

    @Test
    public void testGet() throws Exception {
        Customer customer = new Customer();
        customer.setCode("code_5");
        customerDAO.create(customer);
        assertNotNull(customerDAO.get(customer.getCode()));
    }

    @Test
    public void testGetAll() throws Exception {
        Customer customer = new Customer();
        customer.setCode("code_6");
        customerDAO.create(customer);
        customer = new Customer();
        customer.setCode("code_7");
        customerDAO.create(customer);
        assertTrue(customerDAO.getAll().size() > 1);
    }
}