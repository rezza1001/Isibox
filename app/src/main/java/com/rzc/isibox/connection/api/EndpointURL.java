/*
 * Copyright (c) 10 - 2 - 2021. Baihaqi
 */

package com.rzc.isibox.connection.api;

import android.content.Context;

import com.rzc.isibox.data.Global;
import com.rzc.isibox.data.MyPreference;


public class EndpointURL {
    private String BASE_URL = "";
    private ConnectionCfg cfg;

    private Context context;

    public EndpointURL(Context context) {
        this.context = context;
        String connection = MyPreference.getString(context, Global.CONNECTION);
        String url = MyPreference.getString(context, Global.MAIN_URL);
        if (connection.isEmpty()){
            cfg = ConnectionCfg.PRODUCTION;
            MyPreference.save(context, Global.MAIN_URL, cfg.getUrl());
            MyPreference.save(context, Global.CONNECTION, cfg.getName());
            BASE_URL = cfg.getUrl();
        }
        else {
            MyPreference.save(context, Global.MAIN_URL, url);
            MyPreference.save(context, Global.CONNECTION, connection);
            BASE_URL = url;
            cfg = ConnectionCfg.getByUrl(BASE_URL);
        }
    }

    public void setProduction(){
        cfg = ConnectionCfg.PRODUCTION;
        MyPreference.save(context, Global.MAIN_URL, cfg.getUrl());
        MyPreference.save(context, Global.CONNECTION, cfg.getName());
        BASE_URL = cfg.getUrl();
        cfg = ConnectionCfg.getByUrl(BASE_URL);
    }

    public String getBaseUrl(){
        return BASE_URL;
    }

    public ConnectionCfg getCfg(){
        return cfg;
    }
}
