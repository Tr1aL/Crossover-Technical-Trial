package com.dev.backend.dao;

import com.dev.backend.config.TestConfig;
import com.dev.backend.model.OrderLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OrderLineDAOTest {

    @Autowired
    private OrderLineDAO orderLineDAO;

    @Test
    public void testCreate() throws Exception {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNum("o_1");
        orderLineDAO.create(orderLine);
        assertEquals(orderLineDAO.get(orderLine.getId()).getOrderNum(), "o_1");
    }

    @Test
    public void testUpdate() throws Exception {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNum("o_2");
        orderLineDAO.create(orderLine);
        orderLine = orderLineDAO.get(orderLine.getId());
        orderLine.setQuantity(10);
        orderLineDAO.update(orderLine);
        assertEquals(orderLineDAO.get(orderLine.getId()).getOrderNum(), "o_2");
        assertEquals(String.valueOf(orderLineDAO.get(orderLine.getId()).getQuantity()), String.valueOf(10));
    }

    @Test
    public void testDelete() throws Exception {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNum("o_3");
        orderLineDAO.create(orderLine);
        orderLineDAO.delete(orderLine.getId());
        assertNull(orderLineDAO.get(orderLine.getId()));
    }

    @Test
    public void testGetByOrderNumAndProduct() throws Exception {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNum("o_4");
        orderLine.setProduct("p_1");
        orderLineDAO.create(orderLine);
        assertNotNull(orderLineDAO.getByOrderNumAndProduct(orderLine.getOrderNum(), orderLine.getProduct()));
    }

    @Test
    public void testGetByOrderNum() throws Exception {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNum("o_5");
        orderLineDAO.create(orderLine);
        assertTrue(orderLineDAO.getByOrderNum(orderLine.getOrderNum()).size() > 0);

    }
}