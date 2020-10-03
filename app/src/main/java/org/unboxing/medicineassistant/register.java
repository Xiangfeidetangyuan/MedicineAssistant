package org.unboxing.medicineassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {
    private EditText userNameEdit;
    private EditText pwdEdit1,pwdEdit2,emailEdit;
    private Button registerBtn;
    private String userName,pwd1,pwd2,email;

    private DBhelper dbhelper;
    private SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbhelper = new DBhelper(register.this,"medicine_db");
        userNameEdit = findViewById(R.id.inputUserName);
        pwdEdit1 = findViewById(R.id.inputPwd1);
        pwdEdit2 = findViewById(R.id.inputPwd2);
        emailEdit =findViewById(R.id.inputEmail);
        registerBtn = findViewById(R.id.submitBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = dbhelper.getWritableDatabase();
                userName = userNameEdit.getText().toString();
                pwd1 = pwdEdit1.getText().toString();
                pwd2 = pwdEdit2.getText().toString();
                email = emailEdit.getText().toString();
                if (hasNULL(userName,pwd1,pwd2,email)){
                    Toast.makeText(register.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isSame(pwd1,pwd2)){
                    Toast.makeText(register.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbhelper.isStringDuplicated(mydb,userName,"user","userName")){
                    Toast.makeText(register.this,"用户名重复",Toast.LENGTH_SHORT).show();
                    mydb.close();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("userName",userName);
                values.put("userPwd",pwd1);
                values.put("userEmail",email);
                //数据库执行插入命令
                mydb.insert("user", null, values);
                Log.d("register","插入数据");
                Toast.makeText(register.this,"注册成功",Toast.LENGTH_SHORT).show();
                mydb.close();

                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean isSame(String str1,String str2){
        if (str1.equals(str2)){
            return true;
        }
        else return false;
    }

    public boolean hasNULL(String name,String pwd1,String pwd2,String email){
        if (name.equals(""))
            return true;
        else if (pwd1.equals(""))
            return true;
        else if (pwd2.equals(""))
            return true;
        else if (email.equals(""))
            return true;
        else return false;
    }
}
