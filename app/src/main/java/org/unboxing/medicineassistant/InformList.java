package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;


import org.unboxing.medicineassistant.DAO.InformDao;
import org.unboxing.medicineassistant.entity.Inform;

import java.util.List;


public class InformList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrom_list);
        InformDao informdao = new InformDao(this);
        List<Inform> informList = informdao.listInform();
        InformAdapter adapter= new InformAdapter(InformList.this,R.layout.inform_item, informList);
        ListView listView=(ListView) findViewById(R.id.infrom_list_listview);
        listView.setAdapter(adapter);





    }
}
