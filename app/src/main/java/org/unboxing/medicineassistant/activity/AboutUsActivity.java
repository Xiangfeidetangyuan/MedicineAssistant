package org.unboxing.medicineassistant.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.unboxing.medicineassistant.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    public void sendFeedbackMessage(View view){
        //获取信息发送邮件
        show();
    }

    private void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.icon).setTitle("反馈成功")
                .setMessage("感谢您的反馈，我们的工作人员会与您联系！").
                        setPositiveButton("知道了", null);
        builder.show();
    }


}