package org.unboxing.medicineassistant.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.unboxing.medicineassistant.R;

public class ChangePwdActivity extends AppCompatActivity {
    private EditText pwd01;
    private EditText pwd02;
    private Button updateBtn;
    private String userEmail;
    private DBhelper dbhelper = new DBhelper(ChangePwdActivity.this,"medicine_db");
    private SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        Intent i=getIntent();
        this.userEmail =i.getStringExtra("userEmail") ;
        pwd01 = findViewById(R.id.updatePwd1);
        pwd02 = findViewById(R.id.updatePwd2);
        updateBtn = findViewById(R.id.updatePwd);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = dbhelper.getWritableDatabase();
                String pwd1 = pwd01.getText().toString();
                String pwd2 = pwd02.getText().toString();
                if (hasNULL(pwd1,pwd2)){
                    Toast.makeText(ChangePwdActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                    mydb.close();
                    return;
                }
                if (!isSame(pwd1,pwd2)){
                    Toast.makeText(ChangePwdActivity.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
                    mydb.close();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("userPwd", pwd1);
                mydb.update("user", values, "userEmail = ?", new String[]{userEmail});
                Toast.makeText(ChangePwdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                mydb.close();
                Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public boolean isSame(String str1,String str2){
        if (str1.equals(str2)){
            return true;
        }
        else return false;
    }

    public boolean hasNULL(String pwd1,String pwd2){
        if (pwd1.equals(""))
            return true;
        else if (pwd2.equals(""))
            return true;
        else return false;
    }
}
