package com.itheima.binghua.muiltthreaddownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 时间：2015年11月27日 20:07:35
 * 功能：实现多线程断点下载，所谓断点下载，即重新点击的时候，接着原来的步骤下载，那么，最重要的是，上次下载未完成的时候的读写位置！
 * 找打它，就成功了一半
 * 
 * 
 */
/*多线程完成下载，主要分四步走
        * 1.首先建立好网络连接，获取到服务器端资源文件的大小
        * 2.根据开启的开启线程数量，把服务器的资源给等分分成若干份
        * 3.建立一个缓存随机读写的文件，大小和目标文件一样RandomAcessFile
        * 4.根据http协议("Range""bytes="+开始位置+“-”+“结束位置”);
        * */

public class MainActivity2 extends AppCompatActivity {
    //得到线程的个数
    private static final int threadcount = 3;
    //创建每个单元的大小
    private  long block_size;
    //创建正在运行的线程数
    private int runningThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new myThread().start();

    }

    class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            String path = "http://10.0.2.2:8080/NetEaseServer/PPTV.exe";
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
                    block_size = size / threadcount;
                    //新建一个和目标文件大小一样的临时随机文件,学会建立，并设置目标值！！
                    File file1 = Environment.getExternalStorageDirectory();
                    File file = new File(file1, "temp.exe");
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    raf.setLength(size);
                    long start = 0;
                    long end = 0;
                    runningThread = threadcount;
                    for (int i = 1; i <= threadcount; ++i) {
                        start = (i - 1) * block_size;
                        end = i * block_size - 1;
                        if (i == threadcount) {
                            end = size - 1;
                        }
                        System.out.println("开启线程：" + i + "下载的位置：" + start + "~" + end);
                        new threadDownload(path, i, start, end).start();


                    }

                }
            } catch (Exception e) {
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
                int total =0;//用它记录字节的个数
                File file3 = new File(Environment.getExternalStorageDirectory(), threadId+".txt");//创建用于记录字节个数的一个文件

                coon = (HttpURLConnection) url.openConnection();
                coon.setRequestMethod("GET");
                coon.setConnectTimeout(10000);
                coon.setReadTimeout(5000);
                //再次打开的时候，要是文件存在的话，新建一个读文件流，读取里面的数据
                if (file3.exists() && file3.length()>0) {
                    FileInputStream fileInputStream = new FileInputStream(file3);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String countOfString = bufferedReader.readLine();//得到第一行的数目已足以，本来就这一行
                    int lastTotal = Integer.valueOf(countOfString);//得到了上次下载了的字节数量，从而，开始的位置应该发生了变化！！
                    System.out.println("上次线程" + threadId + "下载的总大小：" + lastTotal);
                    start += lastTotal;
                    total += lastTotal;//得到这次开始下载的时候，已经的总数。将这个值再往下传，得到了下次断点的时候，下载的数量！！
                    fileInputStream.close();
                }
                
                //设置Range参数
                coon.setRequestProperty("Range", "bytes=" + start + "-" + end);

                int code = coon.getResponseCode();
                if (code / 100 == 2) {
                    System.out.println("code=" + code);
                    //加载下载文件，并给出要随机写的位置！
                    File file1 = Environment.getExternalStorageDirectory();
                    File file = new File(file1, "temp.exe");
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    //指定文件开始写的位置
                    raf.seek(start);
                    System.out.println("第" + threadId + "个线程：写文件的开始位置：" + String.valueOf(start));
                    InputStream in = coon.getInputStream();
                    //将读出的流写进文件，xiaocase!使用随机文件，这个len,很关键啊！！全靠它找到上次未完成的位置，那么
                    //我们在寻求已经下载的字节大小，并将它写入一个随机文件流中去，以便后面第二次读出来！！
                    int len = -1;
                    
                    byte[] buff = new byte[1024*1024];
                    while ((len = in.read(buff)) != -1) {

                        RandomAccessFile rf = new RandomAccessFile(file3, "rwd");//file3不存在，即第一次的时候，创建file3，并作为记录个数
                        raf.write(buff, 0, len);
                        total += len;//每次得到个数相加，最后得到total个数
                        rf.write(String.valueOf(total).getBytes());//这样，就把循环这一次的长度写进一个用于记录个数的文件，不断循环，不断的重新覆盖这个数字，直到循环结束
                        rf.close();
                    }
                    in.close();
                    raf.close();


                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally
            {
                //下载完成后，将临时生成的文件删除！只有所有的线程都下载完毕后 才可以删除记录文件。需要同步！！
                synchronized (MainActivity2.class) {
                    System.out.println("线程" + threadId + "下载完毕了！");
                    runningThread--;
                    if (runningThread < 1) 
                    {
                        System.out.println("所有的线程都下载完毕了！！");
    
                        for (int i = 1; i <= threadId; ++i)
                        {
                            System.out.println("所有的线程都工作完毕了。删除临时记录的文件");
                            File f = new File(Environment.getExternalStorageDirectory(),i + ".txt");
                            System.out.println(f.delete()); 
     
                        }
                    }
                }


                if (coon != null) {
                    coon.disconnect();
                }
            }


        }
    }


}
