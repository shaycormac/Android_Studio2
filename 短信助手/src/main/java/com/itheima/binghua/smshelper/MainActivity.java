package com.itheima.binghua.smshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private EditText mText;
    private EditText mText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (EditText) findViewById(R.id.text);
        mText2 = (EditText) findViewById(R.id.text2);
       
        
    }
    public void selectSms (View view) {
        Intent intent = new Intent(this, ListSms.class);
       // startActivity(intent);
        //注意，这里面启动的是带有返回值的意图！！！
        startActivityForResult(intent, 0);

    }
    
    //重写一个方法接收从第二个页面传谷哟来的值

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data !=null) {
            String edittext = data.getStringExtra("data");
            if (resultCode == 0) {

                mText2.setText(edittext);
            }
            if (resultCode == 1) {
                mText.setText(edittext);
                
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    
    public void selectNum (View view) {
        //同点击一，进入新的页面，电话联系人的页面
        Intent intent = new Intent(this, Contacts.class);
        startActivityForResult(intent, 1);
    }

    //发送短信
    public void sendSms(View view) {

       /* <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="text/plain" />*/
        
        
       // Intent intent = new Intent();
        String num = mText.getText().toString().trim();
        String conte=mText2.getText().toString().trim();
        System.out.println(num);
        System.out.println(conte);
        //得到短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(num, null, conte, null, null);
        Toast.makeText(this, "短信发送成功！", Toast.LENGTH_SHORT).show();//我的神来，你怎么能忘记了show!!!
        //使用list的时候，activity对应的布局文件一定要放list,其他页面在放其他数据！！

    }
}
