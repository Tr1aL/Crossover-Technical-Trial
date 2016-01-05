package com.dev.backend.dao.impl;

import com.dev.backend.dao.AbstractDAO;
import com.dev.backend.dao.OrderLineDAO;
import com.dev.backend.model.OrderLine;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderLineDAOImpl extends AbstractDAO<OrderLine> implements OrderLineDAO {

    @Override
    public Class getEntityClass() {
        return OrderLine.class;
    }

    @Override
    public OrderLine getByOrderNumAndProduct(String orderNum, String product) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + getEntityClass().getName() + " as tbl where tbl.orderNum=:orderNum and tbl.product=:product");
        query.setParameter("orderNum", orderNum);
        query.setParameter("product", product);
        List<OrderLine> list = query.list();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<OrderLine> getByOrderNum(String orderNum) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + getEntityClass().getName() + " as tbl where tbl.orderNum=:orderNum");
        query.setParameter("orderNum", orderNum);
        return query.list();
    }
}
