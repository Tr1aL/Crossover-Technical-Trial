package com.dev.backend.service.impl;

import com.dev.backend.dao.ProductDAO;
import com.dev.backend.model.Product;
import com.dev.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Product service implementation
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    /**
     * Save product if exists or delete if not exists
     *
     * @param obj product
     * @return product
     */
    @Override
    public Product save(Product obj) {
        if (productDAO.get(obj.getCode()) == null) {
            productDAO.create(obj);
        } else {
            productDAO.update(obj);
        }
        return obj;
    }

    /**
     * Delete product by its code
     *
     * @param code product code
     */
    @Override
    public void delete(Serializable code) {
        productDAO.delete(code);
    }

    /**
     * Get product by its code
     *
     * @param code product code
     * @return product
     */
    @Override
    public Product get(Serializable code) {
        return productDAO.get(code);
    }

    /**
     * Get all products list
     *
     * @return list of products
     */
    @Override
    public List<Product> getAll() {
        return productDAO.getAll();
    }

    /**
     * Get product price by product code
     *
     * @param code product code
     * @return product price
     */
    @Override
    public double getPrice(Serializable code) {
        return productDAO.getPrice(code);
    }
}
