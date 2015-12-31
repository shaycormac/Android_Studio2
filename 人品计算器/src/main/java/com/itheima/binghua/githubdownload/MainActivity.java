package com.itheima.binghua.githubdownload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**时间：2015年11月30日 13:03:32
 * 功能：
 * 显式意图  ：必须指定要激活的组件的完整包名和类名 （应用程序之间耦合在一起）
  一般激活自己应用的组件的时候 采用显示意图，不指定动作，也不指定数据 直接指定要激活的组件

 隐式意图： 只需要指定要动作和数据就可以 （ 好处应用程序之间没有耦合）
 激活别人写的应用  隐式意图， 不需要关心对方的包名和类名

 * 
 * 
 */
public class MainActivity extends AppCompatActivity 
{

    private EditText mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (EditText) findViewById(R.id.text);
        
      
    }

    public void calscore(View view) {
        //显示意图用在自己的程序中，而隐形用图用在调用系统的软件总做时候使用
        String data =mText.getText().toString().trim();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "名字不能为空！", Toast.LENGTH_SHORT).show();
            return;
            
        }
        Intent intent = new Intent(this, Main2.class);
        //将值传到下一个页面
        intent.putExtra("name", data);
        startActivity(intent);


    }
}
