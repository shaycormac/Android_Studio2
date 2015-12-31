package com.itheima.binghua.bengkui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    private EditText mText;
    private EditText mText1;
    private Handler mHandler;
    private final int SUCCESS = 0;
    private final int FAILED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (EditText) findViewById(R.id.text);
        mText1 = (EditText) findViewById(R.id.text1);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new myListoner());
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) 
            {
                super.handleMessage(msg);
                if (msg.what == SUCCESS) {
                    String ss = (String) msg.obj;
                    Toast.makeText(MainActivity.this, "验证成功：" + ss, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "联网失败", Toast.LENGTH_SHORT).show();

                } 
            }
        };
    }

    class myListoner implements View.OnClickListener 
    {

        @Override
        public void onClick(View v) {
            new myThread().start();
           

        }
    }

    class myThread extends Thread 
    {

       

        @Override
        public void run()
        {
            super.run();
            String name = mText.getText().toString();
            String password = mText1.getText().toString();
            String getDate = getRespond(name, password);
            Message msg = mHandler.obtainMessage();
            if (getDate != null) {
                msg.what = SUCCESS;
                msg.obj = getDate;
                

            } else {
                msg.what = FAILED;
            }
            mHandler.sendMessage(msg);
         
        }
    }

    public String getRespond(String name, String password)
    {
        HttpURLConnection mCoon = null;
        try {
            
            URL url = new URL("http://10.0.2.2:8080/day024webforandroid/servlet/AndroidServlet?name="+ URLEncoder.encode(name)+"&password="+URLEncoder.encode(password));
            mCoon = (HttpURLConnection) url.openConnection();
            mCoon.setRequestMethod("GET");
            mCoon.setConnectTimeout(10000);
            mCoon.setReadTimeout(5000);
            int code = mCoon.getResponseCode();
            if (code == 200) 
            {
                InputStream is = mCoon.getInputStream();
                String shtml = getStringFromInputStream(is);
               /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String s = "";
                while (bufferedReader.readLine() != null) 
                {
                    s = s + bufferedReader.readLine();
                    Log.i("s的值为", s);
                }
                Log.i("s的值为", s);
                return s; */
                return shtml;

            } 
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (mCoon != null) {
                mCoon.disconnect();
            }
        }
        return null;
        
    }

    private String getStringFromInputStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf)) != -1)
        {
            baos.write(buf, 0, len);
            baos.flush();
            
        }
        in.close();
      
        //注意了，服务器传过来的数据，默认采用iso8859-1，如果里面含有中文，则找不到相应字符，默认会使用
        //GBK码表，那么接收过来的数据，要是字节组的话，重新编译，采用"gbk"的码表即可！
        //当然，也可以在服务器那边，要求其写过来采用utf-8的码表，这样，在我们只要得到的自己数组直接toString即可。
       /* byte[] buff = baos.toByteArray();
        String html = new String(buff, "GBK");*/
        String html = baos.toString();
        baos.close();
        return html;
    }
}
