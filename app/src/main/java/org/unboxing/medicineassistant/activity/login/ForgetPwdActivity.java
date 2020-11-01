package org.unboxing.medicineassistant.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.util.mail.mail;

import java.util.ArrayList;

public class ForgetPwdActivity extends AppCompatActivity {

    private EditText userEmailEdit;
    private EditText identifyCodeEdit;
    private Button getCodeBtn, checkBtn;
    private String userEmail;
    private int identifyCode,checkCode;
    private DBhelper dBhelper;
    private SQLiteDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_pwd);
        dBhelper = new DBhelper(ForgetPwdActivity.this, "medicine_db");
        userEmailEdit = findViewById(R.id.changePwdEmail);
        identifyCodeEdit = findViewById(R.id.inputIdentifyCode);
        getCodeBtn = findViewById(R.id.getCodeBtn);
        checkBtn = findViewById(R.id.changePwd);

        getCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = userEmailEdit.getText().toString();
                mydb = dBhelper.getWritableDatabase();
                ArrayList<ArrayList<String>> result = dBhelper.getListFromUser(mydb,userEmail,"userEmail");
                if (result.size()!=0) {
                    identifyCode = (int) (Math.random() * (999999 - 100000) + 100000);
                    mail sendmail = new mail();
                    String message = "【验证码】"+identifyCode;
                    sendmail.send(userEmail,message);
                    Toast.makeText(ForgetPwdActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ForgetPwdActivity.this, "不存在此用户", Toast.LENGTH_SHORT).show();
                }
                mydb.close();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkcode = String.valueOf(identifyCodeEdit.getText());
                String tempMail = userEmailEdit.getText().toString();
                if (checkcode.isEmpty()){
                    Toast.makeText(ForgetPwdActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tempMail.isEmpty()){
                    Toast.makeText(ForgetPwdActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkCode = Integer.parseInt(identifyCodeEdit.getText().toString());
                if (checkCode == identifyCode && tempMail.equals(userEmail)){
                    Intent intent = new Intent(ForgetPwdActivity.this, ChangePwdActivity.class);
                    intent.putExtra("userEmail",userEmail);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ForgetPwdActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
