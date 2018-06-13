package com.example.cwjwj.myapplication.group_manage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cwjwj.myapplication.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendGroupMailActivity extends AppCompatActivity {

    private TextView titleCue;
    private TextView contentCue;
    private EditText inputTitle;
    private EditText inputContent;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_group_mail);
        Toolbar toolbar=findViewById(R.id.sendgroup_toolbar);
        setSupportActionBar(toolbar);
        titleCue=findViewById(R.id.sendGroupTitle);
        contentCue=findViewById(R.id.sendGroupContent);
        inputTitle=findViewById(R.id.inputGroupTitle);
        inputContent=findViewById(R.id.inputGroupContent);
        send=findViewById(R.id.groupSend);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title=inputTitle.getText().toString();
                String content=inputContent.getText().toString();
                int groupId=getIntent().getIntExtra("groupId",0);
                String id=String.valueOf(groupId);
                if(title.length()>0&&content.length()>0){
                    sendMail(id,title,content);
                }
                else {
                    Toast.makeText(SendGroupMailActivity.this,"请输入邮件相关内容！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void sendMail(final String groupId,final String title,final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("id",groupId)
                        .add("title",title)
                        .add("content",content)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/sendGroupMail.php")
                        .post(formBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String data=response.body().string();
                    if(data.equals("Message has been sent.")){
                        SendGroupMailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SendGroupMailActivity.this,"发送成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        SendGroupMailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SendGroupMailActivity.this,"发送失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
