package com.rzc.isibox.master;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class MySerializable implements Serializable {
    public JSONObject toJson(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
    }

    public <T>  T fromString(String data, Class<T> classOfT){
        return new Gson().fromJson(data, classOfT);
    }
    public <T>  T fromJson(JSONObject data, Class<T> classOfT){
        return new Gson().fromJson(data.toString(), classOfT);
    }
}
