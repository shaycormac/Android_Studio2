package com.itheima.binghua.nimade;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2015年11月23日 14:09:14
 * 功能：实现了从网页上抓取数据，展示在手机页面上，用到了网络知识，handler,以及listView综合演示！！
 * 流程很重要，仔细回味。以及学会将大的框架构建好，然后在里面慢慢实现各种功能！！
 * 
 * 
 * 
 */

public class MainActivity extends Activity {


    private Handler mHandler;
    //private List<String> mStringList;
    private List<String> mGetlist;
    ListView mlistView;

    @Override

    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlistView = (ListView) findViewById(R.id.list_web);
        mHandler = new Handler() 
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mGetlist = (List<String>) msg.obj;
                BaseAdapter myAdapter = new MyAdapter();
                mlistView.setAdapter(myAdapter);

               
            }
        };
       


    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGetlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        //某一个位置的布局
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            //初步载入
            if (convertView == null) {
                //获取要在list里面展示的布局文件，将其转换成view对象
                //注意细节，布局填充器对象得到的方式是调用当前类的getLayoutinflater,然后将要展示的布局文件放进布局填充器中去，得到
                //view对象。
                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                view = layoutInflater.inflate(R.layout.listview_layout, null);
            }else {
                view = convertView;
            }
            //得到该布局文件中的TextView
            TextView textView = (TextView) view.findViewById(R.id.list_text);
            textView.setText(mGetlist.get(position));
            return view;
        }
    } 
    
    public void getParams(View view) {
        myThread t = new myThread();
        t.start();

    }


    //从网络上抓取数据，重新开启线程，不能在主线程中去完成
    //sdk6.0以后不再支持HttpClient，取而代之的是HttpUrlConnection ,所以，要想使用原来的，在项目的build.gradle中android中添加useLibrary 'org.apache.http.legacy'
    class myThread extends Thread {
        @Override
        public void run() {
            super.run();

            //1.获取客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            //2.获取请求对象,使用get或者post，使用构造方法，将网址的String穿进去
            HttpGet httpGet = new HttpGet("http://www.shaoit.com");
            List<String> list1 = new ArrayList<String>();
            String s;
            try {
                //3.创建响应对象、
                HttpResponse httpResponse = httpClient.execute(httpGet);
                //4.得到状态码，并判断是否船只正确200Ok
                int code = httpResponse.getStatusLine().getStatusCode();
                if (code == 200)
                {
                    //5.取出来值,首先创建一个实体对象
                    HttpEntity httpEntity = httpResponse.getEntity();
                    //获取得到网页的流
                    InputStream in = httpEntity.getContent();
                    //将其读出来，并放入一个字符串数组中,由于都是文本，使用缓冲字符流
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    while (bufferedReader.readLine() != null)
                    {
                        s = bufferedReader.readLine();
                        Log.d("从网页上读取了数据", s);
                        list1.add(s);
                    }
                   /* for (String si : mStringList)
                    {
                        sw = sw + si;

                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage();
            message.obj = list1;
            //Log.d("得到了数据！！", String.valueOf(message.obj));
            mHandler.sendMessage(message);
        }
    }
}
