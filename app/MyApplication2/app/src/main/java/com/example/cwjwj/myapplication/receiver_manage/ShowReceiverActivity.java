package com.example.cwjwj.myapplication.receiver_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cwjwj.myapplication.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowReceiverActivity extends AppCompatActivity {
    private ImageView nameImg;
    private ImageView emailImg;
    private ImageView phoneImg;
    private ImageView sexImg;
    private TextView nameCue;
    private TextView emailCue;
    private TextView phoneCue;
    private TextView sexCue;
    private TextView name;
    private EditText email;
    private EditText phone;
    private TextView sex;
    private Button save;
    private String getName;
    private String getEmail;
    private String getPhone;
    private String getSex;
    private String id;
    private String newEmail;
    private String newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_receiver);
        Toolbar toolbar=findViewById(R.id.show_re_toolbar);
        setSupportActionBar(toolbar);
        nameImg=findViewById(R.id.name_img);
        emailImg=findViewById(R.id.email_img);
        phoneImg=findViewById(R.id.phone_img);
        sexImg=findViewById(R.id.reSex_img);
        nameCue=findViewById(R.id.reNameCue);
        emailCue=findViewById(R.id.reMailCue);
        phoneCue=findViewById(R.id.rePhoneCue);
        sexCue=findViewById(R.id.reSexCue);
        name=findViewById(R.id.reName);
        email=findViewById(R.id.reMail);
        phone=findViewById(R.id.rePhone);
        sex=findViewById(R.id.reSex);
        save=findViewById(R.id.reSave);
        Intent intent=getIntent();
        id=String.valueOf(intent.getIntExtra("id",0));
        getName=intent.getStringExtra("name");
        getEmail=intent.getStringExtra("email");
        getPhone=intent.getStringExtra("phone");
        getSex=intent.getStringExtra("sex");
        name.setText(getName);
        email.setText(getEmail);
        phone.setText(getPhone);
        sex.setText(getSex);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEmail=email.getText().toString();
                newPhone=phone.getText().toString();
                updateReceiver(id,newEmail,newPhone);
            }
        });
    }

    private void updateReceiver(final String id,final String email,final String phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("id",id)
                        .add("email",email)
                        .add("phone",phone)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/updateReceiver.php")
                        .post(formBody)
                        .build();
                try{
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    if(responseData.equals("success")){
                        ShowReceiverActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowReceiverActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
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
