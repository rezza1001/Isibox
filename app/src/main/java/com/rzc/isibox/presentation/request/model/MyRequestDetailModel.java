package com.rzc.isibox.presentation.request.model;

import com.rzc.isibox.master.MySerializable;

import java.util.ArrayList;
import java.util.Date;

public class MyRequestDetailModel extends MySerializable {

    private String RequestID;
    private String ProductName;
    private String Description;
    private int Quantity;
    private String Metric;
    private long TargetPrice;
    private String ReferenceLink;
    private String Category;
    private String DeliveryTime;
    private String ShippingMethod;
    private String PaymentMethod;
    private String RequestDate;
    private int Status;
    private String UpdatedAt;
    private String RequestBy;
    private String address;
    private String longitude;
    private String latitude;
    private String requestByName;
    private String province;
    private String city;
    private String phone;
    private int offer;
    private ArrayList<Attributes> keywords = new ArrayList<>();
    private ArrayList<Attributes> images = new ArrayList<>();

    private Bid bid;

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getMetric() {
        return Metric;
    }

    public void setMetric(String metric) {
        Metric = metric;
    }

    public long getTargetPrice() {
        return TargetPrice;
    }

    public void setTargetPrice(long targetPrice) {
        TargetPrice = targetPrice;
    }

    public String getReferenceLink() {
        return ReferenceLink;
    }

    public void setReferenceLink(String referenceLink) {
        ReferenceLink = referenceLink;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getRequestBy() {
        return RequestBy;
    }

    public void setRequestBy(String requestBy) {
        RequestBy = requestBy;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public ArrayList<Attributes> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<Attributes> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<Attributes> getImages() {
        return images;
    }

    public void setImages(ArrayList<Attributes> images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRequestByName() {
        return requestByName;
    }

    public void setRequestByName(String requestByName) {
        this.requestByName = requestByName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Bid getBid() {
        return bid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public static class Attributes {
        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Bid{
        int BidID;
        long BidAmount;
        int Status;
        String Comments;
        String CreatedAt;

        public int getBidID() {
            return BidID;
        }

        public void setBidID(int bidID) {
            BidID = bidID;
        }

        public long getBidAmount() {
            return BidAmount;
        }

        public void setBidAmount(long bidAmount) {
            BidAmount = bidAmount;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public String getComments() {
            return Comments;
        }

        public void setComments(String comments) {
            Comments = comments;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            CreatedAt = createdAt;
        }
    }
}
