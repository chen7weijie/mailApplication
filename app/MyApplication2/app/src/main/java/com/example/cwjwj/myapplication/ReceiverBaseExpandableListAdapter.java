package com.example.cwjwj.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ReceiverBaseExpandableListAdapter extends BaseExpandableListAdapter{
    private String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
    private String[][] childStrings = {
            {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
            {"宋江", "林冲", "李逵", "鲁智深"},
            {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"}
    };
    private Context context;
    int groupLayout;
    int childLayout;

    public ReceiverBaseExpandableListAdapter(Context context,int groupLayout,int childLayout){
        this.context=context;
        this.groupLayout=groupLayout;
        this.childLayout=childLayout;
    }

    @Override
    public int getGroupCount() {
        return groupStrings.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childStrings[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupStrings[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childStrings[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null) {view = LayoutInflater.from(context).inflate(groupLayout,parent,false);
        }
        else {
            view=convertView;
        }
        TextView textView=(TextView)view.findViewById(R.id.label_expand_group);
        textView.setText(groupStrings[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null) {view = LayoutInflater.from(context).inflate(childLayout,parent,false);
        }
        else {
            view=convertView;
        }
        TextView textView=(TextView)view.findViewById(R.id.label_expand_content);
        textView.setText(childStrings[groupPosition][childPosition]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
