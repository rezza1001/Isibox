package com.rzc.isibox.presentation.request.model;

import com.google.gson.annotations.SerializedName;
import com.rzc.isibox.master.MySerializable;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;

import java.util.ArrayList;

public class RequestParamModel extends MySerializable {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("token")
    private String token;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("product_description")
    private String productDescription;

    @SerializedName("qty")
    private int qty;

    @SerializedName("metrik")
    private String metrik;

    @SerializedName("price")
    private long price;

    @SerializedName("link")
    private String link;

    @SerializedName("category")
    private String category;

    @SerializedName("delivery_time")
    private String deliveryTime;

    @SerializedName("delivery_method")
    private String deliveryMethod;

    @SerializedName("payment_method")
    private String paymentMethod;


    private CustomerAddressModel address;

    private ArrayList<String> imagesPath = new ArrayList<>();

    @SerializedName("product_images")
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> keywords = new ArrayList<>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getMetrik() {
        return metrik;
    }

    public void setMetrik(String metrik) {
        this.metrik = metrik;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void setImagesPath(ArrayList<String> imagesPath) {
        this.imagesPath = imagesPath;
    }

    public ArrayList<String> getImagesPath() {
        return imagesPath;
    }

    public CustomerAddressModel getAddress() {
        return address;
    }

    public void setAddress(CustomerAddressModel address) {
        this.address = address;
    }
}
