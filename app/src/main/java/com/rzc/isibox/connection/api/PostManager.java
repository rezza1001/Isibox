package com.rzc.isibox.connection.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.rzc.isibox.connection.db.table.AccountDB;
import com.rzc.isibox.presentation.component.AlertDialog;
import com.rzc.isibox.presentation.component.Loading;
import com.rzc.isibox.tools.ActivityUtils;
import com.rzc.isibox.tools.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class PostManager {

    private static final String TAG = "PostManager";
    private static final String SPARATOR = "M0C1-14";
    private String mainUrl ="";
    private String apiUrl;
    private JSONObject mData        = new JSONObject();
    private String mParam = "";
    private final ArrayList<Bundle> headerParams = new ArrayList<>();
    private final ArrayList<Bundle> headerConfig = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private boolean showLoading     = true;
    private boolean showErrorMessage     = true;
    private Date dateStart;
    private String errorOut = "";
    private int timeOut = 120000; // ms

    AccountDB accountDB;

    public PostManager(Context mContext, String apiUrl) {
        this.apiUrl = new EndpointURL(mContext).getBaseUrl() + apiUrl;
        context = mContext;
        accountDB = new AccountDB();
        accountDB.getData(context);
    }


    public void exGet(){
        execute("GET");
    }

    public void exPost(){
        execute("POST");
    }

    public void exDelete(){
        execute("DELETE");
    }

    public void showloading(boolean show){
        showLoading = show;
    }

    public void setData(JSONObject jo) {
        mData = jo;
    }


    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void addHeaderParam(String key, String value){
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putString("value", value);
        headerParams.add(bundle);
    }
    public void addHeaderParam(String key, int value){
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putInt("value", value);
        headerParams.add(bundle);
    }

    public void addHeaderParam(String key, boolean value){
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putBoolean("value", value);
        headerParams.add(bundle);
    }

    public void addHeaderConfig(String key, String value){
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putString("value", value);
        headerConfig.add(bundle);
    }

    public String getApiUrl(){
        StringBuilder param = new StringBuilder();
        if (headerParams.size() > 0){
            param = new StringBuilder("?");
        }
        for (Bundle bundle : headerParams) {
            param.append(bundle.getString("key")).append("=").append(bundle.getString("value"));
        }
        apiUrl = apiUrl + param;
        return apiUrl;
    }


    public void addParam(String key, int value){
        if (context == null){
            return;
        }
        try {
            mData.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addParam(String key, String value){
        if (context == null){
            return;
        }
        try {
            mData.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addParam(String key, ArrayList<String> value){
        if (context == null){
            return;
        }
        try {
            JSONArray ja = new JSONArray();
            for (String s : value){
                ja.put(s);
            }
            mData.put(key, ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addSingleParam(String textParam){
        mParam = textParam;
    }
    public void addParam(String key, double value){
        if (context == null){
            Utility.LogDbug(TAG,"Request Canceled !!!");
            return;
        }
        try {
            mData.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addParam(String key, boolean value){
        if (context == null){
            Utility.LogDbug(TAG,"Request Canceled !!!");
            return;
        }
        try {
            mData.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addParam(String key, JSONObject data){
        if (context == null){
            Utility.LogDbug(TAG,"Request Canceled !!!");
            return;
        }
        try {
            mData.put(key, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setShowErrorMessage(boolean showErrorMessage) {
        this.showErrorMessage = showErrorMessage;
    }

    public JSONObject getParamData(){
        return mData;
    }

    public void addParam(String key, JSONArray data){
        if (context == null){
            Utility.LogDbug(TAG,"Request Canceled !!!");
            return;
        }
        try {
            mData.put(key, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void execute(String type){
        onPreExecute();
        new Thread(() -> {
            String result = doInBackground(type);
            ((Activity)context).runOnUiThread(() -> onPostExecute(result));
        }).start();

    }
    protected void onPreExecute() {
        dateStart = new Date();
        if (context == null){
            Utility.LogDbug(TAG,"Request Canceled !!!");
            return;
        }
        Utility.LogDbug(TAG,"onPreExecute with loading : "+ showLoading);
        if (showLoading){
            Loading.showLoading(context,"Please wait..");
        }

    }

    protected String doInBackground(String... arg0) {

        StringBuilder sbResponse = new StringBuilder();
        try {

            String type = arg0[0];
            if (type.equals("GET")) {
                StringBuilder param = new StringBuilder();
                if (headerParams.size() > 0){
                    param = new StringBuilder("?");
                }
                for (Bundle bundle : headerParams) {
                    if (bundle.get("value") instanceof  Boolean){
                        param.append(bundle.getString("key")).append("=").append(bundle.getBoolean("value"));
                    }
                    else if (bundle.get("value") instanceof  Integer){
                        param.append(bundle.getString("key")).append("=").append(bundle.getInt("value"));
                    }
                    else {
                        param.append(bundle.getString("key")).append("=").append(bundle.getString("value"));
                    }
                    param.append("&");
                }
                apiUrl = apiUrl + param;
            }

            URL url = new URL(mainUrl +apiUrl); //Enter URL here
            String host = url.getHost();
            InetAddress address = InetAddress.getByName(host);
            String ip = address.getHostAddress();
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod(type); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            if (type.equals("POST") || type.equals("PUT")){
                httpURLConnection.setDoOutput(true);
            }

            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.setRequestProperty("Accept", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.setConnectTimeout(timeOut);
            httpURLConnection.setReadTimeout(timeOut);

            for (Bundle bundle : headerConfig) {
                httpURLConnection.setRequestProperty(bundle.getString("key"), bundle.getString("value"));
                Log.d(TAG,"header "+ bundle);
            }
            try {
                httpURLConnection.connect();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            Utility.LogDbug(TAG,type+ " "+url+" "+apiUrl);
            if (type.equals("POST")|| type.equals("PUT")){
                if (accountDB.model.getToken() != null){
                    mData.put("id", accountDB.model.getUser_id());
                    mData.put("token", accountDB.model.getToken());
                }
                String parameterData = mData.toString();
                if (!mParam.isEmpty()){
                    parameterData = mParam;
                }

                Utility.LogDbug(TAG,"HOST : "+host+", InetAddress : "+address+", IP : "+ip);
                Utility.LogDbug(TAG,"data "+ " : "+ parameterData);
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(parameterData);
                wr.flush();
                wr.close();
            }


            int responseCode = httpURLConnection.getResponseCode();
            Utility.LogDbug(TAG,"RESPONSE CODE : "+ responseCode);
            sbResponse.append(responseCode).append(SPARATOR);
            BufferedReader in;
            if (responseCode == ErrorCode.OK_200 || responseCode == ErrorCode.CODE_201){
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            }
            else if (responseCode == ErrorCode.BLOCK){
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            }
            else {
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                Utility.LogDbug(TAG,httpURLConnection.getResponseMessage());
            }


            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            sbResponse.append(response);
            return sbResponse.toString();

        } catch (Exception e) {
            e.printStackTrace();
            sbResponse.append("Request time out");
            errorOut = e.getMessage();
            if (showLoading){
                Loading.cancelLoading();
            }
            return sbResponse.toString();
        }

    }


    protected void onPostExecute(String presults) {
        if (showLoading){
            Loading.cancelLoading();
        }
        Utility.LogDbug(TAG,"onPostExecute "+ presults.length());
        if (context == null){
            Utility.LogDbug(TAG,"Request Canceled !!!");
            return;
        }
        Date date1 = new Date();
        long diff = date1.getTime() - dateStart.getTime();
        Utility.LogDbug(TAG,"TOTAL TIME : "+ diff+" Seconds");

        try {

            String results = presults.split(SPARATOR)[1];
            int code    =  Integer.parseInt(presults.split(SPARATOR)[0]);

            if (results != null){
                Utility.LogDbug(TAG,"TEXT RESPONSE "+this.apiUrl +" | "+ results +" | HEADER CODE : "+ code);
                if (results.equals("Request time out")){
                    if (showErrorMessage){
                        Toast.makeText(context,"Request time out", Toast.LENGTH_SHORT).show();
                    }
                    mReceiveListener.onReceive(new ApiResponse( ErrorCode.TIME_OUT, "Time Out"));
                    return;
                }
                try {
                    JSONObject jo;
                    String message = "";
                    if (results.startsWith("[")){
                        jo = new JSONObject();
                        jo.put("data", new JSONArray(results));
                    }
                    else if (results.startsWith("{")){
                        jo   = new JSONObject(results);
                        if (jo.has("code") && jo.has("message")){
                            code = jo.getInt("code");
                            message = jo.getString("message");
                        }
                    }
                    else {
                        jo = new JSONObject();
                        jo.put("data", results);
                    }
                    if (code == ErrorCode.NOT_AUTHORIZE){
                        clearData();
                    }

                    mReceiveListener.onReceive(new ApiResponse(jo, code,message));
                } catch (JSONException e) {
                    sendError(e,presults);
                    mReceiveListener.onReceive(new ApiResponse( ErrorCode.CODE_UNPROCESSABLE_ENTITY, "Undefined"));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            sendError(e,presults);
            if (mReceiveListener != null){
                mReceiveListener.onReceive( new ApiResponse( ErrorCode.UNDIFINED_ERROR,"Error Connection "+ e.getMessage()));
            }
            if (showErrorMessage){
                Toast.makeText(context, "Error Connection "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void sendError(Exception e, String responseData){
        Log.e(TAG,"Exception "+e.getMessage());
        Log.e(TAG,"ResponseData "+responseData);

        if (dateStart == null){
            dateStart = new Date();
        }
        Log.d(TAG,"SEND Error ---- >");
//        AccountDB accountDB = new AccountDB();
//        accountDB.getData(context);

        long diff = new Date().getTime() - dateStart.getTime();
//        String versionName = "Version " + BuildConfig.VERSION_NAME;

//        DateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String,Object> data = new HashMap<>();
//        data.put("apiUrl", mainUrl +""+ apiUrl);
//        data.put("param", mData.toString());
//        data.put("user_id", accountDB.id);
//        data.put("employeeCode", accountDB.employeeCode);
//        data.put("versionApps",versionName);
//        data.put("versionCode",BuildConfig.VERSION_CODE);
//        data.put("time", format.format(new Date()));
//        data.put("timemillis", System.currentTimeMillis());
//        data.put("long_time", diff+"");
//        data.put("exception", errorOut);
//        data.put("time_hour", Utility.getDateString(new Date(),"dd-MM-yyyy HH"));
//        data.put("time_date", Utility.getDateString(new Date(),"dd-MM-yyyy"));
//        String response = "-";
//        if (e.getMessage() != null){
//            if (Objects.requireNonNull(e.getMessage()).length() > 100){
//                response = e.getMessage().substring(0,100);
//            }
//            else {
//                response = e.getMessage();
//            }
//        }
//        if (responseData.length() > 500){
//            responseData = responseData.substring(0,500);
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        data.put("error", response);
//        data.put("response", responseData);
//        db.collection("DBUG_"+calendar.get(Calendar.YEAR)+"_"+calendar.get(Calendar.MONTH))
//                .add(data)
//                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
//                .addOnFailureListener(e1 -> Log.w(TAG, "Error adding document", e1));
    }

    private void clearData(){
        AccountDB accountDB = new AccountDB();
        accountDB.clearData(context);
        AlertDialog dialog = new AlertDialog(context);
        dialog.showFailed("Sesi login telah habis. silahkan login ulang.");
        dialog.setOnSelectedListener(() -> {
            ActivityUtils.finishCurrentActivity(context);
        });
    }

    private onReceiveListener mReceiveListener;
    public void setOnReceiveListener(onReceiveListener mReceiveListener){
        this.mReceiveListener = mReceiveListener;
    }
    public interface onReceiveListener{
        void onReceive(ApiResponse apiResponse);
    }

}