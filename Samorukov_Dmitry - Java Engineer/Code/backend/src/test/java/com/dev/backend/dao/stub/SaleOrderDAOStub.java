package com.dev.backend.dao.stub;

import com.dev.backend.dao.SalesOrderDAO;
import com.dev.backend.model.SalesOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleOrderDAOStub implements SalesOrderDAO {

    private Map<Serializable, SalesOrder> DB = new HashMap<>();

    @Override
    public synchronized Serializable create(SalesOrder obj) {
        DB.put(obj.getOrderNum(), obj);
        return obj.getOrderNum();
    }

    @Override
    public synchronized void update(SalesOrder obj) {
        DB.put(obj.getOrderNum(), obj);
    }

    @Override
    public synchronized void delete(Serializable orderNum) {
        DB.remove(orderNum);
    }

    @Override
    public synchronized SalesOrder get(Serializable orderNum) {
        return DB.get(orderNum);
    }

    @Override
    public synchronized List<SalesOrder> getAll() {
        return new ArrayList<>(DB.values());
    }
}
