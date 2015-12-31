package com.itheima.binghua.activitylifecycle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * 时间：2015年12月3日 13:37:03
 * 练习：横竖屏切换时，要想保证当前页面不被摧毁重新建立，需要在
 * 清单文件中的activity中添加android:configChanges="orientation|keyboardHidden|screenSize"这行代码！！
 * 
 * 12-03 00:31:30.398: I/System.out(6159): onCreate被调用了！进行一系列的初始化！
 12-03 00:31:30.478: I/System.out(6159): onStart被调用了！
 12-03 00:31:30.478: I/System.out(6159): onResume被调用了！activity得到焦点
 12-03 00:31:55.498: I/System.out(6159): onPause被调用了！activity失去焦点
 12-03 00:31:55.498: I/System.out(6159): onStop被调用了！，activity被完全遮盖住了
 12-03 00:31:55.498: I/System.out(6159): onDestroy被调用了！activity被摧毁了
 12-03 00:31:55.548: I/System.out(6159): onCreate被调用了！进行一系列的初始化！
 12-03 00:31:55.578: I/System.out(6159): onStart被调用了！
 12-03 00:31:55.578: I/System.out(6159): onResume被调用了！activity得到焦点
 当切换屏幕的时候，相当于重新开启activity,之前的已经全部被摧毁，这在游戏中是不可以的，因此可以直接在配置文件中写死屏幕换屏方向
 */

public class Main3kof97 extends AppCompatActivity {

    private TextView mTextView;
    private int blood = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3kof97);
        System.out.println("onCreate被调用了！进行一系列的初始化！");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextView = (TextView) findViewById(R.id.textview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    
    public void power (View view) {
        
        blood--;
        mTextView.setText("你的血量减少为" + blood);

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
