package org.unboxing.medicineassistant.activity.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class LoginDBhelper extends SQLiteOpenHelper {
    private static int myVersion = 1;


    public LoginDBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //context是上下文对象，name数据库名，factory可选的游标过程，version是版本
    }

    public LoginDBhelper(Context context, String name) {
        this(context, name, null, myVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(userName varchar(50) primary key,userPwd varchar(50),userEmail varchar(50))";
        db.execSQL(sql);
        Log.d("aaa", "创建数据库");
        System.out.println("创建数据库");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("diaoyong update");
    }

    //判断输入的字符串是不是在数据库中存在，若有则是重复，返回true。
    public boolean isStringDuplicated(SQLiteDatabase db, String str, String tableName, String type) {
        Cursor cursor = db.query(tableName, new String[]{type}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(type));
            if (name.equals(str)) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    //输入表名，待查的列，如果查到，返回一个二维list
    public ArrayList<ArrayList<String>> getListFromUser(SQLiteDatabase db, String str,String type) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        Cursor cursor = db.query("user", new String[]{"userName", "userPwd", "userEmail"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (str.equals(cursor.getString(cursor.getColumnIndex(type)))){
                ArrayList<String> temp = new ArrayList<String>(3);
                temp.add(cursor.getString(cursor.getColumnIndex("userName")));
                temp.add(cursor.getString(cursor.getColumnIndex("userPwd")));
                temp.add(cursor.getString(cursor.getColumnIndex("userEmail")));
                result.add(temp);
            }

        }
        cursor.close();
        return result;
    }
}
