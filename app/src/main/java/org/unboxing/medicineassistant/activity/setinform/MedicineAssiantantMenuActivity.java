package org.unboxing.medicineassistant.activity.setinform;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.activity.AboutUsActivity;
import org.unboxing.medicineassistant.activity.interaction.ListAllActivity;


public class MedicineAssiantantMenuActivity extends AppCompatActivity {

    private String userName; //用户名

    public void getMainInstruction(View view){
        show();
    }

    private void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.icon).setTitle("用药助手使用说明")
                .setMessage("本程序为用药助手程序是一款为老年人设计的用药程序\n选择相应模块即可使用相应功能\n1.用药禁忌查询功能：选择一种药品可以查看药品的服用禁忌（与之存在相互作用的药品）\n2.服药提醒设置功能：您选择本程序预设的药品，程序自动在手机日历中设置提醒，用户也可以自行设置自定义药品").
                        setPositiveButton("知道了", null);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i=getIntent();
        this.userName =i.getStringExtra("userName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inform);
        TextView tvUserName  =findViewById(R.id.tv_user_info_main);
        tvUserName.setText(userName);
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
