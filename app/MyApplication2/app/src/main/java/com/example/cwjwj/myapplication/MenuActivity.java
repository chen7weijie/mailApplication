package com.example.cwjwj.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cwjwj.myapplication.adapter.AddressActivity;
import com.example.cwjwj.myapplication.group_manage.AddGroupActivity;
import com.example.cwjwj.myapplication.info_manage.AddInfoActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Fragment> fragments;
    private MyAdapter myAdapter;
    private ArrayList<String> titles;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout= findViewById(R.id.tab);
        ViewPager viewPager= findViewById(R.id.viewpager);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragments=new ArrayList<>();
        fragments.add(new Tab1Fragment());
        fragments.add(new MessageFragment());
        fragments.add(new TaskFragment());
        titles=new ArrayList<>();
        titles.add("消息接收组管理");
        titles.add("消息管理");
        titles.add("消息任务管理");

        viewPager.setOffscreenPageLimit(3);

        myAdapter=new MyAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    flag=data.getStringExtra("data_return");
                    Log.d("MessageFragmentFlag",flag);
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.nav_mail){
            enterAddInfo();
        }
        else if(id==R.id.nav_addgroup){
            enterAddgroup();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mail) {

        }
        else if(id==R.id.nav_setting){//更换系统邮箱地址
            enterAddress();
        }
        else if(id==R.id.nav_logout){
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //进入添加分组的活动
    public void enterAddgroup(){
        Intent intent=new Intent(MenuActivity.this, AddGroupActivity.class);
        startActivity(intent);
    }
    //进入更改系统邮箱的活动
    public void enterAddress(){
        Intent intent=new Intent(MenuActivity.this,AddressActivity.class);
        startActivityForResult(intent,1);
    }
    //进入添加消息的活动
    public void enterAddInfo(){
        Intent intent=new Intent(MenuActivity.this, AddInfoActivity.class);
        startActivityForResult(intent,1);
    }
    //进入添加任务的活动
    public void enterAddTask(){
        Intent intent=new Intent(MenuActivity.this,AddTaskActivity.class);
        startActivity(intent);
    }
}
