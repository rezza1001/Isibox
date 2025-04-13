package com.rzc.isibox.presentation.request.model;

import com.rzc.isibox.master.MySerializable;

public class QuotesModel extends MySerializable {
    private String id;
    private String name;
    private String image;
    private int status;
    private String statusName;
    private String locale;
    private String address;
    private String user;
    private int qty;
    private long priceTarget;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getPriceTarget() {
        return priceTarget;
    }

    public void setPriceTarget(long priceTarget) {
        this.priceTarget = priceTarget;
    }
}
