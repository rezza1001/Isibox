package com.rzc.isibox.connection.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public abstract class MasterDB {

    protected static String TAG   = "MasterDB";

    public void clearData(Context context){
        try {
            Log.d(TAG,"Cleat Data "+ tableName());
            DatabaseManager pDB = new DatabaseManager(context);
            pDB.delete(tableName());
            pDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean insert(Context context){
        boolean x = false;
        try {
            DatabaseManager pDB = new DatabaseManager(context);
            x = pDB.insert(tableName(), createContentValues());
            pDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return x;
    }
    public boolean insertAsClear(Context context){
        boolean x = false;
        clearData(context);
        try {
            DatabaseManager pDB = new DatabaseManager(context);
            x = pDB.insert(tableName(), createContentValues());
            pDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return x;
    }


    public void delete(Context context, String where){
        try {
            DatabaseManager pDB = new DatabaseManager(context);
            pDB.delete(tableName(), where);
            pDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected abstract ContentValues createContentValues();
    public abstract String getCreateTable();
    public abstract String tableName();
    protected abstract MasterDB build(Cursor res);
    protected abstract void buildSingle(Cursor res);
}
