package com.example.cwjwj.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cwjwj.myapplication.entity.TaskInfo;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TaskInfoActivity extends AppCompatActivity {
    private TextView title;
    private TextView content;
    private TextView name;
    private TextView date;
    private TextView time;
    private TextView tiaTitle;
    private TextView tiaContent;
    private TextView tiaName;
    private TextView tiaDate;
    private TextView tiaStatus;
    private ImageView titleImg;
    private ImageView contentImg;
    private ImageView nameImg;
    private ImageView dateImg;
    private TaskInfo taskInfo;
    private int id;
    private String taskId;
    private Switch status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        Toolbar toolbar=findViewById(R.id.taskinfo_toolbar);
        title=findViewById(R.id.task_title);
        content=findViewById(R.id.task_content);
        name=findViewById(R.id.taskinfo_name);
        date=findViewById(R.id.taskinfo_date);
        time=findViewById(R.id.taskinfo_time);
        status=findViewById(R.id.status);
        tiaTitle=findViewById(R.id.ati_title);
        tiaContent=findViewById(R.id.ati_content);
        tiaName=findViewById(R.id.ati_name);
        tiaDate=findViewById(R.id.ati_date);
        tiaStatus=findViewById(R.id.ati_status);
        titleImg=findViewById(R.id.title_img);
        contentImg=findViewById(R.id.content_img);
        nameImg=findViewById(R.id.ren_img);
        dateImg=findViewById(R.id.time_img);
        id=getIntent().getIntExtra("taskId",0);
        taskId=String.valueOf(id);

        Log.d("Task",taskId);

        setSupportActionBar(toolbar);
        getInfo(taskId);

    }

    private void  getInfo(final String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder().add("taskId",id).build();
                Request request=new Request.Builder().url("http://192.168.191.1/getTaskInfo.php").post(formBody).build();
                try{
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d("task",responseData);
                    taskInfo=parseData(responseData);
                    Log.d("title",taskInfo.getTitle());
                    TaskInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(taskInfo.getTitle());
                            name.setText(taskInfo.getName());
                            date.setText(taskInfo.getSend_date());
                            time.setText(taskInfo.getSend_time());
                            content.setText(taskInfo.getContent());
                            if(taskInfo.getStatus()==1){
                                status.setChecked(true);
                            }
                            status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if(isChecked){
                                        //发送请求，把状态置为1
                                        changStatus(taskId,taskInfo.getStatus());
                                    }
                                    else {
                                        changStatus(taskId,taskInfo.getStatus());
                                    }
                                }
                            });
                        }
                    });
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private TaskInfo parseData(String responseData){
        Gson gson=new Gson();
        TaskInfo taskInfo=gson.fromJson(responseData,TaskInfo.class);
        return taskInfo;
    }

    private void changStatus(final String id,final int status){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                String s=String.valueOf(status);
                FormBody formBody=new FormBody.Builder()
                        .add("status",s)
                        .add("id",id)
                        .build();
                Request request=new Request.Builder().url("http://192.168.191.1/ChangeStatus.php")
                        .post(formBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d("responseData",responseData);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
