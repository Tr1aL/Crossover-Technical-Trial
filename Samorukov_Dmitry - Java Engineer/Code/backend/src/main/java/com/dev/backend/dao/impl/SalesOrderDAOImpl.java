package com.dev.backend.dao.impl;

import com.dev.backend.dao.AbstractDAO;
import com.dev.backend.dao.SalesOrderDAO;
import com.dev.backend.model.SalesOrder;
import org.springframework.stereotype.Repository;

@Repository("salesOrderDAO")
public class SalesOrderDAOImpl extends AbstractDAO<SalesOrder> implements SalesOrderDAO {

    @Override
    public Class getEntityClass() {
        return SalesOrder.class;
    }
}
