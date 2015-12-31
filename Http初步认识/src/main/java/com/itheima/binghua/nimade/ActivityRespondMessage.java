package com.itheima.binghua.nimade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ActivityRespondMessage extends AppCompatActivity {
    /**
     * 
     * 
     * @param savedInstanceState
     * 查看请求对象以及响应对象里面的各种参数值
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_respondmessage);
        myThread t = new myThread();
        t.start();

       
    }


    //网络数据必须在新开的线程中，哥哥
    class myThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://192.168.0.103:8080/day017register/1.jsp?name='dafang'&psw='123456'");
            httpGet.addHeader("Host","www.marschen.com");
            httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101");
            httpGet.addHeader("Accept-Language"," zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
           
            Header[] headers = httpGet.getAllHeaders();
            for (int i = 0; i < headers.length; ++i)
            {
                String key = headers[i].getName();
                String value = headers[i].getValue();
                Log.i("请求中的参数为：", key + "---" + value);
            }

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                Header[] headers2 = httpResponse.getAllHeaders();
                for (int i = 0; i < headers2.length; ++i) {
                    String key1 = headers2[i].getName();
                    String value1 = headers2[i].getValue();
                    Log.i("响应中的参数为：", key1 + "---" + value1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }

    
}
