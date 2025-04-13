package com.rzc.isibox.presentation.request.model;

import com.rzc.isibox.master.MySerializable;
import com.rzc.isibox.tools.Utility;

import java.util.Date;

public class RequestListModel extends MySerializable {

    private String id;
    private String name;
    private String image;

    private String reqDate;
    private int offer = 0;

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

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public Date getExpiredDate(){
        return Utility.convert2Date(getReqDate(),"yyyy-MM-dd HH:mm");
    }
}
