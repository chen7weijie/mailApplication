package com.example.cwjwj.myapplication.receiver_manage;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendMailActivity extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private TextView titleCue;
    private TextView contentCue;
    private String responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        Toolbar toolbar=findViewById(R.id.send_email_toolbar);
        setSupportActionBar(toolbar);
        titleCue=findViewById(R.id.title_cue);
        contentCue=findViewById(R.id.content_cue);
        content=findViewById(R.id.input_content);
        title=findViewById(R.id.input_title);
        Button send=findViewById(R.id.send_email);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTitle=title.getText().toString();
                String inputContent=content.getText().toString();
                String receiverName=getIntent().getStringExtra("receiverName");
                if(inputTitle.length()!=0&&inputContent.length()!=0&&receiverName!=null){
                    sendEmail(inputTitle,inputContent,receiverName);
                }
            }
        });
    }
    private void sendEmail(final String title,final String content,final String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("receiverName",name)
                        .add("title",title)
                        .add("content",content)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/sendMail.php")
                        .post(formBody)
                        .build();
                try{
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(!responseData.equals("Message has been sent.")){
                        Toast.makeText(SendMailActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SendMailActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                /*client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("SendMailActivity",response.body().string());
                        responseData=response.body().string();

                    }
                });*/
            }
        }).start();
    }
}
