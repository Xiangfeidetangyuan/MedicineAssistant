package org.unboxing.medicineassistant.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.util.mail.mail;

public class AboutUsActivity extends AppCompatActivity {
    private EditText suggestionEdt;
    private EditText userInfoEdt;
    private String suggestion;
    private String userInfo;
    private String senderEmail = "1145902847@qq.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        suggestionEdt = (EditText) findViewById(R.id.edt_feedback);
        userInfoEdt = (EditText) findViewById((R.id.edt_feedback_user_info));
    }

    public void sendFeedbackMessage(View view) {
        suggestion = suggestionEdt.getText().toString();
        userInfo = userInfoEdt.getText().toString();
        if (suggestion.equals("")) {
            notInput();
            return;
        } else {
            if(userInfo.equals("")){
                userInfo = "用户未填写联系方式";
            }
            String sendMessage = "接到用户反馈如下：\n"+suggestion + "\n用户联系方式:\n"+userInfo;
            mail sendmail = new mail();
            sendmail.send(senderEmail,sendMessage,"用户反馈");
            showSuccessSend();
        }
    }

    private void showSuccessSend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.icon).setTitle("反馈成功")
                .setMessage("感谢您的反馈，我们的工作人员会与您联系！").
                        setPositiveButton("知道了", null);
        builder.show();
    }

    private void notInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.icon).setTitle("请输入反馈信息")
                .setMessage("请输入您的反馈信息").
                        setPositiveButton("知道了", null);
        builder.show();
    }

}