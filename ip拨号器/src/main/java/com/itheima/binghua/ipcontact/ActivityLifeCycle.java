package com.itheima.binghua.ipcontact;

import android.app.Activity;
import android.os.Bundle;

public class ActivityLifeCycle extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_life_cycle);
        System.out.println("onCreate被调用了！进行一系列的初始化！");
        
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        System.out.println("onStart被调用了！");
        
    }

    @Override
    protected void onStop() {
        System.out.println("onStop被调用了！，activity被完全遮盖住了");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy被调用了！activity被摧毁了");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause被调用了！activity失去焦点");
        super.onPause();
    }

    @Override
    protected void onResume() {
        System.out.println("onResume被调用了！activity得到焦点");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart被调用了！activity重新被激活");
    }
}
