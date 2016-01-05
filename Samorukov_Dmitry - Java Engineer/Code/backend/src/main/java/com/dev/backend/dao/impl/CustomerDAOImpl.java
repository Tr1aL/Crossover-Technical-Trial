package com.dev.backend.dao.impl;

import com.dev.backend.dao.AbstractDAO;
import com.dev.backend.dao.CustomerDAO;
import com.dev.backend.model.Customer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("customerDAO")
public class CustomerDAOImpl extends AbstractDAO<Customer> implements CustomerDAO {

    @Override
    public Class getEntityClass() {
        return Customer.class;
    }

    @Override
    public void updateCurrentCredit(Serializable code, Double currentCredit) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update " + getEntityClass().getName() + " as tbl set tbl.currentCredit=:currentCredit where tbl.code=:code");
        query.setParameter("currentCredit", currentCredit);
        query.setParameter("code", code);
        query.executeUpdate();
    }
}
