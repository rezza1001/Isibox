package com.rzc.isibox.presentation.request;

public enum QuotesStatus {

    REJECTED(-1, "Ditolak"),
    NEW(1, "Belum Dilihat"),
    UNDEFINED(99, "Tidak dikenali");

    final int status;
    final String name;
    QuotesStatus (int status, String name){
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static QuotesStatus getById(int id){
        for (QuotesStatus val : values()){
            if (val.status == id){
                return val;
            }
        }
        return UNDEFINED;
    }
}
