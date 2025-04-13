package com.rzc.isibox.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataGPSModel implements Serializable {
    private double longitude = 0;
    private double latitude = 0;
    private double altitude = 0;
    private float course = 0;
    private double speed = 0;
    private Date time = new Date();
    private String note = "";
    private String timeZone = "";
    private String address = "";

    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    private final SimpleDateFormat simpleDateFormat;
    public DataGPSModel(){
        simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id"));
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getCourse() {
        return course;
    }

    public void setCourse(float course) {
        this.course = course;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Date getTime() {
        return time;
    }

    public String getTimeString(){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("id"));
        return sdf.format(getTime());
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setTimeZone(String timeZone) {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        this.timeZone = timeZone;
    }

    public JSONObject pack(){
        JSONObject jo = new JSONObject();
        try {
            jo.put(GpsVariable.LONGITUDE,getLongitude());
            jo.put(GpsVariable.LATITUDE,getLatitude());
            jo.put(GpsVariable.SPEED,getSpeed());
            jo.put(GpsVariable.COURSE,getCourse());
            jo.put(GpsVariable.TIME,simpleDateFormat.format(getTime()));
            jo.put(GpsVariable.NOTE,getNote());
            jo.put(GpsVariable.TIMEZONE,getTimeZone());
            jo.put(GpsVariable.ADDRESS,getAddress());
            jo.put(GpsVariable.ALTITUDE,getAltitude());
        } catch (JSONException e) {
            return null;
        }
        return jo;
    }


    public void unpack(String jo){
        if (jo.isEmpty()){
            return;
        }
        try {
            unpack(new JSONObject(jo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void unpack(JSONObject jo){

        try {
            setLatitude(jo.getDouble(GpsVariable.LATITUDE));
            setLongitude(jo.getDouble(GpsVariable.LONGITUDE));
            setSpeed(jo.getInt(GpsVariable.SPEED));
            setCourse(jo.getInt(GpsVariable.COURSE));
            setTime(simpleDateFormat.parse(jo.getString(GpsVariable.TIME)));
            setNote(jo.getString(GpsVariable.NOTE));
            setTimeZone(jo.getString(GpsVariable.TIMEZONE));
            setAddress(jo.getString(GpsVariable.ADDRESS));
            setAltitude(jo.getDouble(GpsVariable.ALTITUDE));
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

}
