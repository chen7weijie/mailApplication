package com.example.cwjwj.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.cwjwj.myapplication.adapter.TaskAdapter;
import com.example.cwjwj.myapplication.entity.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskFragment extends Fragment {
    private ListView listView;
    private String responseData;
    private List<Task> taskList;
    private SwipeRefreshLayout taskRefresh;
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
                                TaskAdapter taskAdapter=new TaskAdapter(getActivity(),R.layout.task_item,taskList);
                                listView.setAdapter(taskAdapter);
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
}
