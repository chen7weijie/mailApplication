 package com.example.cwjwj.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

 public class Tab1Fragment extends Fragment{
     //private TextView messages;
     //private TextView groupMessage;
     private String responseData;
     private String responseGroupData;
     private String responseReceiverData;
     private List<Users> usersList;
     private List<Groups> groupsList;
     private List<Receivers> receiversList;
     private ListView listView;
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.tab1_fragment,container,false);
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
         listView=view.findViewById(R.id.group_listview);
         getGroupsWithOkHttp();


         //messages= view.findViewById(R.id.users_message);
         //groupMessage=view.findViewById(R.id.groups_message);
         //sendRequestWithOkHttp();
         //getReceiversWithOkHttp();
         //getGroupsWithOkHttp();


     }

     @Override
     public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);

//         sendRequestWithOkHttp();
  }

     private void sendRequestWithOkHttp(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 OkHttpClient client=new OkHttpClient();
                 Request request=new Request.Builder().url("http://192.168.191.1/testSelect.php").build();
                 client.newCall(request).enqueue(new Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }

                     @Override
                     public void onResponse(Call call, final Response response) throws IOException {
                         if (response.body() != null) {
                             responseData=response.body().string();
                             usersList=parseJson(responseData);
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     //messages.setText(usersList.get(0).getName());
                                 }
                             });
                            // parseJSONWithGSON(responseData);

                         }
                     }
                 });
             }
         }).start();
     }

     private void getGroupsWithOkHttp(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 OkHttpClient client=new OkHttpClient();
                 Request request=new Request.Builder().url("http://192.168.191.1/allGroup.php").build();
                 client.newCall(request).enqueue(new Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }
                     @Override
                     public void onResponse(Call call, final Response response) throws IOException {
                         if (response.body() != null) {
                             responseGroupData=response.body().string();
                             groupsList=parseJsonToGroups(responseGroupData);// parseJSONWithGSON(responseData);
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     GroupsAdapter groupsAdapter=new GroupsAdapter(getActivity(),R.layout.group_item,groupsList);
                                     listView.setAdapter(groupsAdapter);//messages.setText(receiversList.get(0).getName());
                                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                             Groups group=groupsList.get(position);

                                             Intent intent=new Intent(getActivity(),receiverActivity.class);
                                             intent.putExtra("groupName",group.getName());
                                             startActivity(intent);
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

     private void getReceiversWithOkHttp(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 OkHttpClient client=new OkHttpClient();
                 Request request=new Request.Builder().url("http://192.168.191.1/allReceiver.php").build();
                 client.newCall(request).enqueue(new Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }

                     @Override
                     public void onResponse(Call call, final Response response) throws IOException {
                         if (response.body() != null) {
                             responseReceiverData=response.body().string();
                             receiversList=parseJsonToReceivers(responseReceiverData);
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     //messages.setText(receiversList.get(0).getName());
                                 }
                             });
                             // parseJSONWithGSON(responseData);

                         }
                     }
                 });
             }
         }).start();
     }


     private void parseJSONWithGSON(String jsonData){
         Gson gson=new Gson();
         List<Users> usersList=gson.fromJson(jsonData,new TypeToken<List<Users>>(){}.getType());
         for(Users user:usersList){
             Log.d("MainActivity","id is "+user.getId());
             Log.d("MainActivity","name is "+user.getName());
             Log.d("MainActivity","password is "+user.getPassword());
         }
     }

     private List<Users> parseJson(String jsonData){
         Gson gson=new Gson();
         List<Users> usersList=gson.fromJson(jsonData,new TypeToken<List<Users>>(){}.getType());
         return usersList;
     }

     private List<Groups> parseJsonToGroups(String jsonData){
         Gson gson=new Gson();
         List<Groups> groupsList=gson.fromJson(jsonData,new TypeToken<List<Groups>>(){}.getType());
         return groupsList;
     }

     private List<Receivers> parseJsonToReceivers(String jsonData){
         Gson gson=new Gson();
         List<Receivers> receiversList=gson.fromJson(jsonData,new TypeToken<List<Receivers>>(){}.getType());
         return receiversList;
     }


 }
