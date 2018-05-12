package com.example.cwjwj.myapplication;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;


public class TabActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    private MyAdapter myAdapter;
    private ArrayList<String> titles;
    private DrawerLayout mDrawerLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        mDrawerLayout=findViewById(R.id.drawer_layout);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout= findViewById(R.id.tab);
        ViewPager viewPager= findViewById(R.id.viewpager);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.bars);

        }


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
