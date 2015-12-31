package com.itheima.binghua.testSpeed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private ImageView mImageView;
    private EditText mText;
    private Handler mHandler;
    private static final int SUCCES = 0;
    private static final int FAILED = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        mText = (EditText) findViewById(R.id.text);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) 
                {
                    case SUCCES:
                        mImageView.setImageBitmap((Bitmap) msg.obj);
                        Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                        break;
                    case FAILED:
                        //一定要注意，这里的吐死this指的是主类，而不是这里面的子类哦！！
                        Toast.makeText(MainActivity.this, "下载失败",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        
    }
    
    public void getPicture (View view) 
    {
        final String uri = mText.getText().toString();
        //新开一个线程，在这里读取图片数据
        new Thread(new Runnable() 
        {
            @Override
            public void run() {
                Bitmap bitmap = getBitmap(uri);
                Message msg = mHandler.obtainMessage();
                if (bitmap != null) {

                    msg.what = SUCCES;
                    msg.obj = bitmap;
                    mHandler.sendMessage(msg);
                } else {
                    msg.what = FAILED;
                    mHandler.sendMessage(msg);
                    
                }


            }
        }).start();

    }
    
    public Bitmap getBitmap (String uri) 
    {
      
        HttpURLConnection coon = null;
        URL url = null;
        try {
            url = new URL(uri);
            //这样就打开了一个网络连接，一定要注意，最后要关闭！！finally
            coon = (HttpURLConnection) url.openConnection();
            //请求的连接方式
            coon.setRequestMethod("GET");
            //设置连接时间
            coon.setConnectTimeout(10000);
            //设置读取时间
            coon.setReadTimeout(5000);
            //脸上服务器，这条代码可写可不写，因为后面获取响应状态码就默认连接了
            coon.connect();
            //获取状态码，要是200，说明响应成功，读取数据
            int code =coon.getResponseCode();
            if (code == 200) {
                InputStream in = coon.getInputStream();
                //获取读取流，加载到bitmap中
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            } else {
                Log.i("响应失败", String.valueOf(code));
                
            } 

        } catch (Exception e) {
            e.printStackTrace();
        }finally 
        {
            if (coon != null) 
            {
                coon.disconnect();

            }
        }
        return null;
    }
    
}
