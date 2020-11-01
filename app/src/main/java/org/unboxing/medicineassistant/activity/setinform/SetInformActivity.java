package org.unboxing.medicineassistant.activity.setinform;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import org.unboxing.medicineassistant.DAO.InformDao;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.Inform;
import org.unboxing.medicineassistant.entity.medicine;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class SetInformActivity extends AppCompatActivity {

    private static final String TAG = "Setinform";
    private static final int PERMISSION_RESULT_CODE = 1;
    private InformDao informdao;
    private medicine medicinedemo;
    private String userName; //用户名
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    private TimePicker timePicker3;
    private EditText informName;
    private EditText informContext;
    private Spinner spinner;
    private  TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inform);
        informdao = new InformDao(this);
        Intent intent=getIntent();
        this.userName =intent.getStringExtra("userName");


        //获取传入的medicine对象
        medicinedemo = (medicine) getIntent().getSerializableExtra("Medicine");

        //获取实例
        informName = (EditText) findViewById(R.id.infrom_name_textview);
        informContext = (EditText) findViewById(R.id.infrom_context_textview);
        spinner = (Spinner) findViewById(R.id.spinner);
        textView2 = (TextView)findViewById(R.id.time_textview_2);

        timePicker1 = (TimePicker)findViewById(R.id.timepicker1);
        timePicker2 = (TimePicker)findViewById(R.id.timepicker2);
        timePicker3 = (TimePicker)findViewById(R.id.timepicker3);
        timePicker1.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);//设置点击事件不弹键盘
        timePicker1.setHour(8);
        timePicker1.setIs24HourView(true);
        timePicker1.setMinute(30);


        timePicker3.setIs24HourView(true);
        timePicker3.setHour(19);
        timePicker3.setMinute(30);


        informName.setText(medicinedemo.getName());
        informContext.setText(medicinedemo.getDescription() +" 一天"+ medicinedemo.getDose()+"次");
        spinner.setSelection(medicinedemo.getRepeation() - 1);


        switch (medicinedemo.getDose()) {
            case 2:
                timePicker2.setVisibility(View.INVISIBLE);


            case 3:
                timePicker2.setIs24HourView(true);
                timePicker2.setHour(12);
                timePicker2.setMinute(30);
                break;
            default:
                break;
        }
        //正式设置闹钟

        Button bn = (Button) findViewById(R.id.setInfrombutton);
        bn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                initClock();
                Log.d(TAG, "重复" + (int)(spinner.getSelectedItemId() + 1) + "时间："+timePicker1.getHour()+ timePicker1.getMinute());

                long informid1=SetSure_inform(timePicker1.getHour(), timePicker1.getMinute(),informName.getText().toString(),informContext.getText().toString(), ((int) spinner.getSelectedItemId()) + 1);

                // status, int informid, String username, String content, int hour, int ,minute,title，begindate,enddate
                Date begindate =new Date();
                long begin = begindate.getTime();
                Log.d(TAG, begindate.getDate()+":");
                begindate.setHours(timePicker1.getHour());
                begindate.setMinutes(timePicker1.getMinute());
                begindate.setDate(begindate.getDate()+(int)(spinner.getSelectedItemId()+1));
                long end = begindate.getTime();
                Log.d(TAG, begindate.getDate()+":");

                Inform inform1 = new Inform(1, informid1, userName, informContext.getText().toString(), timePicker1.getHour(), timePicker1.getMinute(), informName.getText().toString(),begin,end);


                Log.d(TAG,inform1.getTitle()+";"+inform1.getContent());
                informdao.addInform(inform1);

                long informid3=SetSure_inform(timePicker3.getHour(), timePicker3.getMinute(),informName.getText().toString(),informContext.getText().toString(), ((int) spinner.getSelectedItemId()) + 1);
                Inform inform3 = new Inform(1, informid3, userName, informContext.getText().toString(), timePicker3.getHour(), timePicker3.getMinute(), informName.getText().toString(),begin,end);
                informdao.addInform(inform3);

                if (medicinedemo.getDose() == 3) {

                    long informid2=SetSure_inform(timePicker2.getHour(), timePicker2.getMinute(),informName.getText().toString(),informContext.getText().toString(), ((int) spinner.getSelectedItemId()) + 1);
                    Inform inform2 = new Inform(1, informid2, userName, informContext.getText().toString(), timePicker2.getHour(), timePicker2.getMinute(), informName.getText().toString(),begin,end);
                    informdao.addInform(inform2);
                }
                finish();
                Intent intent=new Intent(SetInformActivity.this, InformListActivity.class);
                intent.putExtra("userName",userName);
                //启动
                startActivity(intent);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public long SetSure_inform(int hour,int minute,String title,String content, int repeation) {
        //前面查询出来的
        long calID = 1;
        //时间创建
        Calendar beginTime = Calendar.getInstance();
        //Month value is 0-based. e.g., 0 for January.

        //获取系统的日期
        Log.d(TAG, "" + beginTime.get(Calendar.YEAR) + "." + beginTime.get(Calendar.MONTH) + "," + beginTime.get(Calendar.DAY_OF_MONTH) + "");
        beginTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH), hour, minute);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH), hour, minute + 2);
        long endMillis = endTime.getTimeInMillis();
        Log.d(TAG, "beginTime -- > " + startMillis+":");
        Log.d(TAG, "endTime -- > " + endMillis);
        //准备好插入事件数据库的内容
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        //开始时间
        values.put(CalendarContract.Events.DTSTART, startMillis);
        //结束时间
        values.put(CalendarContract.Events.DTEND, endMillis);
        //标题
        values.put(CalendarContract.Events.TITLE, title);
        //描述
        values.put(CalendarContract.Events.DESCRIPTION, content);
        //日历ID
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=" + repeation);
        //时间时区
        String timeZone = TimeZone.getDefault().getID();
        Log.d(TAG, "time zone -- > " + timeZone);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);


        if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            long TODO=0;
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return TODO;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        Log.d(TAG,"insert result --- > " + uri);
        long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.d(TAG,"eventID"+eventID);
        return eventID;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initClock() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkCalendarsPermission();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCalendarsPermission() {
        int writeResult = checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
        int readResult = checkSelfPermission(Manifest.permission.READ_CALENDAR);
        if (writeResult != PackageManager.PERMISSION_GRANTED || readResult != PackageManager.PERMISSION_GRANTED) {
            //没有权限,需要申请权限
            //一般先提示用户，如果用户点击了确定，才去请求权限
            //TODO:同学们就展示一个新的界面，然后如果用户点击不再显示，则不要再显示请求权限了，也不要给用户使用程序，因为没意义呀。
            //TODO:这里我就直接上检查权限的代码，在视频里我们会按实际项目流程走，把提示也做出来。
            //请求权限
            Log.d(TAG, "requestPermission....");

            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR}, PERMISSION_RESULT_CODE);
        } else {
            //有权限

        }
    }



}
