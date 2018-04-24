package com.example.cwjwj.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReceiversAdapter extends ArrayAdapter<Receivers> {
    private int resourceId;

    public ReceiversAdapter(Context context, int textResourceId, List<Receivers> objects){
        super(context,textResourceId,objects);
        this.resourceId=textResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Receivers receiver=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.receiverName=(TextView) view.findViewById(R.id.receiver_name);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();

        }
        viewHolder.receiverName.setText(receiver.getName());
        return view;
    }
    class ViewHolder{
        TextView receiverName;
    }
}
