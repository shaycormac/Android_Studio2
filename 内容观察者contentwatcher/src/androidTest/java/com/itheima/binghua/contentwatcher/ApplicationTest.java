package com.itheima.binghua.contentwatcher;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
{

    private ContentResolver mContentResolver;

    public ApplicationTest() {
        super(Application.class);
    }
    
    
    public void testContentWatchInsertApplication() {
        Uri uri = Uri.parse("content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/insert");
        mContentResolver = getContext().getContentResolver();
        //由于获取的是插入操作，因此选择插入操作
        ContentValues values = new ContentValues();
        values.put("name", "潘金莲");
        values.put("sex", "F");
        values.put("score", 99.87);
        uri = mContentResolver.insert(uri, values);
        Log.i("地址为：","uri"+uri);
        long id = ContentUris.parseId(uri);
        Log.i("插入的位置是：", String.valueOf(id));
        
        
    }
    
    public void testContentWatchUpateApplication () {
        Uri uri = Uri.parse("content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/update");
        mContentResolver =getContext().getContentResolver();//得到内容观察者使用对象
        ContentValues values = new ContentValues();
        values.put("name", "隔壁老王");
       int count = mContentResolver.update(uri, values, "id=?", new String[]{String.valueOf(1)});
        Log.i("更新的位置:", "count" + count);

    }
}