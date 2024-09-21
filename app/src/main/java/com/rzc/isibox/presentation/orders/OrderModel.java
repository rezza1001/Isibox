package com.rzc.isibox.presentation.orders;

import com.rzc.isibox.master.MySerializable;
import com.rzc.isibox.tools.Utility;

import java.util.Date;

public class OrderModel extends MySerializable {
    private String id;
    private String productName;
    private String distributorName;
    private String address = "";
    private String productImage;
    private String metric;
    private int qty;
    private long grandTotal;
    private String createdAt;
    private int statusPay;
    private String statusPayName;
    private int statusAction;
    private String statusActionName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(long grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatusPay() {
        return statusPay;
    }

    public void setStatusPay(int statusPay) {
        this.statusPay = statusPay;
    }

    public String getStatusPayName() {
        return statusPayName;
    }

    public void setStatusPayName(String statusPayName) {
        this.statusPayName = statusPayName;
    }

    public int getStatusAction() {
        return statusAction;
    }

    public void setStatusAction(int statusAction) {
        this.statusAction = statusAction;
    }

    public String getStatusActionName() {
        return statusActionName;
    }

    public void setStatusActionName(String statusActionName) {
        this.statusActionName = statusActionName;
    }

    public String getAddress() {
        if (address.isEmpty() || address == null){
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate(){
        return Utility.convert2Date(getCreatedAt(),"dd-MM-yyyy HH:mm");
    }
}
