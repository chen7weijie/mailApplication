package com.example.cwjwj.myapplication.info_manage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cwjwj.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddInfoActivity extends AppCompatActivity {
    private String flag="no";
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    private String chooseType=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        Toolbar toolbar=findViewById(R.id.addinfo_toolbar);
        setSupportActionBar(toolbar);
        TextView titleCue=findViewById(R.id.addinfo_cue);
        TextView contentCue=findViewById(R.id.addcontent_cue);
        TextView desCue=findViewById(R.id.adddes_cue);
        final EditText inputTitle=findViewById(R.id.input_info_title);
        final EditText inputContent=findViewById(R.id.input_info_content);
        final EditText inputDes=findViewById(R.id.input_info_des);
        Spinner spinner=findViewById(R.id.spinner);
        Button button=findViewById(R.id.add_info);
        initData();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chooseType=adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputTitle.getText().toString()!=null&&inputContent.getText().toString()!=null&&inputDes.getText().toString()!=null&&chooseType!=null){
                    String title=inputTitle.getText().toString();
                    String content=inputContent.getText().toString();
                    String des=inputDes.getText().toString();
                    addInfo(title,content,des,chooseType);
                }
                else {
                    Toast.makeText(AddInfoActivity.this,"请输入相关数据!",Toast.LENGTH_SHORT).show();
                }
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

    public void initData(){
        dataList=new ArrayList<String>();
        dataList.add("立即发送");
        dataList.add("每周一次");
        dataList.add("每日发送");
        dataList.add("定时发送");
    }

    public void addInfo(final String title,final String content,final String des,final String chooseType){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("title",title)
                        .add("content",content)
                        .add("des",des)
                        .add("chooseType",chooseType)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/addInfo.php")
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("AddInfoActivity",response.body().string());
                    }
                });
            }
        }).start();
    }
}
