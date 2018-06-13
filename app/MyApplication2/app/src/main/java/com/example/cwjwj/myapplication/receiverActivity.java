package com.example.cwjwj.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cwjwj.myapplication.receiver_manage.AddReceiverActivity;
import com.example.cwjwj.myapplication.receiver_manage.SendMailActivity;
import com.example.cwjwj.myapplication.receiver_manage.ShowReceiverActivity;
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
    private int receiverPostion;
    private Receivers chooseReceiver;
    private String groupname;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        Toolbar toolbar = findViewById(R.id.receiver_toolbar);
        setSupportActionBar(toolbar);
        listView=findViewById(R.id.receiver_listview);
        Intent intent=getIntent();
        groupname=intent.getStringExtra("groupName");
        Log.d("receiverActivity",groupname);
        getReceiverWithOkhttp(groupname);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    flag=data.getStringExtra("data_return");
                    Log.d("receiverActivity",flag);
                    if(flag.equals("success")){
                        getReceiverWithOkhttp(groupname);
                    }
                }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.receiver_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_receiver){
            Intent intent=new Intent(receiverActivity.this, AddReceiverActivity.class);
            intent.putExtra("groupName",groupname);
            startActivityForResult(intent,1);
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:{
                enterShowReInfo(chooseReceiver.getId(),chooseReceiver.getName(),chooseReceiver.getEmail(),chooseReceiver.getSex(),chooseReceiver.getPhone_number());
            }break;
            case 1:enterSendMail(chooseReceiver.getName());break;
            case 2: {
                if(receiversList.remove(receiverPostion)!=null){
                    ReceiversAdapter receiversAdapter= new ReceiversAdapter(receiverActivity.this, R.layout.receiver_item, receiversList);
                    listView.setAdapter(receiversAdapter);
                    listView.deferNotifyDataSetChanged();
                }
                deleteReceiver(chooseReceiver.getName());
            }
        }
        return super.onContextItemSelected(item);
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
                                                registerForContextMenu(listView);
                                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                    @Override
                                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                        receiverPostion=position;
                                                        chooseReceiver=receiversList.get(position);
                                                        return false;
                                                    }
                                                });
                                                listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                                                    @Override
                                                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                                                        menu.setHeaderTitle("请选择");
                                                        menu.add(0, 0, 1, "查看详细信息");
                                                        menu.add(0, 1, 1, "发送邮件");
                                                        menu.add(0, 2, 1, "删除");
                                                    }
                                                });
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

    private void enterSendMail(String receiverName){
        Intent intent=new Intent(receiverActivity.this, SendMailActivity.class);
        intent.putExtra("receiverName",receiverName);
        startActivity(intent);

    }

    private void deleteReceiver(final String receiverName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody body=new FormBody.Builder().add("receiverName",receiverName).build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/deleteReceiver.php")
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("receiverActivity",response.body().string());
                    }
                });
            }
        }).start();
    }

    private void enterShowReInfo(int id,String name,String email,String sex,String phone){
        Intent intent=new Intent(receiverActivity.this, ShowReceiverActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        intent.putExtra("sex",sex);
        intent.putExtra("phone",phone);
        startActivity(intent);

    }
}
