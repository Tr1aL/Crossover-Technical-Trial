package com.dev.backend.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

public class OrderLineTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTypeValidation() {
        //invalid
        OrderLine orderLine = new OrderLine();
        assertEquals(2, validator.validate(orderLine).size());
        orderLine.setOrderNum("orderNum");
        assertEquals(1, validator.validate(orderLine).size());
        //valid
        orderLine.setProduct("p_1");
        assertEquals(0, validator.validate(orderLine).size());
    }
}