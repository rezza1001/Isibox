package com.rzc.isibox.data;

public enum RequestStatus {
    REQUEST(1, "Permintaan"),
    CANCELED(-1, "Dibatalkan"),
    PROCESS(2, "Proses"),
    FINISH(3, "Selesai");

    private final String name;
    private final int id;

    RequestStatus(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static RequestStatus GetStatusById(int status){
        for (RequestStatus sts : values()){
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
