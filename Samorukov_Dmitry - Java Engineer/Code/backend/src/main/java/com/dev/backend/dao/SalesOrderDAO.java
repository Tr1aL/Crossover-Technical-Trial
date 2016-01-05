package com.dev.backend.dao;

import com.dev.backend.model.SalesOrder;

import java.io.Serializable;
import java.util.List;

public interface SalesOrderDAO {

    public Serializable create(SalesOrder obj);

    public void update(SalesOrder obj);

    public void delete(Serializable orderNum);

    public SalesOrder get(Serializable orderNum);

    public List<SalesOrder> getAll();
}
