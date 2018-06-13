 package com.example.cwjwj.myapplication;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.cwjwj.myapplication.group_manage.GroupInfoActivity;
import com.example.cwjwj.myapplication.group_manage.SendGroupMailActivity;
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

 public class Tab1Fragment extends Fragment{

     private String responseGroupData;
     private List<Groups> groupsList;
     private ListView listView;
     private int itemId;
     private Groups chooseGroup;
     private int chooseId;
     private int groupPostion;
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.tab1_fragment,container,false);

     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
         listView=view.findViewById(R.id.group_listview);

         getGroupsWithOkHttp();


     }

     @Override
     public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);
  }

     public boolean onContextItemSelected(MenuItem item) {
         Log.d("sss", "onCreateContextMenu: "+itemId);
         switch (item.getItemId()){
             case 0:{
                 if(chooseGroup.getStatus()==1) {
                     enterSendGroupMail(itemId);
                 }
                 else {
                     getActivity().runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(getActivity(),"该分组目前不允许发送消息！",Toast.LENGTH_SHORT).show();
                         }
                     });
                 }
             }break;
             case 1:{
                 if(groupsList.remove(groupPostion)!=null){
                     GroupsAdapter groupsAdapter=new GroupsAdapter(getActivity(),R.layout.group_item,groupsList);
                     listView.setAdapter(groupsAdapter);
                     listView.deferNotifyDataSetChanged();
                 }
                 deleteGroup(chooseGroup.getName());
             }break;
             case 2:{final EditText et = new EditText(getActivity());
                 new AlertDialog.Builder(getActivity()).setTitle("请输入新的分组名称")
                         .setIcon(android.R.drawable.sym_def_app_icon)
                         .setView(et)
                         .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 //按下确定键后的事件
                                 Toast.makeText(getActivity(), et.getText().toString(),Toast.LENGTH_LONG).show();
                                 String newGroupName=et.getText().toString();
                                 updateName(itemId,newGroupName);
                                 groupsList.get(groupPostion).setName(newGroupName);
                                 GroupsAdapter groupsAdapter=new GroupsAdapter(getActivity(),R.layout.group_item,groupsList);
                                 listView.setAdapter(groupsAdapter);
                                 listView.deferNotifyDataSetChanged();

                             }
                         }).setNegativeButton("取消",null).show();}break;
             case 6:{
                 updateGroupStatus(chooseGroup.getId(),chooseGroup.getStatus());
                 getGroupsWithOkHttp();
             }break;
         }
         return super.onContextItemSelected(item);
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
                                     listView.setAdapter(groupsAdapter);
                                     registerForContextMenu(listView);
                                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                             Intent intent=new Intent(getActivity(),receiverActivity.class);
                                             String groupName=groupsList.get(position).getName();
                                             intent.putExtra("groupName",groupName);
                                             startActivity(intent);
                                         }
                                     });
                                     listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                         @Override
                                         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                             groupPostion=position;
                                             itemId=groupsList.get(position).getId();
                                             chooseId=groupsList.get(position).getId();
                                             chooseGroup=groupsList.get(position);
                                             return false;
                                         }
                                     });
                                     listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                                         @Override
                                         public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                                             menu.setHeaderTitle("请选择");

                                             menu.add(0, 0, 1, "发送即时邮件");
                                             menu.add(0, 1, 1, "删除");
                                             menu.add(0, 2, 1, "修改名称");
                                             menu.add(0,6,1,"修改状态");

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





     private void deleteGroup(final String groupName){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 OkHttpClient client=new OkHttpClient();
                 FormBody  formBody=new FormBody.Builder().add("groupName",groupName).build();
                 Request request=new Request.Builder()
                         .url("http://192.168.191.1/deleteGroup.php")
                         .post(formBody)
                         .build();
                 client.newCall(request).enqueue(new Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }

                     @Override
                     public void onResponse(Call call, Response response) throws IOException {
                         Log.d("Tab1Fragment",response.body().string());

                     }
                 });
             }
         }).start();
     }

     private void enterGroupinfo(Groups group){
         Intent intent=new Intent(getActivity(), GroupInfoActivity.class);
         intent.putExtra("groupId",group.getId());
         intent.putExtra("groupName",group.getName());
         startActivity(intent);
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

     public void alert_edit(View view){
         final EditText et = new EditText(getActivity());
         new AlertDialog.Builder(getActivity()).setTitle("请输入消息")
                 .setIcon(android.R.drawable.sym_def_app_icon)
                 .setView(et)
                 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         //按下确定键后的事件
                         Toast.makeText(getActivity(), et.getText().toString(),Toast.LENGTH_LONG).show();
                     }
                 }).setNegativeButton("取消",null).show();
     }

     public void updateGroupStatus(final int chooseId,final int status){
         final String id=String.valueOf(chooseId);
         final String newStatus=String.valueOf(status);
         new Thread(new Runnable() {
             @Override
             public void run() {
                 OkHttpClient client=new OkHttpClient();
                 FormBody formbody=new FormBody.Builder()
                         .add("id",id)
                         .add("status",newStatus)
                         .build();
                 Request request=new Request.Builder()
                         .url("http://192.168.191.1/updateGroupStatus.php")
                         .post(formbody)
                         .build();
                 try {
                     Response response=client.newCall(request).execute();
                     final String data=response.body().string();
                     getActivity().runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             if(data.equals("修改成功！"))
                             Toast.makeText(getActivity(),"修改成功！",Toast.LENGTH_SHORT).show();
                         }
                     });

                 }
                 catch (IOException e){
                     e.printStackTrace();
                 }

             }
         }).start();

     }

     public void updateName(final int chooseId,final String name){
         final String id=String.valueOf(chooseId);
         new Thread(new Runnable() {
             @Override
             public void run() {
                 OkHttpClient client=new OkHttpClient();
                 FormBody formbody=new FormBody.Builder()
                         .add("id",id)
                         .add("name",name).build();
                 Request request=new Request.Builder()
                         .url("http://192.168.191.1/updateGroupName.php")
                         .post(formbody)
                         .build();
                 try {
                     Response response=client.newCall(request).execute();
                     String data=response.body().string();
                     getActivity().runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(getActivity(),"data",Toast.LENGTH_SHORT).show();
                         }
                     });

                 }
                 catch (IOException e){
                     e.printStackTrace();
                 }

             }
         }).start();

     }

     public void enterSendGroupMail(int groupId){
         Intent intent=new Intent(getActivity(), SendGroupMailActivity.class);
         intent.putExtra("groupId",groupId);
         startActivity(intent);
     }

 }
