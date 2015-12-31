package com.itheima.binghua.muiltthreaddownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 时间：2015年11月26日 20:28:02
 * 心得：学会随机文件流的熟练使用，如何创建随机文件流，使用其构造方法，后面参数表示只读，读写，以及rwd将每个更新写到底层储存设备，以及设置大小setLength()，以及随机文件流写数据的开始位置
 * seek(postion)，和其它写流有点不同。
 * 学会多线程将文件分成三块，每块的下载开始结束位置要清楚，start = (i - 1) * block_size; end = i * block_size - 1;同时在随机文件中也要清楚！！
 * 网络连接里面设置属性setRequestProperty要知道！！
 */
/*多线程完成下载，主要分四步走
        * 1.首先建立好网络连接，获取到服务器端资源文件的大小
        * 2.根据开启的开启线程数量，把服务器的资源给等分分成若干份
        * 3.建立一个缓存随机读写的文件，大小和目标文件一样RandomAcessFile
        * 4.根据http协议("Range""bytes="+开始位置+“-”+“结束位置”);
        * */

public class MainActivity extends AppCompatActivity {
    //得到线程的个数
    private static final int threadcount = 3;
    //创建每个单元的大小
    public static long block_size;

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
                    // InputStream in = coon.getInputStream();
                    long start = 0;
                    long end = 0;
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
                coon = (HttpURLConnection) url.openConnection();
                coon.setRequestMethod("GET");
                coon.setConnectTimeout(10000);
                coon.setReadTimeout(5000);
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
                    //将读出的流写进文件，xiaocase!使用随机文件六
                    int len = -1;
                    byte[] buff = new byte[1024];
                    while ((len = in.read(buff)) != -1) {

                        raf.write(buff, 0, len);
                    }
                    in.close();
                    raf.close();
                    System.out.println("线程" + threadId + "下载完毕了！");


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


}
