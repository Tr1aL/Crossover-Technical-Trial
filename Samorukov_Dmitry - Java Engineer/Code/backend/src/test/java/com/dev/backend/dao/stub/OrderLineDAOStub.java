package com.dev.backend.dao.stub;

import com.dev.backend.dao.OrderLineDAO;
import com.dev.backend.model.OrderLine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderLineDAOStub implements OrderLineDAO {

    private Map<Serializable, OrderLine> DB = new HashMap<>();

    @Override
    public synchronized Serializable create(OrderLine obj) {
        DB.put(obj.getId(), obj);
        return obj.getId();
    }

    @Override
    public synchronized void update(OrderLine obj) {
        DB.put(obj.getId(), obj);
    }

    @Override
    public synchronized void delete(Serializable id) {
        DB.remove(id);
    }

    @Override
    public synchronized OrderLine get(Serializable id) {
        return DB.get(id);
    }

    @Override
    public synchronized OrderLine getByOrderNumAndProduct(String orderNum, String product) {
        for (Map.Entry<Serializable, OrderLine> e : DB.entrySet()) {
            if (e.getValue().getOrderNum().equals(orderNum)
                    && e.getValue().getProduct().equals(product)) {
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public synchronized List<OrderLine> getByOrderNum(String orderNum) {
        return DB.entrySet().stream()
                .filter(e -> e.getValue().getOrderNum().equals(orderNum))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
