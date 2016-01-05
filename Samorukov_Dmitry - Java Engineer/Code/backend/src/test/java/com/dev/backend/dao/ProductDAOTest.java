package com.dev.backend.dao;

import com.dev.backend.config.TestConfig;
import com.dev.backend.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProductDAOTest {

    @Autowired
    private ProductDAO productDAO;

    @Test
    public void testCreate() throws Exception {
        Product product = new Product();
        product.setCode("code_1");
        productDAO.create(product);
        assertEquals(productDAO.get(product.getCode()).getCode(), "code_1");
    }

    @Test
    public void testUpdate() throws Exception {
        Product product = new Product();
        product.setCode("code_2");
        productDAO.create(product);
        product = productDAO.get(product.getCode());
        product.setDescription("my description");
        productDAO.update(product);
        assertEquals(productDAO.get(product.getCode()).getCode(), "code_2");
        assertEquals(productDAO.get(product.getCode()).getDescription(), "my description");
    }

    @Test
    public void testDelete() throws Exception {
        Product product = new Product();
        product.setCode("code_3");
        productDAO.create(product);
        productDAO.delete(product.getCode());
        assertNull(productDAO.get(product.getCode()));
    }

    @Test
    public void testGet() throws Exception {
        Product product = new Product();
        product.setCode("code_4");
        productDAO.create(product);
        assertNotNull(productDAO.get(product.getCode()));
    }

    @Test
    public void testGetPrice() throws Exception {
        Product product = new Product();
        product.setCode("code_5");
        product.setPrice(150);
        productDAO.create(product);
        assertEquals(productDAO.get(product.getCode()).getCode(), "code_5");
        assertEquals(String.valueOf(productDAO.getPrice(product.getCode())), String.valueOf(150.));

    }

    @Test
    public void testUpdateQuality() throws Exception {
        Product product = new Product();
        product.setCode("code_6");
        productDAO.create(product);
        productDAO.updateQuality(product.getCode(), 10);
        assertEquals(productDAO.get(product.getCode()).getCode(), "code_6");
        assertEquals(String.valueOf(productDAO.get(product.getCode()).getQuantity()), String.valueOf(10));
    }

    @Test
    public void testGetAll() throws Exception {
        Product product = new Product();
        product.setCode("code_7");
        productDAO.create(product);
        product = new Product();
        product.setCode("code_8");
        productDAO.create(product);
        assertTrue(productDAO.getAll().size() > 1);
    }
}