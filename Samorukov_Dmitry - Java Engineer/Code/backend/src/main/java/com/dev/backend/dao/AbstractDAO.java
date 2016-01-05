package com.dev.backend.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractDAO<T> implements DAO {

    protected static Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    @Autowired
    public SessionFactory sessionFactory;

    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM " + getEntityClass().getName());
        return (List<T>) query.list();
    }

    public T get(Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        return (T) session.get(getEntityClass(), id);
    }

    @Transactional
    public Serializable create(T obj) {
        logger.debug("Adding new {}", getEntityClass());
        Session session = sessionFactory.getCurrentSession();
        return session.save(obj);
    }

    @Transactional
    public void delete(Serializable id) {
        logger.debug("Deleting existing {} with id {}", getEntityClass(), id);
        Session session = sessionFactory.getCurrentSession();
        T obj = (T) session.get(getEntityClass(), id);
        session.delete(obj);
    }

    @Transactional
    public void update(T obj) {
        logger.debug("Editing existing {}", getEntityClass());
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }
}