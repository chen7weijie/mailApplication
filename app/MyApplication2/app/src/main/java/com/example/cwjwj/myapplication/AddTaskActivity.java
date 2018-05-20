package com.example.cwjwj.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    private DatePickerDialog dpg;
    private String date;
    private TimePickerDialog tpg;
    private String time;
    private List<Groups> groupsList=null;
    private ArrayList<String> dataList;
    private String responseData;
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private String groupName;
    private String infoId;
    private String chooseData="你选择的发送时间为：";
    private TextView showData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        infoId=getIntent().getStringExtra("infoId");
        Toolbar toolbar=findViewById(R.id.addtask_toolbar);
        setSupportActionBar(toolbar);
        Button chooseDate=findViewById(R.id.datepicker);
        Button chooseTime=findViewById(R.id.timepiker);
        Button addTask=findViewById(R.id.add_task);
        spinner=findViewById(R.id.group_spinner);
        showData=findViewById(R.id.show_choose_data);
        dataList=new ArrayList<String>();
        showData.setText(chooseData);
        getGroups();
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now=Calendar.getInstance();
                dpg=DatePickerDialog.newInstance(
                        AddTaskActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpg.setVersion(DatePickerDialog.Version.VERSION_2);
                dpg.setAccentColor("#0000FF");
                dpg.show(getFragmentManager(), "Datepickerdialog");

            }
        });
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                tpg=TimePickerDialog.newInstance(
                        AddTaskActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                tpg.show(getFragmentManager(), "Timepickerdialog");
                tpg.setAccentColor("#0000FF");
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date!=null&&time!=null&&groupName!=null) {
                     addTask(date,time,groupName,infoId);
                }
                else
                {
                    Toast.makeText(AddTaskActivity.this,"请设置时间或日期",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int newMonthOfYear;
        newMonthOfYear=monthOfYear+1;
        date = year+"/"+newMonthOfYear+"/"+dayOfMonth;
        chooseData=chooseData+date;

        Log.d("AddTaskActivity",date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        time = hourString+":"+minuteString+":"+secondString;
        chooseData=chooseData+" "+time;
        showData.setText(chooseData);
        Log.d("AddTaskActivity",time);
    }

    private List<Groups> parseJsonToGroups(String jsonData){
        Gson gson=new Gson();
        List<Groups> groupsList=gson.fromJson(jsonData,new TypeToken<List<Groups>>(){}.getType());
        return groupsList;
    }

    public void getGroups(){
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
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.body()!=null){
                            responseData=response.body().string();
                            groupsList=parseJsonToGroups(responseData);
                            Log.d("AddTaskActivity",groupsList.get(0).getName());

                        }
                        AddTaskActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0;i<groupsList.size();i++){
                                    dataList.add(groupsList.get(i).getName());
                                }
                                adapter=new ArrayAdapter<String>(AddTaskActivity.this,android.R.layout.simple_spinner_item,dataList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        groupName=adapter.getItem(position);
                                        Log.d("AddTaskActivity",groupName);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public void addTask(final String date,final String time,final String groupName,final String infoId){

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                FormBody formBody=new FormBody.Builder()
                        .add("date",date)
                        .add("time",time)
                        .add("groupName",groupName)
                        .add("infoId",infoId)
                        .build();
                Request request=new Request.Builder()
                        .url("http://192.168.191.1/addTask.php")
                        .post(formBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    //showResponse(responseData);
                    if(responseData.equals("success")){
                        AddTaskActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddTaskActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        AddTaskActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddTaskActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
