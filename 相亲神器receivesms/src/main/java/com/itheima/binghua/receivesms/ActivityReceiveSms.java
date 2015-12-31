package com.itheima.binghua.receivesms;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

public class ActivityReceiveSms extends Activity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_sms);
       /* myThread t = new myThread();
        t.start();*/
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10 * 1000);
                Uri uri = Uri.parse("content://sms/");
                ContentResolver contentResolver = getContentResolver();
                //马勒戈壁！！！设置为null,肯定写不尽东西啊！！！草草草！！
                ContentValues values = new ContentValues();
                values.put("address","9555");
                values.put("type","1");
                String content = "你的帐户收到人民币1000,000,000元，当前账户余额为：289,000,897元。";
                values.put("body",content);
                contentResolver.insert(uri, values);
                
            }
        }).start();*/
        myThread t = new myThread();
        t.start();
        
    }

    class myThread extends Thread
    {
         
        @Override
        public void run()
        {
            super.run();
            

           
            Runnable r = new Runnable() {
                @Override
                public void run()
                {
                    SystemClock.sleep(6 * 1000);
                    Uri uri = Uri.parse("content://sms/");
                    ContentResolver contentResolver = getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put("address","95556");
                    values.put("type",1);
                    String content = "你的帐户收到人民币1000,000,000元，当前账户余额为：289,000,897元。";
                    values.put("body", content);
                    contentResolver.insert(uri, values);
                    
                }
            };
            mHandler.post(r);
        }
    }
}
