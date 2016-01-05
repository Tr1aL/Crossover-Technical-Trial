package com.dev.backend.dao;

import com.dev.backend.model.Product;

import java.io.Serializable;
import java.util.List;

public interface ProductDAO {

    public Serializable create(Product obj);

    public void update(Product obj);

    public void delete(Serializable code);

    public Product get(Serializable code);

    public Double getPrice(Serializable code);

    public void updateQuality(Serializable code, int quantity);

    public List<Product> getAll();
}
