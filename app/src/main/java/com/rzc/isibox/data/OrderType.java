package com.rzc.isibox.data;

public enum OrderType {

    OPEN_PO("1","Open PO / Jastip"),
    FIND_GOODS("2","Cari Barang"),
    INFO("3","Info / Bantuan"),
    FIND_SERVICE("4","Cari Jasa"),
    DONASI("5","Donasi");

    String id ;
    String value ;
    OrderType(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static OrderType getById(String id){
        for (OrderType order : values()){
            if (order.id.equals(id)){
                return order;
            }
        }
        return OPEN_PO;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
