package com.example.cwjwj.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class receiverActivity extends AppCompatActivity {

    private String responseData;
    private List<Receivers> receiversList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        listView=findViewById(R.id.receiver_listview);
        Intent intent=getIntent();
        String data=intent.getStringExtra("groupName");
        Log.d("receiverActivity",data);
        getReceiverWithOkhttp(data);
    }

    private void getReceiverWithOkhttp(final String groupName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder().add("groupName",groupName).build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/getReceiversByName.php")
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.body()!=null) {
                                responseData = response.body().string();
                                if(responseData.equals("[]")){
                                    receiverActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(receiverActivity.this,"该分组下暂无接收者！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else {
                                    receiversList = parseJsonToReceivers(responseData);
                                    Log.d("receiverActivity", receiversList.get(0).getName());
                                    if (receiversList != null) {
                                        receiverActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ReceiversAdapter receiversAdapter = new ReceiversAdapter(receiverActivity.this, R.layout.receiver_item, receiversList);
                                                listView.setAdapter(receiversAdapter);
                                            }
                                        });

                                    }
                                }

                        }

                    }
                });
            }
        }).start();
    }

    private List<Receivers> parseJsonToReceivers(String jsonData){
        Gson gson=new Gson();
        List<Receivers> receiversList=gson.fromJson(jsonData,new TypeToken<List<Receivers>>(){}.getType());
        return receiversList;
    }
}
