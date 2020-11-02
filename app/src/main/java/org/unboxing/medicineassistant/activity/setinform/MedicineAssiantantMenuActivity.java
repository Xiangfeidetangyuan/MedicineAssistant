package org.unboxing.medicineassistant.activity.setinform;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.activity.AboutUsActivity;
import org.unboxing.medicineassistant.activity.interaction.ListAllActivity;


public class MedicineAssiantantMenuActivity extends AppCompatActivity {

    private String userName; //用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inform);

        //设置提醒页面的跳转
        Button btn1=(Button) findViewById(R.id.infrom_control);
        //给btn1绑定监听事件
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent=new Intent(MedicineAssiantantMenuActivity.this, InformListActivity.class);
                intent.putExtra("userName",userName);
                //启动
                startActivity(intent);
            }
        });

        //
        Button query=(Button) findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent(MedicineAssiantantMenuActivity.this, ListAllActivity.class);
                startActivity(intent);
            }
        });

        Button aboutUsButton = (Button)findViewById(R.id.aboutus_button);
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MedicineAssiantantMenuActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });


    }
}
