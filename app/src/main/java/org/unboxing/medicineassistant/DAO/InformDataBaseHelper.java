package org.unboxing.medicineassistant.DAO;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class InformDataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "medicine_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME_INFROM= "inform";
    public static final String STATUS = "status";
    public static final String INFORMID = "informid";
    public static final String USERNAME = "username";
    public static final String CONTENT = "content";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static  final String TITLE="title";
    public static final String  BEGINDATE = "begindate";
    public static  final String ENDDATE="enddate";

    public InformDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        //创建数据库
        Log.d(TAG,"创建数据库表....");
        db.execSQL("drop table if exists "+"TABLE_NAME_INFORM");//若表存在则执行表
        db.execSQL("create table " +TABLE_NAME_INFROM

                + "(" + STATUS +
                " integer," +
                INFORMID+
                "long,"+
                USERNAME +
                " String," +
                CONTENT +
                " String,"+
                TITLE+
                " String,"+
                HOUR+
                "integer,"+
                MINUTE+
                "integer," +
                BEGINDATE+
                "integer,"+
               ENDDATE+
                "integer)");//若表不存在，创建表


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
