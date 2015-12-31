package com.itheima.binghua.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainWorkerThread extends Activity {
    private Handler mHandler;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_worker_thread);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) 
            {
                System.out.println("你再执行");
                Message message = mHandler.obtainMessage();//这一步相当于得到了一个新的对象message,和handlee一一对应，
                message.obj = "我是一个大胖猪";
                mHandler.sendMessage(message);
                
            }
           
        });
      //该线程必须先执行，才有了mhandler对象，然后才能在主线程中进行数据处理再传给子线程
        myThread thread = new myThread();
        thread.start();
        
       
    }
    
  


    class myThread extends Thread
    {
       
        @Override
        public void run() {
            super.run();//51-62行代码可以看成一个模版，必须记住，在workthread线程中。
          //  SystemClock.sleep(5 * 1000);
            Looper.prepare();
            System.out.println("我先执行");
            mHandler = new Handler()//匿名内部类
            {
                
               
                @Override
                public void handleMessage(Message msg)
                {
                    super.handleMessage(msg);
                    String s = (String) msg.obj;
                    Log.i("从主线程得到的数据:---", s);
                }
            };
            Looper.loop();
        }
        
          
    }

}
