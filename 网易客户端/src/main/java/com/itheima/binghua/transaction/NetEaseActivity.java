package com.itheima.binghua.transaction;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.binghua.transaction.entities.NewInfo;
import com.loopj.android.image.SmartImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetEaseActivity extends Activity {
    private Handler mHandler;
    private final int SUCCESS=0;
    private final int FAILED=1;
    private List<NewInfo> mList5;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //去掉标题，在用户点击程序到onStart（）之前就要调用此方法，去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_netease);
        mListView = (ListView) findViewById(R.id.list_view);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
               // mList5 = new ArrayList<>();
                if (msg.what == SUCCESS) {
                    mList5 = (List<NewInfo>) msg.obj;
                    mListView.setAdapter(new myAdapter());
                    Toast.makeText(NetEaseActivity.this, "获取成功！", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(NetEaseActivity.this, "网络崩溃啦！", Toast.LENGTH_SHORT).show();
                    
                } 
            }
        };
        myThread t = new myThread();
        t.start();
        
    }

    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList5.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) 
            {
                //如何将布局管理变成view对象！！
                LayoutInflater layoutInflater = NetEaseActivity.this.getLayoutInflater();
                view = layoutInflater.inflate(R.layout.show_news, null);

            } else {
                view = convertView;
            }
            NewInfo newInfo1 = mList5.get(position);
            SmartImageView sivIcon = (SmartImageView) view.findViewById(R.id.image_url);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView detail = (TextView) view.findViewById(R.id.detail);
            TextView comment = (TextView) view.findViewById(R.id.comment);
            sivIcon.setImageUrl(newInfo1.getImageurl());
            title.setText(newInfo1.getTitle());
            detail.setText(newInfo1.getDetail());
            comment.setText(newInfo1.getComment()+"跟帖");
            return view;
        }
    }


    class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            String path = "http://10.0.2.2:8080/NetEaseServer/new.xml";
            List<NewInfo> list1 = getListFromWeb(path);
            Message msg = mHandler.obtainMessage();
            if (list1 != null) {
                msg.what = SUCCESS;
                msg.obj = list1;
            } else {
                msg.what = FAILED;
            }
            mHandler.sendMessage(msg);
        }
    }

    private List<NewInfo> getListFromWeb(String path) 
    {
        HttpURLConnection mCoon = null;


        try {
            URL url = new URL("http://10.0.2.2:8080/NetEaseServer/new.xml");
            mCoon = (HttpURLConnection) url.openConnection();
            mCoon.setRequestMethod("GET");
            mCoon.setConnectTimeout(10000);
            mCoon.setReadTimeout(5000);
            int code = mCoon.getResponseCode();
            if (code == 200) {
                InputStream in = mCoon.getInputStream();
                List<NewInfo> list3 = getListFromInputStream(in);
                return list3;
            } else {
                Log.i("状态码为：", String.valueOf(code));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally 
        {
            if (mCoon != null)
            {
                mCoon.disconnect();
            }

        }
        return null;

    }

    private List<NewInfo> getListFromInputStream(InputStream in) throws XmlPullParserException, IOException
    {
        //由于是xml文档，因此使用xml解析器来处理
       XmlPullParser pull =  Xml.newPullParser();
        pull.setInput(in, "utf-8");
        int eventType = pull.getEventType();
        List<NewInfo> list2 = null;
        NewInfo newInfo = null;
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            String tagName = pull.getName();
            switch (eventType) 
            {
                case XmlPullParser.START_TAG:
                    if (tagName.equals("news")) {
                        list2 = new ArrayList<>();
                    }else if (tagName.equals("new")) {
                        newInfo = new NewInfo();
                    }else if (tagName.equals("title")) {
                        newInfo.setTitle(pull.nextText());
                    }else if("detail".equals(tagName)) {
                        newInfo.setDetail(pull.nextText());
                    } else if("comment".equals(tagName)) {
                        newInfo.setComment(Integer.valueOf(pull.nextText()));
                    } else if("image".equals(tagName)) {
                        newInfo.setImageurl(pull.nextText());
                    }
                break;
                case XmlPullParser.END_TAG:
                    if (tagName.equals("new")) {
                        list2.add(newInfo);
                    }
                    break;
                 default:
                    break;
                    
            }
            eventType = pull.next();
            

        }
        return list2;
    }


}
