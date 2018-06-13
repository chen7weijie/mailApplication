package com.example.cwjwj.myapplication.info_manage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class ShowInfoActivity extends AppCompatActivity {
    private ImageView titleImg;
    private TextView titleCue;
    private EditText titleText;
    private ImageView contentImg;
    private TextView contentCue;
    private EditText contentText;
    private TextView typeCue;
    private TextView showType;
    private String title;
    private String content;
    private String type;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        Toolbar toolbar=findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);
        titleImg=findViewById(R.id.title_img);
        titleCue=findViewById(R.id.ati_title);
        titleText=findViewById(R.id.new_title);
        titleText.setText("sss");
        contentImg=findViewById(R.id.content_img);
        contentCue=findViewById(R.id.ati_content);
        contentText=findViewById(R.id.new_content);
        typeCue=findViewById(R.id.type_cue);
        showType=findViewById(R.id.show_type);
        save=findViewById(R.id.save_change);
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        type=getIntent().getStringExtra("type");
        titleText.setText(title);
        contentText.setText(content);
        if(type.equals("timing")) {
            showType.setText("定时发送");
        }
        else if(type.equals("week")){
            showType.setText("每周一次");
        }
        else{
            showType.setText("每日一次");
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=getIntent().getStringExtra("id");
                String newTitle=titleText.getText().toString();
                String newContent=contentText.getText().toString();
                updateInfo(id,newTitle,newContent);
            }
        });
    }

    private void updateInfo(final String id,final String title,final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("id",id)
                        .add("title",title)
                        .add("content",content)
                        .build();
                Request request=new Request.Builder().url("http://192.168.191.1/updateInfo.php")
                        .post(formBody)
                        .build();
                try{
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d("ShowInfoActivity",responseData);
                    if(responseData.equals("success!")){
                        ShowInfoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowInfoActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
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
