package com.dev.backend.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

public class ProductTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTypeValidation() {
        //invalid
        Product product = new Product();
        product.setCode("code");
        assertEquals(2, validator.validate(product).size());
        product.setDescription("description");
        assertEquals(1, validator.validate(product).size());
        //valid
        product.setPrice(1);
        assertEquals(0, validator.validate(product).size());
    }
}