package com.itheima.binghua.handler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ActivityHandlerPost extends AppCompatActivity 
{
    private Handler mHandler = new Handler();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_post);
        mTextView = (TextView) findViewById(R.id.text);
       
       
    }
    public void dianJi (View view) 
    {
        Thread thread = new myThread();
        thread.start();
    }

    class myThread extends Thread 
    {
        @Override
        public void run()
        {
            super.run();
            Runnable r = new Runnable() 
            {
                @Override
                public void run() {
                    mTextView.setText("哈哈，我变化了吧！！");
                    Log.i("需要处理的数据在这里面树立", "回头直接把这个加入消息队列中，让主线程去执行，不行你看");
                    System.out.println("执行当前的线程是："+Thread.currentThread().getName());

                }
            };
            mHandler.post(r);
            
        }
    }

}
