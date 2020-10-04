package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InformControl extends AppCompatActivity {
    private String userName; //用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_control);

        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");

        //设置提醒页面的跳转
        Button btn1=(Button) findViewById(R.id.setInfrom);
        //给btn1绑定监听事件
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent=new Intent(InformControl.this,searchMedicine.class);
                intent.putExtra("userName",userName);
                //启动
                startActivity(intent);
            }
        });

        //提醒列表页面的跳转
        Button btn2=(Button) findViewById(R.id.showinform);
        //给btn1绑定监听事件
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 给bnt2添加点击响应事件
                Intent intent=new Intent(InformControl.this, InformList.class);
                intent.putExtra("userName",userName);
                //启动
                startActivity(intent);
            }
        });


    }
}
