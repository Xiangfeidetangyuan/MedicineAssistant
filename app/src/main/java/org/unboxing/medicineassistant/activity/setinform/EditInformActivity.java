package org.unboxing.medicineassistant.activity.setinform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import org.unboxing.medicineassistant.dao.InformDao;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.Inform;

import java.util.Calendar;

import static java.lang.StrictMath.abs;

public class EditInformActivity extends AppCompatActivity {

    private Inform informdemo;
    private TimePicker infrom_time;
    private EditText infrom_context;
    private EditText infrom_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinform);
        final InformDao informdao = new InformDao(this);
        //获取传入的inform对象
        informdemo = (Inform) getIntent().getSerializableExtra("inform");
        infrom_time = (TimePicker) findViewById(R.id.infrom_time_2);
        infrom_context = (EditText) findViewById(R.id.infrom_context_2);
        infrom_title = (EditText) findViewById(R.id.infrom_title);
        //初始化
        infrom_time.setIs24HourView(true);
        infrom_time.setHour(informdemo.getHour());
        infrom_time.setMinute(informdemo.getMinute());

        infrom_context.setText(informdemo.getContent());
        infrom_title.setText(informdemo.getTitle());
        Button bn = (Button) findViewById(R.id.button_edit);
        bn.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                         //更改数据库
                                         informdemo.setContent(infrom_context.getText().toString());
                                         informdemo.setHour(infrom_time.getHour());
                                         informdemo.setMinute(infrom_time.getMinute());
                                         informdemo.setTitle(infrom_title.getText().toString());
                                         informdao.updateInform(informdemo);



                                         long enddate = informdemo.getEnddate();
                                         long now = Calendar.getInstance().getTimeInMillis();
                                         long repeation = abs((enddate-now)/(24*60*60*1000));

                                         Log.d("debug","repeation："+repeation);
                                         //更改日历
                                          ContentResolver cr = getContentResolver();
                                          ContentValues values = new ContentValues();
                                      Calendar beginTime = Calendar.getInstance();
                                      //Month value is 0-based. e.g., 0 for January.
                                      //获取系统的日期
                                      beginTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH),infrom_time.getHour(),infrom_time.getMinute());
                                      long startMillis = beginTime.getTimeInMillis();
                                      Calendar endTime = Calendar.getInstance();
                                      endTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH), infrom_time.getHour(),infrom_time.getMinute());
                                      long endMillis = endTime.getTimeInMillis();

                                        //开始时间
                                       values.put(CalendarContract.Events.DTSTART, startMillis);
                                       //结束时间
                                       values.put(CalendarContract.Events.DTEND,endMillis);
                                       //标题
                                        values.put(CalendarContract.Events.TITLE, infrom_title.getText().toString());
                                       //描述
                                       values.put(CalendarContract.Events.DESCRIPTION,infrom_context.getText().toString());
                                      values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=" + repeation);
                                      Uri updateUri = null;
                                      updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, informdemo.getInformid());
                                      int rows = cr.update(updateUri,values, null, null);
                                      finish();

                                  }
                              }
        );

    }
}