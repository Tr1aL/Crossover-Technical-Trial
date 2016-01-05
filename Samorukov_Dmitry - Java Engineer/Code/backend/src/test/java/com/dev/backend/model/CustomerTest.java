package com.dev.backend.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTypeValidation() {
        //invalid
        Customer customer = new Customer();
        customer.setCode("code");
        assertEquals(3, validator.validate(customer).size());
        customer.setName("name");
        assertEquals(2, validator.validate(customer).size());
        customer.setPhone1("phone");
        assertEquals(1, validator.validate(customer).size());
        //valid
        customer.setCreditLimit(1000);
        assertEquals(0, validator.validate(customer).size());
    }
}