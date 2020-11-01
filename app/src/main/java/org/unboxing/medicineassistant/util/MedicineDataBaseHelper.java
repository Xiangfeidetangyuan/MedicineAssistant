package org.unboxing.medicineassistant.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MedicineDataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "medicine_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME_MEDICINE= "medicine";
    public static final String MEDICINE_NAME = "medicine_name";
    public static final String DESCRIPTION = "description";
    public static final String REPEATION = "repeation";
    public static final String DOSE = "dose";



    public MedicineDataBaseHelper(Context context) {

        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户名，密码，性别，年龄
        //创建数据库
        Log.d(TAG,"创建数据库表....");
        db.execSQL("drop table if exists "+"TABLE_NAME_MEDICINE");//若表存在则执行表
        db.execSQL("create table " +TABLE_NAME_MEDICINE

               + "(" + MEDICINE_NAME +
                " String," +
                DESCRIPTION+
                " String,"+
                DOSE +
                " integer," +
                REPEATION +
                " integer)");//若表不存在，创建表

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
  /*
        switch(oldVersion){
            case 1://添加phone字段
                sql= "alter table " + TABLE_NAME_MEDICINE + " add phone integer";
                db.execSQL(sql);
                break;
            case 2:

                break;
        }*/
    }
}
