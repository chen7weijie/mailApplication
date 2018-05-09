package com.example.cwjwj.myapplication.group_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cwjwj.myapplication.Groups;
import com.example.cwjwj.myapplication.R;

import java.util.List;

import okhttp3.OkHttpClient;

public class GroupInfoActivity extends AppCompatActivity {
    private TextView groupName;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        groupName=findViewById(R.id.show_group_name);
        description=findViewById(R.id.show_group_description);
        int id= getIntent().getIntExtra("groupId",0);
        String name= getIntent().getStringExtra("groupName");
        String content=getIntent().getStringExtra("description");
        Log.d("bbb", "onCreateContextMenu: "+id);
        Log.d("ccc",name);
        groupName.setText(name);
        description.setText(content);


    }

    private void getGroup(final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();

            }
        }).start();
    }


}
