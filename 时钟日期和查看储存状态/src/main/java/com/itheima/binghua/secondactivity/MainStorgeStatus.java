package com.itheima.binghua.secondactivity;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainStorgeStatus extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_storge_status);
        Button button = (Button) findViewById(R.id.buttonStorage);
        button.setOnClickListener(new myButton());
        mTextView = (TextView) findViewById(R.id.textStorage);
        
    }

    class myButton implements View.OnClickListener 
    {

        @Override
        public void onClick(View v) {
            String fileStorage = Environment.getExternalStorageDirectory().getPath();
            System.out.println(fileStorage);
            String fileDate = Environment.getDataDirectory().getPath();
            System.out.println(fileDate);
            String storage = getSize(fileStorage);
            String date = getSize(fileDate);
            mTextView.setText("手机内存卡" + storage + "\n手机内存" + date);
        }
    }

    private String getSize(String path) {
        //获得磁盘的状态
        StatFs statFs = new StatFs(path);
        long available = statFs.getAvailableBytes();//可用的内存
        long total = statFs.getTotalBytes();//总共内存
        long free = statFs.getFreeBytes();//自由内存？
        //注意啦，这个Formatter类专门设计为将long转换成内存大小的格式转换器，注意一点的是，Foramtter有好几个，注意导好包呀！！！
        String totalSize = android.text.format.Formatter.formatFileSize(this, total);
        String availableSize = android.text.format.Formatter.formatFileSize(this, available);
        String freeSize = Formatter.formatFileSize(this, free );
        return "总共为" + totalSize + ",可用的内存为：" + availableSize + "自由的内存" + freeSize;
        
        //下面这种方法比较落伍了，直接推荐使用上面方法，首先得到磁盘的对象Statfs,利用其构造方法将路径传过去，便可知道每一个你想知道的内存大小
/*        long avilable = statFs.getAvailableBlocksLong();//可用的扇区
        long count = statFs.getBlockCountLong();//总共的扇区
        long size = statFs.getBlockSizeLong();//每个扇区的大小
        long free = statFs.getFreeBlocksLong();
        //注意啦，这个Formatter类专门设计为将long转换成内存大小的格式转换器，注意一点的是，Foramtter有好几个，注意导好包呀！！！
        String totalSize = android.text.format.Formatter.formatFileSize(this, count * size);
        String availableSize = android.text.format.Formatter.formatFileSize(this, avilable * size);
        String freeSize = Formatter.formatFileSize(this, free * size);
        return "总共为" + totalSize + ",可用的内存为：" + availableSize + "自由的内存" + freeSize;*/
    }

}
