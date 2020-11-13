package org.unboxing.medicineassistant.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.unboxing.medicineassistant.dao.MedicinePairDao;
import org.unboxing.medicineassistant.entity.MedicinePair;
import org.unboxing.medicineassistant.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MedicinePairDaoImpl implements MedicinePairDao {

    private DBHelper dBHelper;

    public MedicinePairDaoImpl(Context context){
        dBHelper = new DBHelper(context);
    }

    @Override
    public List<MedicinePair> findAllMainMedicine() {
        String sql = "SELECT * FROM medicine_pair";
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<MedicinePair> list = new ArrayList<>();
        int count = cursor.getCount();
        while (count != 0) {
            String main_medicine = cursor.getString(cursor.getColumnIndex("main_medicine"));
            String pair_medicine = cursor.getString(cursor.getColumnIndex("pair_medicine"));
            String level = cursor.getString(cursor.getColumnIndex("level"));
            list.add(new MedicinePair(main_medicine, pair_medicine, level));
            count--;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<MedicinePair> findByMainMedicine(String mainMedicine) {
        String sql = "SELECT * FROM medicine_pair WHERE main_medicine = ?";
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{mainMedicine});
        cursor.moveToFirst();
        List<MedicinePair> list = new ArrayList<>();
        int count = cursor.getCount();
        while (count != 0) {
            String main_medicine = cursor.getString(cursor.getColumnIndex("main_medicine"));
            String pair_medicine = cursor.getString(cursor.getColumnIndex("pair_medicine"));
            String level = cursor.getString(cursor.getColumnIndex("level"));
            list.add(new MedicinePair(main_medicine, pair_medicine, level));
            count--;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public void save(MedicinePair medicinePair) {
        String sql = "INSERT INTO medicine_pair(main_medicine, pair_medicine, level) values(?, ?, ?)";
        SQLiteDatabase db = dBHelper.getWritableDatabase();
//        if (!exist(user.getUsername())) {
            db.execSQL(sql, new String[]{medicinePair.getMainMedicine(), medicinePair.getPairMedicine(),medicinePair.getLevel(),});
            System.out.println("插入");
//        } else {
//            System.out.println("未插入");
//        }
    }

    @Override
    public List<String> findAllName() {
        String sql = "SELECT distinct main_medicine FROM medicine_pair";
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<String> list = new ArrayList<>();
        int count = cursor.getCount();
        while (count != 0) {
            String main_medicine = cursor.getString(cursor.getColumnIndex("main_medicine"));
            list.add(main_medicine);
            count--;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
