package com.dev.backend.dao;

import com.dev.backend.model.OrderLine;

import java.io.Serializable;
import java.util.List;

public interface OrderLineDAO {

    public Serializable create(OrderLine obj);

    public void update(OrderLine obj);

    public void delete(Serializable id);

    public OrderLine get(Serializable id);

    public OrderLine getByOrderNumAndProduct(String orderNum, String product);

    public List<OrderLine> getByOrderNum(String orderNum);
}
