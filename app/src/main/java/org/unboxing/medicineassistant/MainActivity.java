package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.unboxing.medicineassistant.util.CopyDateBaseFile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText userNameEdit;
    private EditText passwordEdit;
    private Button loginBtn, registerBtn,fogPwdBtn;
    private String userName, pwd;

    private DBhelper dbhelper;
    private SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //复制数据库
        String path = this.getFilesDir().getAbsolutePath();
        String rootpath = path.substring(0, path.lastIndexOf("/"));
        String datapath = rootpath + "/" + "databases";
        System.out.println("路：" + datapath);
        String name_db = "medicine_db";
        CopyDateBaseFile.copy(this, name_db, datapath, name_db);
        userNameEdit = findViewById(R.id.inputUserName);
        passwordEdit = findViewById(R.id.inputPwd);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        fogPwdBtn = findViewById(R.id.fogPwdBtn);

        dbhelper = new DBhelper(MainActivity.this, "medicine_db");
        mydb = dbhelper.getWritableDatabase();
        Cursor cursor = mydb.query("user", new String[]{"userName", "userPwd", "userEmail"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        String textview_data = "\n";
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("userName"));
            String pwd = cursor.getString(cursor.getColumnIndex("userPwd"));
            String email = cursor.getString(cursor.getColumnIndex("userEmail"));
            textview_data = textview_data + "\n" + name + " " + pwd + " " + email + " ";
        }
        Log.d("result", textview_data);
        // 关闭游标，释放资源
        cursor.close();
        mydb.close();
        Toast.makeText(MainActivity.this, textview_data, Toast.LENGTH_LONG).show();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEdit.getText().toString();
                pwd = passwordEdit.getText().toString();
                mydb = dbhelper.getWritableDatabase();
                ArrayList<ArrayList<String>> temp = dbhelper.getListFromUser(mydb,userName,"userName");
                if (temp != null && temp.get(0).get(1).equals(pwd) ){
                    Intent intent = new Intent(MainActivity.this, MedicineInform.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }
        else
            Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();


            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = userNameEdit.getText().toString();
                pwd = passwordEdit.getText().toString();
                Toast.makeText(MainActivity.this, "点击了注册按钮", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);


//                mydb = dbhelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("userName",userName);
//                values.put("userPwd",pwd);
//                values.put("userEmail","aaa.com");
//                //数据库执行插入命令
//                mydb.insert("user", null, values);
//                Log.d("bbb","插入数据");
//
//                Cursor cursor = mydb.query("user", new String[]{"userName","userPwd","userEmail"}, null, null, null, null, null);
//                //利用游标遍历所有数据对象
//                //为了显示全部，把所有对象连接起来，放到TextView中
//                String textview_data = "";
//                while(cursor.moveToNext()){
//                    String name = cursor.getString(cursor.getColumnIndex("userName"));
//                    String pwd = cursor.getString(cursor.getColumnIndex("userPwd"));
//                    String email = cursor.getString(cursor.getColumnIndex("userEmail"));
//                    textview_data = textview_data + "\n" + name + " " + pwd + " " + email+ " " ;
//                }
//                Log.d("result",textview_data);
//                // 关闭游标，释放资源
//                cursor.close();
//
//                mydb.close();
            }
        });

        fogPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, fogetPwd.class);
                startActivity(intent);
            }
        });
    }
}
