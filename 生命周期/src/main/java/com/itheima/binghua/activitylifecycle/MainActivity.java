package com.itheima.binghua.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**时间：2015年12月3日 13:01:16
 * 功能：activity生命周期，注意了，1.当使用home键的时候，当前的activity先是调用onpause（失去焦点）,再调用onStop（完全被遮盖住）
 * 2.当使用退回键，回到前一个activity的时候，先是调用当前的onpause，载调用之前的那个页面的onrestart,onstart,onresume,得到焦点后，当前的页面的onstop才被调用
 * 3.当第二个页面是一个窗口之类的时候，没有完全覆盖住当前页面，则不会调用当前页面的onStop。
 * 
 * 
 * 完整生命周期  oncreate--》onstart--》onresume--》onpause--》onstop--》ondestory

 可视生命周期  onstart--》onresume--》onpause--》onstop

 前台生命周期  onresume--》onpause  界面用户仍然可见，但是失去焦点


 使用场景：
 1.应用程序退出自动保存数据   ondestory   oncreate
 2.应用程序最小化 暂停的操作  onstop onstart  视频播放器
 3.游戏的暂停和开始 前台生命周期
 * 
 */

public class MainActivity extends AppCompatActivity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void click (View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

    }
}


