package com.dev.backend.service.impl;

import com.dev.backend.dao.CustomerDAO;
import com.dev.backend.dao.OrderLineDAO;
import com.dev.backend.dao.ProductDAO;
import com.dev.backend.dao.SalesOrderDAO;
import com.dev.backend.model.Customer;
import com.dev.backend.model.OrderLine;
import com.dev.backend.model.Product;
import com.dev.backend.model.SalesOrder;
import com.dev.backend.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("salesOrderService")
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderDAO salesOrderDAO;

    @Autowired
    private OrderLineDAO orderLineDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ProductDAO productDAO;

    /**
     * Save salesOrder and orderLines list if exists or create if not exists
     *
     * @param salesOrder salesOrder
     * @param orderLines list of orderList
     * @return true if success or false if fail
     */
    //todo need synchronize all of this in the future
    @Override
    public boolean save(SalesOrder salesOrder, List<OrderLine> orderLines) {
        //aggregate counts by products
        List<OrderLine> aggregate = new ArrayList<>();
        orderLines.forEach(ol -> {
            boolean found = false;
            for (OrderLine ol2 : aggregate) {
                if (ol2.getProduct().equals(ol.getProduct())) {
                    ol2.setQuantity(ol2.getQuantity() + ol.getQuantity());
                    found = true;
                }
            }
            if (!found) {
                aggregate.add(ol);
            }
        });
        List<OrderLine> existsOrderLines = orderLineDAO.getByOrderNum(salesOrder.getOrderNum());

        double totalPrice = 0;
        double changeBalancePrice = 0;
        //need to merge lists
        Map<String, Integer> newProductCountMap = new HashMap<>();
        //iterate aggregated list
        for (OrderLine newOL : aggregate) {
            boolean found = false;
            //look its elements in already exists
            for (OrderLine existsOL : existsOrderLines) {
                if (existsOL.getProduct().equals(newOL.getProduct())) {
                    //ok, found
                    found = true;
                    Product product = productDAO.get(existsOL.getProduct());
                    if (existsOL.getQuantity().equals(newOL.getQuantity())) {
                        //quantity didn't change, do nothing
                    } else {
                        //count is change, recalc credits and inventory balance
                        if (newOL.getQuantity() > existsOL.getQuantity()) {
                            //quantity increased
                            if (product.getQuantity() < (newOL.getQuantity() - existsOL.getQuantity())) {
                                //product quantity isn't enough, return
                                return false;
                            } else {
                                newProductCountMap.put(product.getCode(), product.getQuantity() - (newOL.getQuantity() - existsOL.getQuantity()));
                                changeBalancePrice += product.getPrice() * (newOL.getQuantity() - existsOL.getQuantity());
                            }
                        } else {
                            //quantity decreased
                            //it's cool :)
                            newProductCountMap.put(product.getCode(), product.getQuantity() + (existsOL.getQuantity() - newOL.getQuantity()));
                            changeBalancePrice -= product.getPrice() * (existsOL.getQuantity() - newOL.getQuantity());
                        }
                    }
                    totalPrice += product.getPrice() * newOL.getQuantity();
                    break;
                }
            }
            if (!found) {
                //found late, check product quantity and return false if enough
                Product product = productDAO.get(newOL.getProduct());
                if (product.getQuantity() < newOL.getQuantity()) {
                    return false;
                }
                totalPrice += newOL.getQuantity() * product.getPrice();
                newProductCountMap.put(product.getCode(), product.getQuantity() - newOL.getQuantity());
                changeBalancePrice += product.getPrice() * newOL.getQuantity();
            }
        }
        ;
        //look elements which need to be removed
        List<Integer> forDelete = new ArrayList<>();
        for (OrderLine existsOL : existsOrderLines) {
            boolean found = false;
            for (OrderLine newOL : aggregate) {
                if (existsOL.getProduct().equals(newOL.getProduct())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Product product = productDAO.get(existsOL.getProduct());
                newProductCountMap.put(product.getCode(), product.getQuantity() + existsOL.getQuantity());
                changeBalancePrice -= product.getPrice() * existsOL.getQuantity();
                forDelete.add(existsOL.getId());
            }
        }
        Customer customer = customerDAO.get(salesOrder.getCustomer());
        //check user free credit limit
        if (changeBalancePrice > customer.getCreditLimit() - customer.getCurrentCredit()) {
            return false;
        }
        //update fields
        customerDAO.updateCurrentCredit(customer.getCode(), customer.getCurrentCredit() + changeBalancePrice);

        salesOrder.setTotalPrice(totalPrice);
        SalesOrder origSalesOrder = salesOrderDAO.get(salesOrder.getOrderNum());
        if (origSalesOrder == null) {
            salesOrderDAO.create(salesOrder);
        } else {
            salesOrderDAO.update(salesOrder);
        }
        aggregate.forEach(orderLine -> {
            OrderLine existsOrderLine = orderLineDAO.getByOrderNumAndProduct(orderLine.getOrderNum(), orderLine.getProduct());
            if (existsOrderLine == null) {
                orderLineDAO.create(orderLine);
            } else {
                existsOrderLine.setQuantity(orderLine.getQuantity());
                orderLineDAO.update(existsOrderLine);
            }
        });
        forDelete.forEach(id -> {
            orderLineDAO.delete(id);
        });
        newProductCountMap.forEach((code, quantity) -> {
            productDAO.updateQuality(code, quantity);
        });
        return true;
    }

    /**
     * Delete SalesOrder by its orderNum
     *
     * @param orderNum salesOrder orderNum
     */
    //todo need synchronize all of this in the future
    @Override
    public void delete(String orderNum) {
        //if we remove SalesOrder we need also remove and OrderLines
        //and recalc credits and inventory balance
        SalesOrder salesOrder = salesOrderDAO.get(orderNum);
        List<OrderLine> orderLines = orderLineDAO.getByOrderNum(orderNum);
        Customer customer = customerDAO.get(salesOrder.getCustomer());
        double changeBalance = 0;
        for (OrderLine ol : orderLines) {
            Product product = productDAO.get(ol.getProduct());
            changeBalance += product.getPrice() * ol.getQuantity();
            productDAO.updateQuality(product.getCode(), product.getQuantity() + ol.getQuantity());
            orderLineDAO.delete(ol.getId());
        }
        salesOrderDAO.delete(salesOrder.getOrderNum());
        customerDAO.updateCurrentCredit(customer.getCode(), customer.getCurrentCredit() - changeBalance);
    }

    /**
     * Get salesOrder by orderNum
     *
     * @param orderNum salesOrder orderNum
     * @return salesOrder
     */
    @Override
    public SalesOrder getSalesOrder(String orderNum) {
        return salesOrderDAO.get(orderNum);
    }

    /**
     * Get all salesOrder list
     *
     * @return list of orderSales
     */
    @Override
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderDAO.getAll();
    }

    /**
     * Get orderList list by orderNum
     *
     * @param orderNum orderSales orderNum
     * @return list of orderList
     */
    @Override
    public List<OrderLine> getOrderLines(String orderNum) {
        return orderLineDAO.getByOrderNum(orderNum);
    }
}
