package com.dev.backend.service;

import com.dev.backend.model.OrderLine;
import com.dev.backend.model.SalesOrder;

import java.util.List;

public interface SalesOrderService {

    public boolean save(SalesOrder salesOrder, List<OrderLine> orderLines);

    public void delete(String orderNum);

    public SalesOrder getSalesOrder(String orderNum);

    public List<SalesOrder> getAllSalesOrders();

    public List<OrderLine> getOrderLines(String orderNum);
}
