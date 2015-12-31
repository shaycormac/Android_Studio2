package com.itheima.binghua.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArrayAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);
        ListView listView = (ListView) findViewById(R.id.list_array);

        String[] arrayString = new String[]{"我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1","我想展示的1",};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayString);
        //需要注意的是 android.R.layout.simple_list_item_1 ，这表示安卓系统自己带的布局，学会了解，淡然，你也可以自己设置布局，array和simple两个布局不常用
        listView.setAdapter(arrayAdapter);
        
    }

}
