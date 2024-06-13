package com.rzc.isibox.presentation.main;

import com.rzc.isibox.master.MySerializable;

import java.util.Date;

public class MainModel extends MySerializable {

    private String id;
    private String name;
    private String profile;
    private String productImg;
    private String productName;
    private String productCategory;
    private String productDescription;
    private String metric;
    private int quantity;
    private long targetPrice;
    private Date createdAt;
    private String locale;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        if (profile == null){
            return "";
        }
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(long targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Date getCreatedAt() {
        if (createdAt == null){
            createdAt = new Date();
        }
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
