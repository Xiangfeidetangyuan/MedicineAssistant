package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import org.unboxing.medicineassistant.DAO.InformDao;
import org.unboxing.medicineassistant.entity.Inform;

import java.util.List;


public class InformList extends AppCompatActivity {
    private String userName; //用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrom_list);
        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");
        InformDao informdao = new InformDao(this);
        List<Inform> informList = informdao.listInform(userName);
        InformAdapter adapter= new InformAdapter(InformList.this,R.layout.inform_item, informList,userName);
        ListView listView=(ListView) findViewById(R.id.infrom_list_listview);
        listView.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.new_inform_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent(InformList.this,searchMedicine.class);
                intent.putExtra("userName",userName);
                //启动
                startActivity(intent);
                finish();
            }
        });




    }
}
