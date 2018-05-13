package com.example.cwjwj.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar=findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        Button login=(Button)findViewById(R.id.login);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        message=(TextView)findViewById(R.id.message);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName=name.getText().toString();
                String inputPassWord=password.getText().toString();
                loginWithOkhttp(inputName,inputPassWord);
            }
        });
    }

    private void loginWithOkhttp(final String name, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody formbody=new FormBody.Builder().add("name",name).add("password",password).build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/login.php")
                        .post(formbody)
                        .build();

                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    //showResponse(responseData);
                    if(responseData.equals("成功登陆")){
                        Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
                        startActivity(intent);
                    }
                    else{
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                message.setText(response);
            }
        });
    }
}
