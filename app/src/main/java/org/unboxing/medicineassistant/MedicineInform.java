package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;




public class MedicineInform extends AppCompatActivity {




    private static final String TAG = "ceshi";
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inform);

        //复制数据库
        String path = this.getFilesDir().getAbsolutePath();
        String rootpath = path.substring(0, path.lastIndexOf("/"));
        String datapath = rootpath + "/" + "databases";
        System.out.println("路：" + datapath);
        String name = "medicine_db";
        CopyDateBaseFile.copy(this, name, datapath, name);


        //设置提醒页面的跳转
        Button btn1=(Button) findViewById(R.id.infrom_control);
        //给btn1绑定监听事件
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent=new Intent(MedicineInform.this,InformControl.class);
                //启动
                startActivity(intent);
            }
        });


    }
}
