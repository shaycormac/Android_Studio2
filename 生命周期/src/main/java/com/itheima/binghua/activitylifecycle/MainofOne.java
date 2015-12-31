package com.itheima.binghua.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**时间：2015年12月4日 15:40:08
 * 学习：activity启动模式：在哪呢？在清单文件中好多activity，你要设置的那个activity的标签中，
 * 设置  android:launchMode="singleTop"，共四种模式
 *
 * 1.standard：默认标准的启动模式， 每次startActivity都是创建一个新的activity的实例。适用于绝大大数情况
 * 2.singletop:只要被设置，则在顶部的时候，就只能创建一次，不能重复创建退回时，一次即可，而是调用 onNewIntent() 方法。
    应用场景： 浏览器书签。 避免栈顶的activity被重复的创建，解决用户体验问题。
 * 3.singlestack:保证了在任务栈里面只有被设置的那个activity，如果激活的activity在任务栈中已经存在，那么复用这个对象，并把他上面的activity都给清空！！
 * 那么按返回键时，只要当前页面以及栈底的那个对象，譬如本例中，就俩个实例，将one设置为singletask之后，只要有了一个，上面在激活它时，他就把之前的全部清空，
 * 好极端！！
 * 单一任务栈 ， activity只会在任务栈里面存在一个实例。如果要激活的activity，在任务栈里面已经存在，就不会创建新的activity，而是复用这个已经存在的activity，
 调用 onNewIntent() 方法，并且清空当前activity任务栈上面所有的activity
 应用场景：浏览器activity， 整个任务栈只有一个实例，节约内存和cpu的目的，因为手机内存有限制，不能像电脑那样浪费内存！！
 注意： activity还是运行在当前应用程序的任务栈里面的。不会创建新的任务栈。
 
 4.singleinstance,单例模式！！
 单态 单例模式
 单一实例，整个手机操作系统里面只有一个实例存在。不同的应用去打开这个activity
 共享 公用的同一个activity。
 他会运行在自己单独，独立的任务栈里面，并且任务栈里面只有他一个实例存在。
 应用场景：呼叫来电界面 InCallScreen
 * 
 */

public class MainofOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainof_one);
        
        System.out.println("one被创建啦！！任务栈的id为：："+getTaskId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    
    public void open1(View v) {
        Intent intent = new Intent(this, MainofOne.class);
        startActivity(intent);
        
    }
    public void open2(View v) {
        Intent intent = new Intent(this, MainofTwo.class);
        startActivity(intent);
        
    }

    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("NewIntenet被创建啦！！而没有新建One,任务栈的id为：："+getTaskId());
        
        super.onNewIntent(intent);
    }
}
