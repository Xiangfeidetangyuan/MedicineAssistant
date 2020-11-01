package org.unboxing.medicineassistant.activity.login;

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

import org.unboxing.medicineassistant.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText userNameEdit;
    private EditText pwdEdit1, pwdEdit2, emailEdit;
    private Button registerBtn;
    private String userName, pwd1, pwd2, email;

    private LoginDBhelper dbhelper;
    private SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbhelper = new LoginDBhelper(RegisterActivity.this, "medicine_db");
        userNameEdit = findViewById(R.id.inputUserName);
        pwdEdit1 = findViewById(R.id.inputPwd1);
        pwdEdit2 = findViewById(R.id.inputPwd2);
        emailEdit = findViewById(R.id.inputEmail);
        registerBtn = findViewById(R.id.submitBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = dbhelper.getWritableDatabase();
                userName = userNameEdit.getText().toString();
                pwd1 = pwdEdit1.getText().toString();
                pwd2 = pwdEdit2.getText().toString();
                email = emailEdit.getText().toString();
                if (hasNULL(userName, pwd1, pwd2, email)) {
                    Toast.makeText(RegisterActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isSame(pwd1, pwd2)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbhelper.isStringDuplicated(mydb, userName, "user", "userName")) {
                    Toast.makeText(RegisterActivity.this, "用户名重复", Toast.LENGTH_SHORT).show();
                    mydb.close();
                    return;
                }
                if (dbhelper.isStringDuplicated(mydb, email, "user", "userEmail")) {
                    Toast.makeText(RegisterActivity.this, "邮箱已被注册", Toast.LENGTH_SHORT).show();
                    mydb.close();
                    return;
                }
                if (!isVaildEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isVaildPwd(pwd1)) {
                    Toast.makeText(RegisterActivity.this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("userName", userName);
                values.put("userPwd", pwd1);
                values.put("userEmail", email);
                //数据库执行插入命令
                mydb.insert("user", null, values);
                Log.d("register", "插入数据");
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                mydb.close();

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean isSame(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        } else return false;
    }

    public boolean isVaildEmail(String email) {
        String rule = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public boolean isVaildPwd(String pwd) {
        if (pwd.length() < 6) {
            return false;
        } else return true;
    }

    public boolean hasNULL(String name, String pwd1, String pwd2, String email) {
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
