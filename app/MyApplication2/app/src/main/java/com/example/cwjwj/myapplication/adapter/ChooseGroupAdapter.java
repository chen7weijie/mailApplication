package com.example.cwjwj.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cwjwj.myapplication.Groups;
import com.example.cwjwj.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseGroupAdapter extends ArrayAdapter<Groups> {
    private int resourceId;
    private List<Groups>  groupsList;

    public ArrayList<Integer> getCs() {
        return cs;
    }

    public void setCs(ArrayList<Integer> cs) {
        this.cs = cs;
    }

    private ArrayList<Integer> cs;


    public int getChoosen() {
        return choosen;
    }

    public void setChoosen(int choosen) {
        this.choosen = choosen;
    }

    private int  choosen;

    public ChooseGroupAdapter(Context context,int textResourceId,List<Groups> objects,ArrayList<Integer> Cs){
        super(context,textResourceId,objects);
        resourceId=textResourceId;
        groupsList=objects;
        cs=Cs;

        choosen=0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Groups group=getItem(position);
        View view;
        viewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new viewHolder();
            viewHolder.groupName=view.findViewById(R.id.choosegroup_name);
            viewHolder.groupChoose=view.findViewById(R.id.choose_group);
            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(viewHolder)view.getTag();
        }
        viewHolder.groupName.setText(group.getName());
        if(viewHolder.groupChoose.isChecked()){
            cs.set(position,1);
        }
        else {
            cs.set(position,0);
        }
        Log.d("Add","chooseList"+cs.get(position));

        return view;
    }
    public class viewHolder{
        public TextView groupName;
        public CheckBox groupChoose;
    }
}
