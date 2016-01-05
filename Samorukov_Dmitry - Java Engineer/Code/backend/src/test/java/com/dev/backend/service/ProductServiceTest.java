package com.dev.backend.service;

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
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testSave() throws Exception {
        Product product = new Product();
        product.setCode("book_1");
        product.setDescription("a book");
        productService.save(product);
        product = productService.get("book_1");
        assertEquals(product.getDescription(), "a book");
        product.setQuantity(10);
        productService.save(product);
        product = productService.get("book_1");
        assertEquals(String.valueOf(product.getQuantity()), String.valueOf(10));
    }

    @Test
    public void testDelete() throws Exception {
        Product product = new Product();
        product.setCode("book_1");
        productService.save(product);
        product = productService.get("book_1");
        assertNotNull(product);
        productService.delete(product.getCode());
        product = productService.get("book_1");
        assertNull(product);
    }

    @Test
    public void testGet() throws Exception {
        Product product = new Product();
        product.setCode("book_1");
        productService.save(product);
        product = productService.get("book_1");
        assertNotNull(product);
    }

    @Test
    public void testGetAll() throws Exception {
        Product product = new Product();
        product.setCode("book_2");
        productService.save(product);
        product = new Product();
        product.setCode("book_3");
        productService.save(product);
        assertTrue(productService.getAll().size() >= 2);
    }

    @Test
    public void testGetPrice() throws Exception {
        Product product = new Product();
        product.setCode("book_4");
        product.setDescription("java book");
        product.setPrice(150.);
        productService.save(product);
        assertEquals(String.valueOf(productService.getPrice("book_4")), String.valueOf(150.));
    }
}