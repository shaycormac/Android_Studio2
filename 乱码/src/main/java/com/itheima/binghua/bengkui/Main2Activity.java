package com.itheima.binghua.bengkui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 时间：2015年11月25日 19:32:46
 * 目的。通过这个练习，学习如何将读取过来的文档（以字节码读取）的乱码问题解决，要比从servlet简单一点？
 * 关注的是发过来的文档采取什么样的编码格式，无非三种格式，utf-8,gb2312,GBK,安卓模拟器默认所有都是utf-8
 * 传过来的文档要是以utf-8过来，则不需要改变，要是其它两种，则需要改变编码！！
 *
 *
 *
 *
 */

public class Main2Activity extends AppCompatActivity {

    private static final int SUCCESS = 0;
    private static final int FAILED = 1;
    private TextView mTextView;
    private EditText mEditText;
    private Button mButton;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mButton = (Button) findViewById(R.id.button_2);
        mTextView = (TextView) findViewById(R.id.text_data);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SUCCESS:
                        String tt = (String) msg.obj;
                        mTextView.setText(tt);
                        break;
                    case FAILED:
                        Toast.makeText(Main2Activity.this, "网络崩溃啦！", Toast.LENGTH_SHORT);
                        break;
                    default:
                        break;
                    
                }
            }
        };
        mButton.setOnClickListener(new myListener());
    }

    class myListener implements View.OnClickListener 
    {

        @Override
        public void onClick(View v) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    String path = mEditText.getText().toString();
                    //String path = "http://10.0.2.2:8080/NetEaseServer/index.jsp";
                    String getData = getDataFromWeb(path);
                    Message msg = mHandler.obtainMessage();
                    if (getData != null) {
                        msg.what = SUCCESS;
                        msg.obj = getData;
                    } else {
                        msg.what = FAILED;
                    }
                    mHandler.sendMessage(msg);
                }
            }.start();

        }
    }

   

    private String getDataFromWeb(String path) {
        HttpURLConnection coon = null;
        try {
            URL url = new URL(path);
            coon = (HttpURLConnection) url.openConnection();
            coon.setRequestMethod("GET");
            coon.setConnectTimeout(10000);
            coon.setReadTimeout(5000);
            int code = coon.getResponseCode();
            if (code == 200) {
                InputStream in= coon.getInputStream();
                String getdata2 = getDataFromInputstream(in);
                return getdata2;
                
            } else {
                Log.i("code不对：", String.valueOf(code));
                
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (coon != null) {
                coon.disconnect();
                
            }
        }


        return null;
    }

    private String getDataFromInputstream(InputStream in) throws IOException {
        //使用字节流，将得到的网页字节流读入一个字节数组，再写进一个字节缓冲流即可
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buff = new byte[1024];
        while ((len=in.read(buff))!=-1) 
        {
            baos.write(buff, 0, len);
            baos.flush();
        }
        in.close();
        //如果对面传过来的是utf，=直接转换成字符
       // String getData1 = baos.toString();
        
        //如果传过来的编码是按照GBK或者是gb2312，则按照人家的方式解码
      //  String getData1 = new String(baos.toByteArray(), "GBK");//一定要明白这行代码的意思，既然人家已经用编码GBK编好了，因此，你取出来的时候
        //先变成字节数组，在按照人家的GBK编码重新编码即可，要与String name=request.getParames(name)区分开了。这个家伙假设请求的编码是GBK，
        //由于HttpRequest已经把它按照iso8859-1编码了，因此先要反编译，变成自由的字节数组，即byte[] buff = name.getByte("iso8859-1"),
        //然后再 String name = new String (buff,"GBK");和这个类似，只是由于这里面直接得到了字节数组，因此不需要反编译这一步了，要明白！！
        //String getData = baos.toString();
        
        //为了统一编码，因此加上下面一段代码来解决！
        String getData1 = baos.toString();//先不管编码如何，把流中的数据转换成字符串, 采用的编码是: utf-8
        String charSet = "utf-8";
        //因为抓取的网页里面有英文的编码字符关键字！，她和编码无关，因此可以在专区的界面寻找
        if (getData1.contains("GBK") || getData1.contains("gbk") || getData1.contains("gb2312") || getData1.contains("GB2312")) {
            // 如果包含gbk, gb2312编码, 就采用gbk编码进行对字符串编码
            charSet = "gbk";
        }
        getData1 = new String(baos.toByteArray(), charSet);
        return getData1;
    }
    
}
