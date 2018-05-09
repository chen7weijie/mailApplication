package com.example.cwjwj.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends ArrayAdapter<Groups>{
    private int resourceId;

    public GroupsAdapter(Context context, int textResourceId, List<Groups> objects){
        super(context,textResourceId,objects);
        resourceId=textResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Groups group=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.groupName=(TextView)view.findViewById(R.id.group_name);
            viewHolder.man=view.findViewById(R.id.man);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.man.setImageResource(R.drawable.man);
        viewHolder.groupName.setText(group.getName());
        return view;
    }
    class ViewHolder{
        TextView groupName;
        ImageView man;

    }
}
