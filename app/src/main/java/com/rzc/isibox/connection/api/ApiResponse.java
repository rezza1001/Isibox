package com.rzc.isibox.connection.api;



import com.rzc.isibox.master.MySerializable;

import org.json.JSONObject;

public class ApiResponse extends MySerializable {

    private int code;
    private String message;
    private JSONObject data;

    private Object object;

    public ApiResponse(){

    }

    public ApiResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public ApiResponse(JSONObject data, int code, String message){
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public static ApiResponse getError(String message){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.FAILED);
        apiResponse.setMessage(message);
        apiResponse.setData(null);
        return apiResponse;
    }
    public static ApiResponse getErrorWithCode(String message, int code){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        apiResponse.setData(null);
        return apiResponse;
    }

    public static ApiResponse getSuccess(JSONObject data){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.OK_200);
        apiResponse.setMessage("Success");
        apiResponse.setData(data);
        return apiResponse;
    }
    public static ApiResponse getSuccessWithMsg(JSONObject data, String message){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.OK_200);
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return apiResponse;
    }

}
