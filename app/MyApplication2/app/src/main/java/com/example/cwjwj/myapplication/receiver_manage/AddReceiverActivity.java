package com.example.cwjwj.myapplication.receiver_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cwjwj.myapplication.MenuActivity;
import com.example.cwjwj.myapplication.R;
import com.example.cwjwj.myapplication.group_manage.AddGroupActivity;
import com.example.cwjwj.myapplication.receiverActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddReceiverActivity extends AppCompatActivity {
    private EditText input_name;
    private EditText input_email;
    private EditText input_des;
    private TextView add_name_cue;
    private TextView add_email_cue;
    private TextView add_des_cue;
    private RadioGroup sexGroup;
    private String groupName;
    private String flag="no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receiver);
        Toolbar toolbar=findViewById(R.id.add_receiver_toolbar);
        setSupportActionBar(toolbar);
        groupName=getIntent().getStringExtra("groupName");
        add_name_cue=findViewById(R.id.add_receiver_name);
        add_email_cue=findViewById(R.id.add_receiver_email);
        add_des_cue=findViewById(R.id.add_receiver_des);
        input_name=findViewById(R.id.input_receiver_name);
        input_email=findViewById(R.id.input_receiver_email);
        input_des=findViewById(R.id.input_receiver_des);
        sexGroup=findViewById(R.id.rg_main_sex);
        Button add=findViewById(R.id.add_receiver_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId=sexGroup.getCheckedRadioButtonId();
                RadioButton radioButton=findViewById(checkId);
                String sex=radioButton.getText().toString();
                String name=input_name.getText().toString();
                String email=input_email.getText().toString();
                String des=input_des.getText().toString();
                addReceiver(groupName,name,email,sex,des);
                
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("data_return",flag);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void addReceiver(final String groupName, final String name, final String email, final String sex, final String des){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("groupName",groupName)
                        .add("name",name)
                        .add("email",email)
                        .add("sex",sex)
                        .add("des",des)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/addReceiver.php")
                        .post(formBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    final String responseData=response.body().string();
                    //showResponse(responseData);
                    if(responseData.equals("success")){
                        flag="success";
                        AddReceiverActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddReceiverActivity.this,"添加接受者成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        flag="defealt";
                        AddReceiverActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddReceiverActivity.this,"添加接受者失败！"+responseData,Toast.LENGTH_SHORT).show();
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
