package com.dev.backend.dao.impl;

import com.dev.backend.dao.AbstractDAO;
import com.dev.backend.dao.ProductDAO;
import com.dev.backend.model.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("productDAO")
public class ProductDAOImpl extends AbstractDAO<Product> implements ProductDAO {

    @Override
    public Class getEntityClass() {
        return Product.class;
    }

    @Override
    public Double getPrice(Serializable code) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select tbl.price FROM " + getEntityClass().getName() + " as tbl where tbl.code=:code");
        query.setParameter("code", code);
        List<Double> list = query.list();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateQuality(Serializable code, int quantity) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update " + getEntityClass().getName() + " as tbl set tbl.quantity=:quantity where tbl.code=:code");
        query.setParameter("quantity", quantity);
        query.setParameter("code", code);
        query.executeUpdate();
    }
}
