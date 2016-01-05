package com.dev.backend.config;

import com.dev.backend.dao.CustomerDAO;
import com.dev.backend.dao.OrderLineDAO;
import com.dev.backend.dao.ProductDAO;
import com.dev.backend.dao.SalesOrderDAO;
import com.dev.backend.dao.stub.CustomerDAOStub;
import com.dev.backend.dao.stub.OrderLineDAOStub;
import com.dev.backend.dao.stub.ProductDAOStub;
import com.dev.backend.dao.stub.SaleOrderDAOStub;
import com.dev.backend.service.CustomerService;
import com.dev.backend.service.ProductService;
import com.dev.backend.service.SalesOrderService;
import com.dev.backend.service.impl.CustomerServiceImpl;
import com.dev.backend.service.impl.ProductServiceImpl;
import com.dev.backend.service.impl.SalesOrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    CustomerDAO getCustomerDAO() {
        return new CustomerDAOStub();
    }

    @Bean
    ProductDAO getProductDAO() {
        return new ProductDAOStub();
    }

    @Bean
    SalesOrderDAO getSalesOrderDAO() {
        return new SaleOrderDAOStub();
    }

    @Bean
    OrderLineDAO getOrderLineDAO() {
        return new OrderLineDAOStub();
    }

    @Bean
    CustomerService getCustomerService() {
        return new CustomerServiceImpl();
    }

    @Bean
    ProductService getProductService() {
        return new ProductServiceImpl();
    }

    @Bean
    SalesOrderService getSalesOrderService() {
        return new SalesOrderServiceImpl();
    }
}
