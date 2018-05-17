package com.example.cwjwj.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;


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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    private DatePickerDialog dpg;
    private String date;
    private TimePickerDialog tpg;
    private String time;
    private List<Groups> groupsList=null;
    private ArrayList<String> dataList=null;
    private String responseData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar=findViewById(R.id.addtask_toolbar);
        setSupportActionBar(toolbar);
        Button chooseDate=findViewById(R.id.datepicker);
        Button chooseTime=findViewById(R.id.timepiker);
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

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int newMonthOfYear;
        newMonthOfYear=monthOfYear+1;
        date = "You picked the following date: "+year+"/"+newMonthOfYear+"/"+dayOfMonth;
        Log.d("AddTaskActivity",date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        time = "You picked the following time: "+hourString+"h"+minuteString+"m"+secondString+"s";
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
                    }
                });
            }
        }).start();
    }
}
