package com.rzc.isibox.presentation.request;

import com.rzc.isibox.master.MySerializable;
import com.rzc.isibox.tools.Utility;

import java.util.Date;

public class RequestModel extends MySerializable {

    private String id;
    private String name;
    private String image;

    private String expired;
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

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public Date getExpiredDate(){
        return Utility.convert2Date(getExpired(),"dd-MM-yyyy HH:mm");
    }
}
