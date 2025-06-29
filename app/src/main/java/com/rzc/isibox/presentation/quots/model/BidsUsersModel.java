/*
 * Copyright (c) 2025. Rezza Developer
 */

package com.rzc.isibox.presentation.quots.model;

import com.rzc.isibox.master.MySerializable;

public class BidsUsersModel extends MySerializable {

    private int BidID;
    private String RequestID;
    private String UserID;
    private String name;
    private String CreatedAt;
    private long BidAmount;
    private String Comments;

    public int getBidID() {
        return BidID;
    }

    public void setBidID(int bidID) {
        BidID = bidID;
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public long getBidAmount() {
        return BidAmount;
    }

    public void setBidAmount(long bidAmount) {
        BidAmount = bidAmount;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
