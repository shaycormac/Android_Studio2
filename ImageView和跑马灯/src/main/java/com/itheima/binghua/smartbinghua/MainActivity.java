package com.itheima.binghua.smartbinghua;

import android.app.Activity;
import android.os.Bundle;

/**如何在已有的moudle中新建一个布局，然后开机并运行这个布局呢？就相当于新的一个程序，只不过和旧的放在一起罢了！
 * 只需要修改三处文件即可：
 * 1.在java文件中新建一个activity,譬如就叫做activity2
 * public class MainActivity2 extends Activity
 * 2.在AndroidManifest.xml中新建一个<activity>标签，并把过滤器交给他,名字改为.avtivity2
 *  <activity
 android:name=".MainActivity2"
 3.在layout文件夹中新建自己想要的布局，譬如叫做content_main3。xml,在里面布局自己想要的格式
 4.重点，在activity_main.xml文件中修改两点：：
 一是  tools:context=".activity2">修改成自己的activity2
 *二是 <include layout="@layout/content_main3"></include>在这里面修改布局文件名为自己的
 * 
 * 这样就相当于在旧的文件中新建了一个全新的启动类！！
 * 
 * 
 */
public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        
    }
}
