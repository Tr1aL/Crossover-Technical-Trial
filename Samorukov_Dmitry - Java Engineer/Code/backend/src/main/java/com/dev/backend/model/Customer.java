package com.dev.backend.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Customer model
 */
@Entity
@Table(name = "df_customer")
public class Customer implements Serializable {

    @Id
    @Column(name = "c_code", nullable = false, unique = true)
    @Length(min = 1, max = 50)
    private String code;

    @Column(name = "c_name")
    @NotNull
    @Length(min = 1)
    private String name;

    @Column(name = "c_phone1")
    @NotNull
    @Length(min = 1)
    private String phone1;

    @Column(name = "c_phone2")
    private String phone2;

    @Column(name = "c_address")
    private String address;

    @Column(name = "c_creditLimit")
    @NotNull
    @Min(1)
    private double creditLimit = 0;

    @Column(name = "c_currentCredit")
    @NotNull
    private double currentCredit = 0;

    public Customer() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(double currentCredit) {
        this.currentCredit = currentCredit;
    }
}
