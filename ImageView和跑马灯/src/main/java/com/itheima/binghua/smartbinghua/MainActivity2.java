package com.itheima.binghua.smartbinghua;

import android.app.Activity;
import android.os.Bundle;

/**
 * 实现多行TextView跑马灯滚动效果
 * 1.在布局文件中，TextView中，设置三个属性
 * android:ellipsize="marquee"
 android:focusable="true"
 android:focusableInTouchMode="true"//实现单行得到焦点
 同时实现单行输出的话
 android:singleLine="true"
 其实这四步只能实现一个标签跑马灯
 2，自定义一个类，继承TextView抽象类，写出三个构造函数，并
   复写他的 public boolean isFocused () {
 return true; //实现了所有的行都得到焦点！！
 }
 3.将布局文件的标签<TextView></TextView>变成类的包名加上类，就搞定了
 com.itheima.binghua.smartbinghua.PaoMaDeng
 * 
 * 
 */

public class MainActivity2 extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
}
