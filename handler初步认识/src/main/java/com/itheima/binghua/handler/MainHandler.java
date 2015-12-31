package com.itheima.binghua.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

/**时间：2015年11月19日 23:46:57
 * 练习：通过本例题，实现线程之间通信，通过handler来操作，
 * 本例是mainThread接收信息，workThread处理信息，例题二桥好翻过来
 * 
 * 
 */
public class MainHandler extends Activity 
{
    private android.os.Handler mHandler ;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_handler);
        //主线程启动，生成了接收数据的handler对象，刚上来由于子线程没有启动，因此looper得不到消息，处于阻塞状态，block，主线程里面不用写loop！！
        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String s = (String) msg.obj;
                mTextView.setText(s);
            }
        };
        mTextView = (TextView) findViewById(R.id.text);
       
        
    }
    public void diaoBao (View view)
    {
        myThread t = new myThread();
        t.start();

    }

    

    class myThread extends Thread {
        /**
         * Calls the <code>run()</code> method of the Runnable object the receiver
         * holds. If no Runnable is set, does nothing.
         *
         * @see Thread#start
         */
        @Override
        public void run() {
            super.run();
            SystemClock.sleep(5*1000);
            String s = "变了吧？？";
            Message message = mHandler.obtainMessage();
            message.obj = s;
            mHandler.sendMessage(message);

        }
    }
}
