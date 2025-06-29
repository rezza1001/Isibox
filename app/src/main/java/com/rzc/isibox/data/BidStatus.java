package com.rzc.isibox.data;

public enum BidStatus {
    CANCEL(-1, "Dibatalkan"),
    REQUEST(0, "Diajukan"),
    VIEW(1, "Dilihat"),
    ACCEPT(3, "Dipilih"),
    REJECT(4, "Ditolak");

    private final String name;
    private final int id;

    BidStatus(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static BidStatus GetStatusById(int status){
        for (BidStatus sts : values()){
            if (sts.getId() == status){
                return sts;
            }
        }
        return REQUEST;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
