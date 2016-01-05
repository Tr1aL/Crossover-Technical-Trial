package com.dev.backend.dao.stub;

import com.dev.backend.dao.ProductDAO;
import com.dev.backend.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAOStub implements ProductDAO {

    private Map<Serializable, Product> DB = new HashMap<>();

    @Override
    public synchronized Serializable create(Product obj) {
        DB.put(obj.getCode(), obj);
        return obj.getCode();
    }

    @Override
    public synchronized void update(Product obj) {
        DB.put(obj.getCode(), obj);
    }

    @Override
    public synchronized void delete(Serializable code) {
        DB.remove(code);
    }

    @Override
    public synchronized Product get(Serializable code) {
        return DB.get(code);
    }

    @Override
    public synchronized Double getPrice(Serializable code) {
        Product product = DB.get(code);
        if (product != null) {
            return product.getPrice();
        }
        return null;
    }

    @Override
    public synchronized void updateQuality(Serializable code, int quantity) {
        Product product = DB.get(code);
        if (product != null) {
            product.setQuantity(quantity);
        }
        DB.put(code, product);
    }

    @Override
    public synchronized List<Product> getAll() {
        return new ArrayList<>(DB.values());
    }
}
