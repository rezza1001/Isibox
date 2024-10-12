package com.rzc.isibox.connection.api;

public enum ConnectionCfg {

    PRODUCTION("Production", "https://apiapps.isibox.id/");

    private final String name;
    private final String url;

    ConnectionCfg(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public static ConnectionCfg getByUrl(String url){
        for (ConnectionCfg cfg : values()){
            if (cfg.url.equals(url)){
                return cfg;
            }
        }
        return PRODUCTION;
    }
}
