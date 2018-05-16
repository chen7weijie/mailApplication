package com.example.cwjwj.myapplication.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cwjwj.myapplication.R;
import com.example.cwjwj.myapplication.entity.Address;
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

public class AddressActivity extends AppCompatActivity {
    private List<Address> addressList;
    private ListView listView;
    private String responseData="no";
    private int selectId;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Toolbar toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        listView=findViewById(R.id.address_listview);
        button=findViewById(R.id.change_address);
        getAddress();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressId=String.valueOf(selectId);
                changeAddress(addressId);
            }
        });

    }

    private void getAddress(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url("http://192.168.191.1/selectAddress.php").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseData=response.body().string();
                        addressList=parseJsonToAddress(responseData);
                        Log.d("AddressActivity", responseData);
                        AddressActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddressActivity.this,responseData,Toast.LENGTH_SHORT).show();
                            }
                        });
                        if(response.body()!=null){

                            if(addressList!=null){
                                AddressActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AddressAdapter addressAdapter=new AddressAdapter(AddressActivity.this,R.layout.address_item,addressList);
                                        listView.setAdapter(addressAdapter);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                selectId=addressList.get(position).getId();
                                                AddressAdapter.addressViewHolder viewHolder=(AddressAdapter.addressViewHolder)view.getTag();
                                                viewHolder.checkButton.toggle();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }).start();
    }

    private List<Address> parseJsonToAddress(String jsonData){
        Gson gson=new Gson();
        List<Address> addressList=gson.fromJson(jsonData,new TypeToken<List<Address>>(){}.getType());
        return addressList;
    }

    private void changeAddress(final String addressId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody body=new FormBody.Builder().add("addressId",addressId).build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/changeAddress.php")
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("AddressActivity_aa",response.body().string());
                    }
                });
            }
        }).start();

    }
}
