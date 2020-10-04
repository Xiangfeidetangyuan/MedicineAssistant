package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import org.unboxing.medicineassistant.DAO.MedicineDao;
import org.unboxing.medicineassistant.entity.medicine;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class searchMedicine extends AppCompatActivity {
    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);
    private HashMap<String, medicine> checkMap = new HashMap<>();
    private SearchView mSearchView;
    private ListView mListView;
    private  ListAllAdapter adapter;
    private MedicineDao me;
    private String userName; //用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);
        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");

        mSearchView= (SearchView)findViewById(R.id.search_view);
        mListView =(ListView)findViewById(R.id.adapter_listview);
        me = new MedicineDao(this);
        final List<medicine> medicinelist = me.listMedicine();

        ArrayList<String> li = new ArrayList<>();
        for(medicine item:medicinelist){
            li.add(item.getName());
            checkMap.put(item.getName(),item);
        }

        Collections.sort(li,CHINA_COMPARE);
        adapter = new ListAllAdapter(this,R.layout.medicine_item,li,userName);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(checkMap.get(query)!=null){
                    String tempName = checkMap.get(query).getName();
                    Intent intent = new Intent(searchMedicine.this, SetInform.class);
                    medicine medicinedemo = me.getmedicinebyname(tempName);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Medicine",medicinedemo);
                    intent.putExtras(bundle);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }else{
                    Toast.makeText(searchMedicine.this, "当前搜索不存在", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    adapter.getFilter().filter(newText.toString());
                }else{
                    mListView.clearTextFilter();
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });



    }
    public String getUserName() {
        return userName;
    }
}
