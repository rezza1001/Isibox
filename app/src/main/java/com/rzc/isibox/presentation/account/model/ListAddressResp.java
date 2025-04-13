package com.rzc.isibox.presentation.account.model;

import com.rzc.isibox.master.MySerializable;

import java.util.ArrayList;

public class ListAddressResp extends MySerializable {

    private boolean status;
    private boolean isset;

    private ArrayList<CustomerAddressModel> list = new ArrayList<>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIsset() {
        return isset;
    }

    public void setIsset(boolean isset) {
        this.isset = isset;
    }

    public ArrayList<CustomerAddressModel> getList() {
        return list;
    }

    public void setList(ArrayList<CustomerAddressModel> list) {
        this.list = list;
    }
}
