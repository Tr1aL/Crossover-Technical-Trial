package com.dev.backend.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

public class SalesOrderTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTypeValidation() {
        //invalid
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum("");
        salesOrder.setCustomer("customer");
        assertEquals(1, validator.validate(salesOrder).size());
        //valid
        salesOrder.setOrderNum("o_1");
        salesOrder.setTotalPrice(100);
        assertEquals(0, validator.validate(salesOrder).size());
    }
}