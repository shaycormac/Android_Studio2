package com.itheima.binghua.receivesms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main1 extends AppCompatActivity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        
     
        
    }
    public void click (View view) {
        //创建显性意图
       /* Intent intent = new Intent();
        intent.setClassName(getPackageName(), "com.itheima.binghua.receivesms.Main2");*/
        //第二种，直接在意图的构造方法中实现参数
        /*Intent intent = new Intent(this, Main2.class);
        startActivity(intent);*/
        //显性意图一般不灵活，多使用隐式意图
        /*注意了，使用时注意
        1，创建意图对象
        2，设置动作，添加category分类（有默认，车载什么的）,设置数据值和类型，类型
        3.启动意图。这是在代码中
        4.在清单文件中找到要启动的第二个类，设置意图过滤器intentfilter
        在里面设置和类差不多的内容
        <action 你的动作
        <category 你的种类
        <data minetype 你的类型
        <data scheme 你的规划，即前缀，
        这才完成
        *
        * */
        Intent intent = new Intent();
        intent.setAction("com.itheima.binghua.receivesms.open");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.parse("jianren:张三"), "application/person");
        startActivity(intent);
    }

}
