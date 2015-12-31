package com.itheima.binghua.muiltthreaddownload;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2015年11月27日 21:24:48
 * 功能：实现多线程断点下载，所谓断点下载，即重新点击的时候，接着原来的步骤下载，那么，最重要的是，上次下载未完成的时候的读写位置！
 * 找打它，就成功了一半，
 * 难度再次加大，添加一个UI界面，注意由于progressBar控件内部实现了handler传值机制，因此可以在子线程中修改，其他控件则不可以，而
 * 除了UI操作，其他操作都要在新开的线程中操作！！
 */
/*多线程完成下载，主要分四步走
        * 1.首先建立好网络连接，获取到服务器端资源文件的大小
        * 2.根据开启的开启线程数量，把服务器的资源给等分分成若干份
        * 3.建立一个缓存随机读写的文件，大小和目标文件一样RandomAcessFile
        * 4.根据http协议("Range""bytes="+开始位置+“-”+“结束位置”);
        * */

public class MainActivity3 extends AppCompatActivity {
    private static final int FAILEDOFTHREAD = 0;
    private static final int FAILEDOFSONTHREAD = 1;
    private static final int SUCCESS = 2;
    //得到线程的个数
    private int threadcount;
    //创建每个单元的大小
    private long block_size;
    //创建正在运行的线程数
    private int runningThread;
    private EditText mPath;
    private EditText mThreadCount;
    private LinearLayout mLinearLay;
    //设置一个消息机制，用来向用户有好提示
    private Handler mHandler;
    private List<ProgressBar> list;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPath = (EditText) findViewById(R.id.edit_path);
        mThreadCount = (EditText) findViewById(R.id.edit_count);
        mLinearLay = (LinearLayout)findViewById(R.id.lay_thread);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case FAILEDOFSONTHREAD:
                        Toast.makeText(MainActivity3.this, "子线程下载失败", Toast.LENGTH_SHORT).show();
                        break;
                    case FAILEDOFTHREAD:
                        Toast.makeText(MainActivity3.this, "主线程下载失败", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        Toast.makeText(MainActivity3.this, "恭喜你，下载成功！！", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };


    }

    public void downloadFile(View view)
    {
        String path = mPath.getText().toString().trim();
        String count = mThreadCount.getText().toString().trim();
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(MainActivity3.this, "请输入正确的网址！", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(count)) {
            Toast.makeText(MainActivity3.this, "请输入正整数", Toast.LENGTH_SHORT).show();
        }
        //得到线程的个数
        threadcount = Integer.parseInt(count);
        //清空掉旧的进度条
        mLinearLay.removeAllViews();
        //在界面里面添加count个进度条
        //将进度条添加到容器中去，方便在子线程中去操作相应的进度条
        list = new ArrayList<>();
        //向填充器中添加线程个数的进度条
        for (int i = 0; i < threadcount; ++i) 
        {
            ProgressBar pb = (ProgressBar) View.inflate(this, R.layout.pb, null);//在程序中创建一个进度条
            mLinearLay.addView(pb);//将进度条添加到布局中
            list.add(pb);//list角标是从0开始的！！,目的在于将进度条放进集合中，在子线程中取得相应进度条，进行设置！
        }
        Toast.makeText(this, "开始下载", 0).show();
        new myThread(path,threadcount).start();
    }

    class myThread extends Thread {
        private String path;
        private int threadcount2;

        public myThread(String path, int threadcount) {
            this.path = path;
            this.threadcount2 = threadcount;
        }


        @Override
        public void run() {
            super.run();
            //  String path = "http://10.0.2.2:8080/NetEaseServer/PPTV.exe";
          
            HttpURLConnection coon = null;
            try {
                URL url = new URL(path);
                coon = (HttpURLConnection) url.openConnection();
                coon.setRequestMethod("GET");
                coon.setConnectTimeout(10000);
                coon.setReadTimeout(5000);
                int code = coon.getResponseCode();
                if (code / 100 == 2) {
                    //得到目标文件的大小,ZH这需要是得到响应之后，你有点着急了
                    int size = coon.getContentLength();
                    System.out.println("要下载的文件大小为：" + size);
                    //得到分割的每个单元的大小
                    block_size = size / threadcount2;
                    //新建一个和目标文件大小一样的临时随机文件,学会建立，并设置目标值！！
                    File file1 = Environment.getExternalStorageDirectory();
                    File file = new File(file1, getFileName(path));
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    raf.setLength(size);
                    long start = 0;
                    long end = 0;
                    runningThread = threadcount2;
                    for (int i = 1; i <= threadcount2; ++i)
                    {
                        //设置每个线程的开始和结束位置
                        start = (i - 1) * block_size;
                        end = i * block_size - 1;
                        if (i == threadcount2) {
                            end = size - 1;
                        }
                        //设置每个进度条的满数
                        int MaxSize = (int) (end - start);
                        list.get(i - 1).setMax(MaxSize);
                        
                        System.out.println("开启线程：" + i + "下载的位置：" + start + "~" + end);
                        new threadDownload(path, i, start, end).start();


                    }

                }
            } catch (Exception e) {
                Message msg = mHandler.obtainMessage();
                msg.what = FAILEDOFTHREAD;
                mHandler.sendMessage(msg);
                e.printStackTrace();
            } finally {
                if (coon != null) {
                    coon.disconnect();
                }
            }


        }
    }
  
    
    class threadDownload extends Thread {

        private String path;

        private int threadId;

        private long start;
        private long end;

        public threadDownload(String path, int threadId, long start, long end) {
            this.path = path;
            this.threadId = threadId;
            this.start = start;
            this.end = end;
        }


        @Override
        public void run() {
            super.run();
            HttpURLConnection coon = null;
            try {
                URL url = new URL(path);
                int total = 0;//用它记录字节的个数
                File file3 = new File(Environment.getExternalStorageDirectory(), threadId + ".txt");//创建用于记录字节个数的一个文件
                coon = (HttpURLConnection) url.openConnection();
                coon.setRequestMethod("GET");
                coon.setConnectTimeout(10000);
                coon.setReadTimeout(5000);
                //再次打开的时候，要是文件存在的话，新建一个读文件流，读取里面的数据
                if (file3.exists() && file3.length() > 0) {
                    FileInputStream fileInputStream = new FileInputStream(file3);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String countOfString = bufferedReader.readLine();//得到第一行的数目已足以，本来就这一行
                    int lastTotal = Integer.valueOf(countOfString);//得到了上次下载了的字节数量，从而，开始的位置应该发生了变化！！
                    System.out.println("上次线程" + threadId + "下载的总大小：" + lastTotal);
                    start += lastTotal;
                    total += lastTotal;//得到这次开始下载的时候，已经的总数。将这个值再往下传，得到了下次断点的时候，下载的数量！！
                    fileInputStream.close();//养成关闭每个流的好习惯，不关闭会导致最后删除文件异常
                }

                //设置Range参数，是一个Http协议，通知下载以字节，从开头到结束的位置。
                coon.setRequestProperty("Range", "bytes=" + start + "-" + end);

                int code = coon.getResponseCode();
                //市面上一般流行的模式，即找到206
                if (code / 100 == 2) 
                {
                    System.out.println("code=" + code);
                    //加载下载文件，并给出要随机写的位置！
                    File file1 = Environment.getExternalStorageDirectory();
                    File file = new File(file1, getFileName(path));
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    //指定文件开始写的位置
                    raf.seek(start);
                    System.out.println("第" + threadId + "个线程：写文件的开始位置：" + String.valueOf(start));
                    InputStream in = coon.getInputStream();
                    //将读出的流写进文件，xiaocase!使用随机文件，这个len,很关键啊！！全靠它找到上次未完成的位置，那么
                    //我们在寻求已经下载的字节大小，并将它写入一个随机文件流中去，以便后面第二次读出来！！
                    int len = -1;

                    byte[] buff = new byte[1024*1024*10];
                    while ((len = in.read(buff)) != -1) 
                    {

                        RandomAccessFile rf = new RandomAccessFile(file3, "rwd");//file3不存在，即第一次的时候，创建file3，并作为记录个数
                        raf.write(buff, 0, len);
                        total += len;//每次得到个数相加，最后得到total个数
                        //设置进度条的进度数
                        rf.write(String.valueOf(total).getBytes());//这样，就把循环这一次的长度写进一个用于记录个数的文件，不断循环，不断的重新覆盖这个数字，直到循环结束
                        rf.close();
                        list.get(threadId - 1).setProgress(total);
                    }
                    in.close();
                    raf.close();


                }
            } catch (Exception e) {
                Message msg = mHandler.obtainMessage();
                msg.what = FAILEDOFSONTHREAD;
                mHandler.sendMessage(msg);
                e.printStackTrace();
            } finally 
            {
                if (coon != null) {
                    coon.disconnect();
                }
                //下载完成后，将临时生成的文件删除！只有所有的线程都下载完毕后 才可以删除记录文件。需要同步！！
                synchronized (MainActivity3.class) 
                {
                    System.out.println("线程" + threadId + "下载完毕了！");
                    runningThread--;
                    if (runningThread < 1) 
                    {
                        System.out.println("所有的线程都下载完毕了！！");

                        for (int i = 1; i <= threadcount; ++i) 
                        {
                            System.out.println("所有的线程都工作完毕了。删除临时记录的文件");
                            File f = new File(Environment.getExternalStorageDirectory(), i + ".txt");
                            System.out.println(i+".txt已经被删除");
                            System.out.println(f.delete());

                        }
                        Message msg = mHandler.obtainMessage();
                        msg.what = SUCCESS;
                        mHandler.sendMessage(msg);
                    }
                }


               
            }


        }
    }


    //将下载的文件名字名为和源文件一样的名字
    private String getFileName(String path) {
        int index =  path.lastIndexOf("/");
        String name = path.substring(index + 1);
        return name;
    }


}
