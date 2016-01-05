package com.dev.backend.service;

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
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testSave() throws Exception {
        Customer customer = new Customer();
        customer.setCode("user_1");
        customer.setPhone1("+79998887766");
        customer.setCreditLimit(1000);
        customerService.save(customer);
        customer = customerService.get("user_1");
        assertEquals(customer.getPhone1(), "+79998887766");
        customer.setCreditLimit(1500);
        customerService.save(customer);
        customer = customerService.get("user_1");
        assertEquals(String.valueOf(customer.getCreditLimit()), String.valueOf(1500.));
    }

    @Test
    public void testDelete() throws Exception {
        Customer customer = new Customer();
        customer.setCode("user_1");
        customer.setPhone1("+79998887766");
        customer.setCreditLimit(1000);
        customerService.save(customer);
        customer = customerService.get("user_1");
        assertNotNull(customer);
        customerService.delete(customer.getCode());
        customer = customerService.get("user_1");
        assertNull(customer);
    }

    @Test
    public void testGet() throws Exception {
        Customer customer = new Customer();
        customer.setCode("user_1");
        customer.setPhone1("+79998887766");
        customer.setCreditLimit(1000);
        customerService.save(customer);
        customer = customerService.get("user_1");
        assertNotNull(customer);
    }

    @Test
    public void testGetAll() throws Exception {
        Customer customer = new Customer();
        customer.setCode("user_2");
        customer.setPhone1("+7999888755");
        customer.setCreditLimit(2000);
        customerService.save(customer);
        customer = new Customer();
        customer.setCode("user_3");
        customer.setPhone1("+7999888744");
        customer.setCreditLimit(3000);
        customerService.save(customer);
        assertTrue(customerService.getAll().size() >= 2);
    }
}