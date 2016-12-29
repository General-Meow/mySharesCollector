package com.paulhoang.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by paul on 30/10/2016.
 */
public class ShareData implements Serializable{
    private String name;
    private String code;
    private BigDecimal price;

    public ShareData() {

    }

    public ShareData(String name, String code, BigDecimal price) {
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShareData{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }
}
