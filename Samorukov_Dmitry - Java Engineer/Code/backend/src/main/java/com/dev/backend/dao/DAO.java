package com.dev.backend.dao;

public interface DAO<T> {

    Class<T> getEntityClass();
}
