package com.dev.backend.dao;

import com.dev.backend.config.TestConfig;
import com.dev.backend.model.SalesOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SalesOrderDAOTest {

    @Autowired
    private SalesOrderDAO salesOrderDAO;

    @Test
    public void testCreate() throws Exception {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum("o_1");
        salesOrderDAO.create(salesOrder);
        assertEquals(salesOrderDAO.get(salesOrder.getOrderNum()).getOrderNum(), "o_1");
    }

    @Test
    public void testUpdate() throws Exception {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum("o_2");
        salesOrderDAO.create(salesOrder);
        salesOrder = salesOrderDAO.get(salesOrder.getOrderNum());
        salesOrder.setTotalPrice(100);
        salesOrderDAO.update(salesOrder);
        assertEquals(salesOrderDAO.get(salesOrder.getOrderNum()).getOrderNum(), "o_2");
        assertEquals(String.valueOf(salesOrderDAO.get(salesOrder.getOrderNum()).getTotalPrice()), String.valueOf(100.));
    }

    @Test
    public void testDelete() throws Exception {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum("o_3");
        salesOrderDAO.create(salesOrder);
        salesOrderDAO.delete(salesOrder.getOrderNum());
        assertNull(salesOrderDAO.get(salesOrder.getOrderNum()));
    }

    @Test
    public void testGet() throws Exception {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum("o_4");
        salesOrderDAO.create(salesOrder);
        assertNotNull(salesOrderDAO.get(salesOrder.getOrderNum()));
    }

    @Test
    public void testGetAll() throws Exception {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum("o_5");
        salesOrderDAO.create(salesOrder);
        salesOrder = new SalesOrder();
        salesOrder.setOrderNum("o_6");
        salesOrderDAO.create(salesOrder);
        assertTrue(salesOrderDAO.getAll().size() > 1);
    }
}