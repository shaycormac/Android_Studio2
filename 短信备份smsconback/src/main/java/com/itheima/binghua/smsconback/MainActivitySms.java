package com.itheima.binghua.smsconback;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itheima.binghua.smsconback.entities.SmsInfomation;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivitySms extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_sms);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new myListener());
    }

    class myListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //select _id,address,data,type,body from sms;
            Uri uri = Uri.parse("content://sms/");
            ContentResolver contentResolver =getContentResolver();
            Cursor cursor = contentResolver.query(uri, new String[]{"_id", "address", "date", "type", "body"}, null, null, null);
            if (cursor!=null && cursor.getCount() > 0) 
            {
                SmsInfomation sms = null;
                List<SmsInfomation> list = new ArrayList<SmsInfomation>();
                while (cursor.moveToNext()) 
                {
                    sms = new SmsInfomation();
                    sms.set_id(cursor.getInt(0));
                    sms.setAddress(cursor.getString(1));
                    sms.setDate(cursor.getLong(2));
                    sms.setType(cursor.getInt(3));
                    sms.setBody(cursor.getString(4));
                    list.add(sms);
                }
                cursor.close();
                //将得到的list写到sd卡中去！！
                if (list!=null) {
                    write2Sdcard(list);
                } else {
                    throw new IllegalArgumentException("list集合为空!");
                }
            }
        }
    }

    private void write2Sdcard(List<SmsInfomation> list) {
        XmlSerializer serializer = Xml.newSerializer();
        File file = Environment.getExternalStorageDirectory();
        File file2 = new File(file,"smsBackup.xml");

        Log.d("路径", file2.getPath());
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file2);
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "smss");
            for (SmsInfomation smsInfomation:list) {
                serializer.startTag(null, "sms");
                serializer.attribute(null, "_id", String.valueOf(smsInfomation.get_id()));
                //ddress,data,type,body
                serializer.startTag(null, "address");
                serializer.text(smsInfomation.getAddress());
                serializer.endTag(null, "address");
                
                serializer.startTag(null, "date");
                serializer.text(String.valueOf(smsInfomation.getDate()));
                serializer.endTag(null, "date");
                
                serializer.startTag(null, "type");
                serializer.text(String.valueOf(smsInfomation.getType()));
                serializer.endTag(null, "type");
                
                serializer.startTag(null, "body");
                serializer.text(smsInfomation.getBody());
                serializer.endTag(null, "body");
                
                serializer.endTag(null, "sms");
            }
            serializer.endTag(null, "smss");
            serializer.endDocument();
            Toast.makeText(this, "备份成功！",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "备份失败！",Toast.LENGTH_SHORT).show();
        }

    }
}
