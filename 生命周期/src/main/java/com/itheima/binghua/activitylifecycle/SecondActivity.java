package com.itheima.binghua.activitylifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        System.out.println("2onCreate被调用了！");
       
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        System.out.println("2onStart被调用了！");

    }

    @Override
    protected void onStop() {
        System.out.println("2onStop被调用了！，activity被完全遮盖住了");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("2onDestroy被调用了！activity被摧毁了");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        System.out.println("2onPause被调用了！activity失去焦点");
        super.onPause();
    }

    @Override
    protected void onResume() {
        System.out.println("2onResume被调用了！activity得到焦点");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("2onRestart被调用了！activity重新被激活");
    }

}
