package com.example.cwjwj.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.cwjwj.myapplication.adapter.TaskAdapter;
import com.example.cwjwj.myapplication.entity.Task;
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

public class TaskFragment extends Fragment {
    private ListView listView;
    private String responseData;
    private List<Task> taskList;
    private SwipeRefreshLayout taskRefresh;
    private Task chooseTask;
    private int taskChoosePosition=-1;
    private int chooseId=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.task_listview);
        taskRefresh=view.findViewById(R.id.task_refresh);
        getTask();
        taskRefresh.setColorSchemeResources(R.color.colorPrimary);
        taskRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }



   /* public boolean onContextItemSelected(MenuItem item) {
        Log.d("ssb6"," "+chooseId);
        Log.d("ssb7"," "+taskChoosePosition);
        switch (item.getItemId()){
            case 6:{
            if(taskChoosePosition!=-1&&chooseId!=0){
                TaskAdapter taskAdapter=new TaskAdapter(getActivity(),R.layout.task_item,taskList);
                taskList.remove(taskChoosePosition);
                listView.setAdapter(taskAdapter);
                listView.deferNotifyDataSetChanged();
                //deleteTask(chooseId);
            }
            }break;
            case 7:break;
        }
        return true;
    }*/

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
                        getTask();
                        taskRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void getTask(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url("http://192.168.191.1/allTask.php").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseData=response.body().string();
                        taskList=parseData(responseData);
                        Log.d("TaskFragment",taskList.get(0).getSend_date());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final TaskAdapter taskAdapter=new TaskAdapter(getActivity(),R.layout.task_item,taskList);
                                listView.setAdapter(taskAdapter);
                                //registerForContextMenu(listView);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        taskChoosePosition=position;
                                        chooseTask=taskList.get(position);
                                        chooseId=chooseTask.getId();
                                        Log.d("ssb4"," "+chooseId);
                                        Log.d("ssb5"," "+taskChoosePosition);
                                        Intent intent=new Intent(getActivity(),TaskInfoActivity.class);
                                        intent.putExtra("taskId",chooseId);
                                        startActivity(intent);
                                    }
                                });
                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                        taskChoosePosition=position;
                                        chooseTask=taskList.get(position);
                                        chooseId=chooseTask.getId();
                                        Log.d("ssb4"," "+chooseId);
                                        Log.d("ssb5"," "+taskChoosePosition);
                                        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                                        dialog.setTitle("提示");
                                        dialog.setMessage("确定删除该任务吗？");
                                        dialog.setCancelable(false);
                                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deleteTask(chooseId);
                                                taskList.remove(position);
                                                listView.setAdapter(taskAdapter);
                                                listView.deferNotifyDataSetChanged();

                                            }
                                        });
                                        dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        dialog.show();
                                        return true;
                                    }
                                });
                               /* listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                                    @Override
                                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                                        menu.setHeaderTitle("请选择");
                                        menu.add(0, 6, 1, "删除该任务");
                                        menu.add(0, 7, 1, "改变任务状态");

                                    }
                                });*/

                            }
                        });

                    }
                });
            }
        }).start();
    }

    private List<Task> parseData(String data){
        Gson gson=new Gson();
        List<Task> taskList=gson.fromJson(data,new TypeToken<List<Task>>(){}.getType());
        return  taskList;
    }

    public void deleteTask(final int chooseId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String id=String.valueOf(chooseId);
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("taskId",id)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/deleteTask.php")
                        .post(formBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Log.d("task delete",responseData);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
