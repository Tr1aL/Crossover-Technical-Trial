package com.dev.backend.service;

import com.dev.backend.model.Product;

import java.io.Serializable;
import java.util.List;

public interface ProductService {

    public Product save(Product obj);

    public void delete(Serializable code);

    public Product get(Serializable code);

    public List<Product> getAll();

    public double getPrice(Serializable code);
}
