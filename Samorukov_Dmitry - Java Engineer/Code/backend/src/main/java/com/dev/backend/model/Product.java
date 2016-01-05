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
 * Product model
 */
@Entity
@Table(name = "df_product")
public class Product implements Serializable {

    @Id
    @Column(name = "c_code", nullable = false, unique = true)
    @Length(min = 1, max = 50)
    private String code;

    @Column(name = "c_description")
    @NotNull
    @Length(min = 1)
    private String description;

    @Column(name = "c_price")
    @NotNull
    @Min(value = 1)
    private double price = 0;

    @Column(name = "c_quantity")
    @NotNull
    @Min(value = 0)
    private int quantity = 0;

    public Product() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
