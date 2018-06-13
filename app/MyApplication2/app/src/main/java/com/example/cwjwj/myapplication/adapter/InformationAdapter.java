package com.example.cwjwj.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cwjwj.myapplication.R;
import com.example.cwjwj.myapplication.entity.Information;

import java.util.List;

public class InformationAdapter extends ArrayAdapter<Information>{
    private int resourceId;

    public InformationAdapter(Context context,int textSourceId,List<Information> objects){
        super(context,textSourceId,objects);
        resourceId=textSourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Information information=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.infoImg=view.findViewById(R.id.info);
            viewHolder.title=view.findViewById(R.id.info_title);
            viewHolder.type=view.findViewById(R.id.infotype);
            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.infoImg.setImageResource(R.drawable.info);
        viewHolder.title.setText("标题:"+information.getTitle());
        if(information.getType().equals("timing")) {
            viewHolder.type.setText(" 消息类型:" + "定时发送");
        }
        else if(information.getType().equals("week")){
            viewHolder.type.setText(" 消息类型:" + "每周一次");
        }
        else {
            viewHolder.type.setText(" 消息类型:" + "每天一次");
        }
        return view;
    }

}

    class ViewHolder{
        ImageView infoImg;
        TextView title;
        TextView type;
    }
