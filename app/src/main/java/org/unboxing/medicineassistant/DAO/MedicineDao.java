package org.unboxing.medicineassistant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import org.unboxing.medicineassistant.entity.medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineDao extends IMedicineDao {
    private static final String TAG ="dao" ;
    private final MedicineDataBaseHelper mMedicineDataBaseHelper;
    public MedicineDao(Context context) {
         mMedicineDataBaseHelper = new MedicineDataBaseHelper(context);
    }

    /**
     *
     *
     * columns 需要查询的字段,全部查询就写null,部分就写new String[]{"id","name","sex"}
     * selection 查询条件,where后的条件语句,不带内容 例如:id>? and name=?
     * selectionArgs 查询条件对于的内容 例如:{"10","xiaoming"}
     * groupBy 分组
     * having 分组的过滤条件
     * orderBy 排序,例如 "id asc"(desc-降序 asc-升序)
     * limit 查询显示的条数(分页,可以不写,默认全部显示,
     * 例如"100"(前100条数据),
     * 也可以只取 前4条，例如"0,4"（从第1条数据开始取，取4条数据，下标从0开始）。
     * 例如"3,4"(从第4条数据开始取，取4条数据))
     *
     *
     * @return
     */
    @Override
   public long addMedicine(medicine drug) {
        SQLiteDatabase writableDatabase=mMedicineDataBaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MedicineDataBaseHelper.MEDICINE_NAME,drug.getName());
        values.put(MedicineDataBaseHelper.DESCRIPTION,drug.getDescription());
        values.put(MedicineDataBaseHelper.DOSE,drug.getDose());
        values.put(MedicineDataBaseHelper.REPEATION,drug.getRepeation());
        long result=writableDatabase.insert(MedicineDataBaseHelper.TABLE_NAME_MEDICINE,null,values);
        writableDatabase.close();
        return result;
    }

    @Override
   public int delMedicineByName(String name) {



        return 0;
    }

    @Override
   public List<medicine> getUserByName(String name) {
        SQLiteDatabase readableDataBase =mMedicineDataBaseHelper.getReadableDatabase();
        //String queueSql= String.format("select * from %s where%s = ?", MedicineDataBaseHelper.TABLE_NAME_MEDICINE, MedicineDataBaseHelper.MEDICINE_NAME);
   //现在支持以name开头的模糊查找
        Cursor cursor=readableDataBase.query(MedicineDataBaseHelper.TABLE_NAME_MEDICINE,null,MedicineDataBaseHelper.MEDICINE_NAME+" LIKE ? ",new String[]{"%"+name+"%"},null,null,null);
        List<medicine> list = new ArrayList<>();
        if(cursor.getCount() > 0)
        {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                medicine drug = new medicine();

                String medicine_name=cursor.getString(cursor.getColumnIndex(MedicineDataBaseHelper.MEDICINE_NAME));
                drug.setName(medicine_name);


                int dose= cursor.getInt(cursor.getColumnIndex(MedicineDataBaseHelper.DOSE));

                drug.setDose(dose);

                int  repeation=cursor.getInt(cursor.getColumnIndex(MedicineDataBaseHelper.REPEATION));
                drug.setRepeation(repeation);


                String description=cursor.getString(cursor.getColumnIndex(MedicineDataBaseHelper.DESCRIPTION));
                drug.setDescription(description);


                list.add(drug);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        readableDataBase.close();
        return list;


    }

 //   @Override
    public List<medicine> listMedicine() {


        SQLiteDatabase readableDataBase =mMedicineDataBaseHelper.getReadableDatabase();
        //查询全部数据
        Cursor cursor =readableDataBase.query(MedicineDataBaseHelper.TABLE_NAME_MEDICINE,null,null,null,null,null,null,null);
        List<medicine> list = new ArrayList<>();
        if(cursor.getCount() > 0)
        {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                medicine drug = new medicine();

                String medicine_name=cursor.getString(cursor.getColumnIndex(MedicineDataBaseHelper.MEDICINE_NAME));
                drug.setName(medicine_name);


                int dose= cursor.getInt(cursor.getColumnIndex(MedicineDataBaseHelper.DOSE));

                drug.setDose(dose);

                int  repeation=cursor.getInt(cursor.getColumnIndex(MedicineDataBaseHelper.REPEATION));
                drug.setRepeation(repeation);


                String description=cursor.getString(cursor.getColumnIndex(MedicineDataBaseHelper.DESCRIPTION));
                drug.setDescription(description);


                list.add(drug);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        readableDataBase.close();

        return list;
    }
    public medicine getmedicinebyname(String name){
        SQLiteDatabase readableDataBase =mMedicineDataBaseHelper.getReadableDatabase();
        //String queueSql= String.format("select * from %s where%s = %s", MedicineDataBaseHelper.TABLE_NAME_MEDICINE, MedicineDataBaseHelper.MEDICINE_NAME,name);
        //现在支持以name开头的模糊查找
        Cursor cursor=readableDataBase.query(MedicineDataBaseHelper.TABLE_NAME_MEDICINE,null,MedicineDataBaseHelper.MEDICINE_NAME+" LIKE ? ",new String[]{"%"+name+"%"},null,null,null);

        medicine drug = new medicine();
        if(cursor.getCount() > 0)
        {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                String medicine_name=cursor.getString(cursor.getColumnIndex(MedicineDataBaseHelper.MEDICINE_NAME));
                drug.setName(medicine_name);
                int dose= cursor.getInt(cursor.getColumnIndex(MedicineDataBaseHelper.DOSE));
                drug.setDose(dose);
                int  repeation=cursor.getInt(cursor.getColumnIndex(MedicineDataBaseHelper.REPEATION));
                drug.setRepeation(repeation);
                String description=cursor.getString(cursor.getColumnIndex(MedicineDataBaseHelper.DESCRIPTION));
                drug.setDescription(description);

                Log.d("drug",drug.getName());
                //移动到下一位
                cursor.moveToNext();
            }
        }
        cursor.close();
        readableDataBase.close();

        return drug;

    }
}
