package org.unboxing.medicineassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.unboxing.medicineassistant.DAO.MedicinePairDao;
import org.unboxing.medicineassistant.DAO.impl.MedicinePairDaoImpl;
import org.unboxing.medicineassistant.R;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ListAllActivity extends AppCompatActivity {


    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    private MedicinePairDao medicinePairDao = new MedicinePairDaoImpl(ListAllActivity.this);
    private SearchView searchView;
    private ListView listView;
    private HashMap<String, String> checkMap = new HashMap<>();
    private ListAllAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        init();
        initArrayList();
        this.setTitle("药物相互作用查询表");
    }

    public void init(){
        listView = findViewById(R.id.listView_medicine);
    }

    public void initArrayList(){
        searchView = findViewById(R.id.sv);
        List<String> list = medicinePairDao.findAllName();
        for(String s:list){
            checkMap.put(s,s);
        }
        Collections.sort(list, CHINA_COMPARE);

        adapter = new ListAllAdapter(this,android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);

        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String medicineName = arg0.getItemAtPosition(arg2).toString();
                Toast.makeText(ListAllActivity.this, "当前药物："+ medicineName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListAllActivity.this, ShowItemActivity.class);
                intent.putExtra("main_medicine", medicineName);
                startActivity(intent);
            }
        });
        searchView.setIconifiedByDefault(false);

        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("输入您想查找的内容");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(checkMap.get(query)!=null){
                    String medicineName = query;
                    Intent intent = new Intent(ListAllActivity.this, ShowItemActivity.class);
                    Toast.makeText(ListAllActivity.this, "当前药物："+medicineName, Toast.LENGTH_SHORT).show();
                    intent.putExtra("main_medicine", medicineName);
                    startActivity(intent);
                }else{
                    Toast.makeText(ListAllActivity.this, "当前搜索不存在", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    adapter.getFilter().filter(newText.toString());
                    //listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });

    }

}
