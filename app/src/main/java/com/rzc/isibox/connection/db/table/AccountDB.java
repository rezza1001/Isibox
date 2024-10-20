package com.rzc.isibox.connection.db.table;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rzc.isibox.connection.db.DatabaseManager;
import com.rzc.isibox.connection.db.MasterDB;


public class AccountDB extends MasterDB {

    public static final String TAG          = "AccountDB";
    public static final String TABLE_NAME   = "ACCOUNT_DB";

    public static final String USERID = "userId";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String TOKEN = "token";

    public AccountModel model = new AccountModel();


    public String getCreateTable() {
        String create = "create table " + TABLE_NAME + " "
                + "(" +
                " " + USERID + " varchar(50) NULL," +
                " " + EMAIL + " varchar(200) NULL," +
                " " + NAME + " varchar(200) NULL," +
                " " + PHONE + " varchar(10) NULL," +
                " " + ADDRESS + " varchar(500) NULL," +
                " " + TOKEN + " varchar(150) NULL" +
                " )";
        Log.d(TAG,create);
        return create;
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @SuppressLint("Range")
    @Override
    protected AccountDB build(Cursor res) {
        AccountDB cartDB = new AccountDB();
        cartDB.model.setUser_id(res.getString(res.getColumnIndex(USERID)));
        cartDB.model.setEmail(res.getString(res.getColumnIndex(EMAIL)));
        cartDB.model.setName(res.getString(res.getColumnIndex(NAME)));
        cartDB.model.setPhone(res.getString(res.getColumnIndex(PHONE)));
        cartDB.model.setAddress(res.getString(res.getColumnIndex(ADDRESS)));
        cartDB.model.setToken(res.getString(res.getColumnIndex(TOKEN)));
        return cartDB;
    }

    @SuppressLint("Range")
    @Override
    protected void buildSingle(Cursor res) {
        model = new AccountModel();
        model.setUser_id(res.getString(res.getColumnIndex(USERID)));
        model.setEmail(res.getString(res.getColumnIndex(EMAIL)));
        model.setName(res.getString(res.getColumnIndex(NAME)));
        model.setPhone(res.getString(res.getColumnIndex(PHONE)));
        model.setAddress(res.getString(res.getColumnIndex(ADDRESS)));
        model.setToken(res.getString(res.getColumnIndex(TOKEN)));
    }

    public ContentValues createContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERID, model.getUser_id());
        contentValues.put(EMAIL, model.getEmail());
        contentValues.put(NAME, model.getName());
        contentValues.put(PHONE, model.getPhone());
        contentValues.put(ADDRESS, model.getAddress());
        contentValues.put(TOKEN, model.getToken());
        return contentValues;
    }


    @Override
    public boolean insert(Context context) {
        clearData(context);
        return super.insert(context);
    }



    public void getData(Context context){
        DatabaseManager pDB = new DatabaseManager(context);
        SQLiteDatabase db = pDB.getReadableDatabase();
        String query = "select *  from "+TABLE_NAME+" LIMIT 1";
        Cursor res = db.rawQuery( query , null);
        try {
            while (res.moveToNext()){
                buildSingle(res);
            }
            pDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            res.close();
            pDB.close();
        }
    }

}
