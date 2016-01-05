package com.dev.backend.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * SalesOrder model
 */
@Entity
@Table(name = "df_salesOrder")
public class SalesOrder implements Serializable {

    @Id
    @Column(name = "c_orderNum", nullable = false, unique = true)
    @Length(min = 1, max = 50)
    private String orderNum;

    @Column(name = "c_customer")
    @NotNull
    private String customer;

    @Column(name = "c_totalPrice")
    @NotNull
    private double totalPrice = 0;

    public SalesOrder() {
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
