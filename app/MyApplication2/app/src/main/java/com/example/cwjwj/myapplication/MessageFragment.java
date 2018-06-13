package com.example.cwjwj.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cwjwj.myapplication.adapter.InformationAdapter;
import com.example.cwjwj.myapplication.entity.Information;
import com.example.cwjwj.myapplication.info_manage.ShowInfoActivity;
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

import static android.app.Activity.RESULT_OK;

public class MessageFragment extends Fragment{
    private ListView listView;
    private SwipeRefreshLayout swipeRefresh;
    private List<Information> informationList;
    private String responseData;
    private Information chooseInfo=null;
    private int choosePostion=-1;
    private int chooseId=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.message_fragment,container,false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.information_listview);
        swipeRefresh=view.findViewById(R.id.message_refresh);
        getInformation();
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getInformation();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public boolean onContextItemSelected(MenuItem item) {
        Log.d("sssb2"," "+chooseId);
        Log.d("sssb3"," "+choosePostion);
        Log.d("sss", "MessageFragment "+item.getItemId());
        switch (item.getItemId()){
            case 3:{
                if(chooseId!=0&&choosePostion!=-1) {
                    String id = String.valueOf(chooseId);
                    enterShowInfo(id,informationList.get(choosePostion).getTitle(),informationList.get(choosePostion).getContent(),
                            informationList.get(choosePostion).getType());
                }
            }break;
            case 4:{
                if(chooseId!=0&&choosePostion!=-1) {
                    String id = String.valueOf(chooseId);
                    deleteInfo(id);
                    informationList.remove(choosePostion);
                    InformationAdapter adapter=new InformationAdapter(getActivity(),R.layout.info_item,informationList);
                    listView.setAdapter(adapter);
                    listView.deferNotifyDataSetChanged();
                }
            }break;
            case 5:{
                if(chooseId!=0&&choosePostion!=-1) {
                    String id = String.valueOf(chooseId);
                    enterAddTask(id);
                }
            };break;
        }
        return true;
    }

    //从服务器获取information并绑定listview
    private void getInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url("http://192.168.191.1/getInformation.php").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.body()!=null){
                            responseData=response.body().string();
                            informationList=parseJson(responseData);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    InformationAdapter adapter=new InformationAdapter(getActivity(),R.layout.info_item,informationList);
                                    listView.setAdapter(adapter);
                                    registerForContextMenu(listView);
                                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                            choosePostion=position;
                                            chooseInfo=informationList.get(position);
                                            Log.d("sssb"," "+chooseInfo.getId());
                                            chooseId=chooseInfo.getId();
                                            return false;
                                        }
                                    });
                                    listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                                        @Override
                                        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                                            menu.setHeaderTitle("请选择");
                                            menu.add(0, 3, 1, "查看详细信息");
                                            menu.add(0, 4, 1, "删除");
                                            menu.add(0, 5, 1, "设置发送任务");
                                        }
                                    });


                                }
                            });

                        }
                    }
                });
            }
        }).start();
    }

    //解析数据
    private List<Information> parseJson(String data){
        Gson gson=new Gson();
        List<Information> list=gson.fromJson(data,new TypeToken<List<Information>>(){}.getType());
        return list;
    }
    //删除information
    private void deleteInfo(final String infoId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder().add("infoId",infoId).build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/deleteInfo.php")
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("MessageFragment",response.body().string());
                    }
                });
            }
        }).start();
    }

    private void enterAddTask(String id){
        Intent intent=new Intent(getActivity(),AddTaskActivity.class);
        intent.putExtra("infoId",id);
        startActivity(intent);

    }

    private void enterShowInfo(String id,String title,String content,String type){
        Intent intent=new Intent(getActivity(), ShowInfoActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
