package com.example.cwjwj.myapplication.group_manage;

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

import com.example.cwjwj.myapplication.MenuActivity;
import com.example.cwjwj.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddGroupActivity extends AppCompatActivity {
    private TextView add_name_cue;
    //private TextView add_des_cue;
    private EditText input_name;
    //private EditText input_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Toolbar toolbar=findViewById(R.id.addgroup_toolbar);
        setSupportActionBar(toolbar);
        add_name_cue=findViewById(R.id.add_group_name);
        //add_des_cue=findViewById(R.id.add_group_des);
        input_name=findViewById(R.id.input_group_name);
       // input_description=findViewById(R.id.input_group_des);
        Button add=findViewById(R.id.add_group);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=input_name.getText().toString();
                //String description=input_description.getText().toString();
                addGroup(name);
            }
        });
    }
    private void addGroup(final String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("groupName",name)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/addGroup.php")
                        .post(formBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    //showResponse(responseData);
                    if(responseData.equals("success")){
                        Intent intent=new Intent(AddGroupActivity.this,MenuActivity.class);
                        startActivity(intent);
                    }
                    else{
                       AddGroupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddGroupActivity.this,"添加分组失败！",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
