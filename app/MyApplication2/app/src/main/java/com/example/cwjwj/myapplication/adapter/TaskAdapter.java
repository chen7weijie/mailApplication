package com.example.cwjwj.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.cwjwj.myapplication.R;
import com.example.cwjwj.myapplication.entity.Task;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private int resourceId;

    public TaskAdapter(Context context,int textResourceId,List<Task> objects){
        super(context,textResourceId,objects);
        resourceId=textResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.taskImg=view.findViewById(R.id.task);
            viewHolder.msgTilte=view.findViewById(R.id.msg_title);
            viewHolder.groupName=view.findViewById(R.id.task_groupname);
            viewHolder.status=view.findViewById(R.id.status);
            //viewHolder.sendDate=view.findViewById(R.id.send_date);
            //viewHolder.sendTime=view.findViewById(R.id.send_time);
            //viewHolder.status= view.findViewById(R.id.status_switch);
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.msgTilte.setText("消息标题:"+task.getTitle()+" 发送对象:"+task.getName());
        viewHolder.groupName.setText("发送日期"+task.getSend_date()+" 发送时间"+task.getSend_time());
        //viewHolder.sendDate.setText("发送日期"+task.getSend_date());
       // viewHolder.sendTime.setText("发送时间"+task.getSend_time());
        if(task.getStatus()==1){
            viewHolder.taskImg.setImageResource(R.drawable.task);
            viewHolder.status.setText("等待执行");
            //viewHolder.status.setVisibility(View.INVISIBLE);
            //viewHolder.status.setChecked(true);
        }
        else if(task.getStatus()==0){
            viewHolder.taskImg.setImageResource(R.drawable.task_pause);
            viewHolder.status.setText("暂停");

        }
        else {
            viewHolder.taskImg.setImageResource(R.drawable.task_pause);
            viewHolder.status.setText("已完成");
        }
        return view;
    }

   public class ViewHolder{
        ImageView taskImg;
        TextView msgTilte;
        TextView groupName;
        TextView status;
       // TextView sendDate;
        //TextView sendTime;
       // Switch status;

    }
}
