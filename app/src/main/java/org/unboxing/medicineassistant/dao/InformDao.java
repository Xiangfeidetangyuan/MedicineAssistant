package org.unboxing.medicineassistant.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import org.unboxing.medicineassistant.entity.Inform;
import org.unboxing.medicineassistant.util.InformDataBaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InformDao extends IInformDao {
    private static final String TAG ="dao" ;
    private final InformDataBaseHelper mInformDataBaseHelper ;
    public InformDao(Context context) {

        mInformDataBaseHelper = new InformDataBaseHelper(context);
    }



    @Override
   public long addInform(Inform inform) {
        SQLiteDatabase writableDatabase=mInformDataBaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(InformDataBaseHelper.STATUS,inform.getStatus());
        values.put(InformDataBaseHelper.INFORMID,inform.getInformid());
        values.put(InformDataBaseHelper.USERNAME,inform.getUsername());
        values.put(InformDataBaseHelper.CONTENT,inform.getContent());
        values.put(InformDataBaseHelper.HOUR,inform.getHour());
        values.put(InformDataBaseHelper.MINUTE,inform.getMinute());
        values.put(InformDataBaseHelper.TITLE,inform.getTitle());
        values.put(InformDataBaseHelper.BEGINDATE,inform.getBegindate());
        values.put(InformDataBaseHelper.ENDDATE,inform.getEnddate());
        long result=writableDatabase.insert(InformDataBaseHelper.TABLE_NAME_INFROM,null,values);

        writableDatabase.close();
        return result;

    }
    public void updateInform(Inform inform){

        SQLiteDatabase writableDatabase=mInformDataBaseHelper.getWritableDatabase();
        String sql= "Update  "+InformDataBaseHelper.TABLE_NAME_INFROM+" set "+
                InformDataBaseHelper.CONTENT+ " = '"+inform.getContent()+"'"+" , "+InformDataBaseHelper.HOUR+" = "+inform.getHour()+
                ", "+ InformDataBaseHelper.MINUTE+" = "+inform.getMinute()+
                " Where "+InformDataBaseHelper.INFORMID+" = "+inform.getInformid();
        writableDatabase.execSQL(sql);
        writableDatabase.close();

    }

    @Override
    public int delInformById(long id) {
        SQLiteDatabase writableDatabase=mInformDataBaseHelper.getWritableDatabase();
        writableDatabase.execSQL("Delete From "+InformDataBaseHelper.TABLE_NAME_INFROM+" Where "+InformDataBaseHelper.INFORMID+" = "+id);
        writableDatabase.close();
        return 0;
    }

    @Override
   public List<Inform> listInform(String username) {
        SQLiteDatabase readableDataBase =mInformDataBaseHelper.getReadableDatabase();
        //查询全部数据
        Cursor cursor =readableDataBase.query(InformDataBaseHelper.TABLE_NAME_INFROM,null,InformDataBaseHelper.USERNAME+" LIKE ? ",new String[]{"%"+username+"%"},null,null,null);
        List<Inform> list = new ArrayList<>();
        if(cursor.getCount() > 0)
        {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                Inform inform = new Inform();

                int status=cursor.getInt(cursor.getColumnIndex(InformDataBaseHelper.STATUS));
                inform.setStatus(status);
                long informid=cursor.getInt(cursor.getColumnIndex(InformDataBaseHelper.INFORMID));
                inform.setInformid(informid);

                String username2=cursor.getString(cursor.getColumnIndex(InformDataBaseHelper.USERNAME));
                inform.setUsername(username2);

                String content=cursor.getString(cursor.getColumnIndex(InformDataBaseHelper.CONTENT));
                inform.setContent(content);

                int hour= cursor.getInt(cursor.getColumnIndex(InformDataBaseHelper.HOUR));
                inform.setHour(hour);

                int  minute=cursor.getInt(cursor.getColumnIndex(InformDataBaseHelper.MINUTE));
                inform.setMinute(minute);

                String title=cursor.getString(cursor.getColumnIndex(InformDataBaseHelper.TITLE));
                inform.setTitle(title);

                long  begindate=cursor.getLong(cursor.getColumnIndex(InformDataBaseHelper.BEGINDATE));
                inform.setBegindate(begindate);

                long  enddate=cursor.getLong(cursor.getColumnIndex(InformDataBaseHelper.ENDDATE));
                inform.setBegindate(enddate);
                if (new Date().getTime()<=enddate)
                list.add(inform);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        readableDataBase.close();

        return list;
    }

    @Override
   public int setInfromStatus(long id,int status) {
        SQLiteDatabase writableDatabase=mInformDataBaseHelper.getWritableDatabase();
        writableDatabase.execSQL("Update  "+InformDataBaseHelper.TABLE_NAME_INFROM+" Set "+InformDataBaseHelper.STATUS+" = "+status+" Where "+InformDataBaseHelper.INFORMID+" = "+id);
        writableDatabase.close();

        return 0;
    }
}
