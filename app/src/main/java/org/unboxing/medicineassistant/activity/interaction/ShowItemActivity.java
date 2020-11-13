package org.unboxing.medicineassistant.activity.interaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.unboxing.medicineassistant.dao.MedicinePairDao;
import org.unboxing.medicineassistant.dao.impl.MedicinePairDaoImpl;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.InteractionLevelInfo;
import org.unboxing.medicineassistant.entity.MedicinePair;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ShowItemActivity extends AppCompatActivity {

    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);
    private MedicinePairDao medicinePairDao = new MedicinePairDaoImpl(ShowItemActivity.this);
    private RadioButton rb_all;
    private RadioButton rb4;
    private RadioButton rb3;
    private RadioButton rb2;
    private RadioButton rb1;

    private TextView tv_name;
    private ShowItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        initView();
        initLsit();
        initButtons();
    }

    public void initButtons(){
        RadioGroup rg = findViewById(R.id.RG);
        rb_all = findViewById(R.id.rb_all);
        rb4 = findViewById(R.id.rb4);
        rb3 = findViewById(R.id.rb3);
        rb2 = findViewById(R.id.rb2);
        rb1 = findViewById(R.id.rb1);
        rg.setOnCheckedChangeListener(new getItem());
    }


    private class getItem implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId==rb4.getId()){
                //Toast.makeText(ShowItemActivity.this,"禁忌：严禁同时时服用", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("禁忌");
            }
            if(checkedId==rb3.getId()){
                //Toast.makeText(ShowItemActivity.this,"严重", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("严重");
            }
            if(checkedId==rb2.getId()){
                //Toast.makeText(ShowItemActivity.this,"中度", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("中度");
            }
            if(checkedId==rb1.getId()){
                //Toast.makeText(ShowItemActivity.this,"轻度", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("轻度");
            }
            if(checkedId==rb_all.getId()){
                //Toast.makeText(ShowItemActivity.this,"全部", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("");
            }
        }
    }

    private void initView() {
        tv_name = findViewById(R.id.tv_itemname);
    }

    private void initLsit(){
        List<InteractionLevelInfo> data = new ArrayList<>();
        String medicineName = getIntent().getStringExtra("main_medicine");

        tv_name.setText(medicineName);
        setTitle(medicineName);

        List<MedicinePair> list = medicinePairDao.findByMainMedicine(medicineName);
        List<String> list_4 = new ArrayList<>();
        List<String> list_3 = new ArrayList<>();
        List<String> list_2 = new ArrayList<>();
        List<String> list_1 = new ArrayList<>();
        for(MedicinePair mp:list){
            if(!mp.getPairMedicine().equals("#")){
                if(mp.getLevel().equals("禁忌")){
                    list_4.add(mp.getPairMedicine());
                }
                if(mp.getLevel().equals("严重")){
                    list_3.add(mp.getPairMedicine());
                }
                if(mp.getLevel().equals("中度")){
                    list_2.add(mp.getPairMedicine());
                }
                if(mp.getLevel().equals("轻度")){
                    list_1.add(mp.getPairMedicine());
                }
            }
        }
        Collections.sort(list_4, CHINA_COMPARE);
        Collections.sort(list_3, CHINA_COMPARE);
        Collections.sort(list_2, CHINA_COMPARE);
        Collections.sort(list_1, CHINA_COMPARE);

        ListView listView = findViewById(R.id.ListView);
        for(String s : list_4){
            data.add(new InteractionLevelInfo(s, "禁忌"));
        }
        for(String s : list_3){
            data.add(new InteractionLevelInfo(s, "严重"));
        }
        for(String s : list_2){
            data.add(new InteractionLevelInfo(s, "中度"));
        }
        for(String s : list_1){
            data.add(new InteractionLevelInfo(s, "轻度"));
        }

        adapter = new ShowItemAdapter(this, data);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
            }
        });
    }

}
