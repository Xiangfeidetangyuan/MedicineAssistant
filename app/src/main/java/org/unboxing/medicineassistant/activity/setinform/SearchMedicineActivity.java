package org.unboxing.medicineassistant.activity.setinform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import org.unboxing.medicineassistant.activity.interaction.ListAllActivity;
import org.unboxing.medicineassistant.dao.MedicineDao;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.medicine;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SearchMedicineActivity extends AppCompatActivity {
    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);
    private HashMap<String, medicine> checkMap = new HashMap<>();
    private SearchView mSearchView;
    private ListView mListView;
    private SearchMedicineAdapter adapter;
    private MedicineDao me;
    private String userName; //用户名
    private Button notfindmedicine_button;
    private List<medicine> medicinelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);
        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");

        mSearchView= (SearchView)findViewById(R.id.search_view);
        mListView =(ListView)findViewById(R.id.adapter_listview);
        me = new MedicineDao(this);
        medicinelist = me.listMedicine();
        notfindmedicine_button=(Button) findViewById(R.id.notfindmedicine_textview);

        for(medicine item:medicinelist){
            checkMap.put(item.getName(),item);
        }
        adapter = new SearchMedicineAdapter(this,R.layout.medicine_item,medicinelist,userName);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(checkMap.get(query)!=null){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(SearchMedicineActivity.this, "当前搜索不存在", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                if (!TextUtils.isEmpty(newText)){
                    adapter.getFilter().filter(newText);
                }else{
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });

        notfindmedicine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(SearchMedicineActivity.this, DIYInformActivity.class);
               intent.putExtra("userName",userName);
               startActivity(intent);
            }

        });

    }

}
