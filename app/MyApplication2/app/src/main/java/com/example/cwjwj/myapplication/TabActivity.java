package com.example.cwjwj.myapplication;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    private MyAdapter myAdapter;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        TabLayout tabLayout= findViewById(R.id.tab);
        ViewPager viewPager= findViewById(R.id.viewpager);
        FloatingActionButton fb=findViewById(R.id.fab);

        fragments=new ArrayList<>();
        fragments.add(new Tab1Fragment());
        fragments.add(new MessageFragment());
        fragments.add(new MessageFragment());
        titles=new ArrayList<>();
        titles.add("消息接收者");
        titles.add("消息发布");
        titles.add("软件管理");

        viewPager.setOffscreenPageLimit(3);

        myAdapter=new MyAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
