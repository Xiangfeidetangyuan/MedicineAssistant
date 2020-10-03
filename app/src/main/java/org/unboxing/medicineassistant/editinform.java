package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.unboxing.medicineassistant.DAO.InformDao;
import org.unboxing.medicineassistant.entity.Inform;

import java.util.Calendar;
import java.util.Date;

import static java.lang.StrictMath.abs;

public class editinform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinform);
        final InformDao informdao = new InformDao(this);
        //获取传入的inform对象
        final Inform informdemo = (Inform) getIntent().getSerializableExtra("inform");
        final EditText infrom_time = (EditText) findViewById(R.id.infrom_time_2);
        final EditText infrom_context = (EditText) findViewById(R.id.infrom_context_2);
        final EditText infrom_title = (EditText) findViewById(R.id.infrom_title);
        infrom_time.setText(informdemo.getHour() + ":" + informdemo.getMinute());
        infrom_context.setText(informdemo.getContent());
        infrom_title.setText(informdemo.getTitle());
        Button bn = (Button) findViewById(R.id.button_edit);
        bn.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                         //更改数据库
                                         informdemo.setContent(infrom_context.getText().toString());
                                         informdemo.setHour(Integer.parseInt(infrom_time.getText().toString().substring(0, infrom_time.getText().toString().indexOf(":"))));
                                         informdemo.setMinute(Integer.parseInt(infrom_time.getText().toString().substring(infrom_time.getText().toString().indexOf(":") + 1)));
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
                                      beginTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH),Integer.parseInt(infrom_time.getText().toString().substring(0, infrom_time.getText().toString().indexOf(":"))) , Integer.parseInt(infrom_time.getText().toString().substring(infrom_time.getText().toString().indexOf(":") + 1)));
                                      long startMillis = beginTime.getTimeInMillis();
                                      Calendar endTime = Calendar.getInstance();
                                      endTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH), Integer.parseInt(infrom_time.getText().toString().substring(0, infrom_time.getText().toString().indexOf(":"))), Integer.parseInt(infrom_time.getText().toString().substring(infrom_time.getText().toString().indexOf(":") + 1)));
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