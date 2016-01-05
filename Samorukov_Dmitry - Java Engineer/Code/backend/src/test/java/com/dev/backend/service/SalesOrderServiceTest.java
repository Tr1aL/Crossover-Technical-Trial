package com.dev.backend.service;

import com.dev.backend.config.TestConfig;
import com.dev.backend.model.Customer;
import com.dev.backend.model.OrderLine;
import com.dev.backend.model.Product;
import com.dev.backend.model.SalesOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SalesOrderServiceTest {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Test
    public void testSave() throws Exception {
        createUserAndProduct("user1", "product1");
        assertTrue(createSalesOrderLines("salesOrder1", "user1", "product1"));
        assertEquals(String.valueOf(customerService.get("user1").getCurrentCredit()), String.valueOf(600.));
        assertEquals(String.valueOf(productService.get("product1").getQuantity()), String.valueOf(6));
    }

    @Test
    public void testDelete() throws Exception {
        createUserAndProduct("user2", "product2");
        assertTrue(createSalesOrderLines("salesOrder2", "user2", "product2"));
        salesOrderService.delete("salesOrder2");
        assertEquals(String.valueOf(customerService.get("user2").getCurrentCredit()), String.valueOf(0.));
        assertEquals(String.valueOf(productService.get("product2").getQuantity()), String.valueOf(10));
    }

    @Test
    public void testGetSalesOrder() throws Exception {
        createUserAndProduct("user3", "product3");
        assertTrue(createSalesOrderLines("salesOrder3", "user3", "product3"));
        salesOrderService.delete("salesOrder3");
        assertNull(salesOrderService.getSalesOrder("salesOrder3"));
    }

    @Test
    public void testGetAllSalesOrders() throws Exception {
        createUserAndProduct("user4", "product4");
        assertTrue(createSalesOrderLines("salesOrder4", "user4", "product4"));
        assertTrue(salesOrderService.getAllSalesOrders().size() >= 1);
    }

    @Test
    public void testGetOrderLines() throws Exception {
        createUserAndProduct("user5", "product5");
        assertTrue(createSalesOrderLines("salesOrder5", "user5", "product5"));
        assertTrue(salesOrderService.getOrderLines("salesOrder5").size() == 1);
    }

    private boolean createSalesOrderLines(String orderNum, String user, String product) {
        SalesOrder salesOrder = buildSalesOrder(orderNum, user);
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(buildOrderLine(orderNum, product));
        orderLines.add(buildOrderLine(orderNum, product));
        return salesOrderService.save(salesOrder, orderLines);
    }

    private SalesOrder buildSalesOrder(String orderNum, String user) {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum(orderNum);
        salesOrder.setCustomer(user);
        return salesOrder;
    }

    private OrderLine buildOrderLine(String orderNum, String product) {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNum(orderNum);
        orderLine.setProduct(product);
        orderLine.setQuantity(2);
        return orderLine;
    }

    private void createUserAndProduct(String userCode, String productCode) {
        Customer customer = new Customer();
        customer.setCode(userCode);
        customer.setCreditLimit(2000);
        customerService.save(customer);

        Product product = new Product();
        product.setCode(productCode);
        product.setQuantity(10);
        product.setPrice(150);
        productService.save(product);
    }
}