package com.itheima.binghua.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapterActivity extends AppCompatActivity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);
        ListView listView = (ListView) findViewById(R.id.simplelist);
        
        //使用simpleAdapter操作listview，需要做的是把数据通过map储存，map再储存到list中，list再交给构造函数，最后，listview在获取监听，即可
        //在重新新建一个方法来提供这样的list
        List<Map<String, Object>> list = getList();
        SimpleAdapter simple = new SimpleAdapter(this, list, R.layout.simpleadapterlayout, new String[]{ "name","icon"}, new int[]{ R.id.text,R.id.image});
        listView.setAdapter(simple);

        /*SimpleAdapter adapter = new SimpleAdapter(
                this, // 上下文 Contextcontext
                list, // listView绑定的数据 data
                R.layout.listview_item, // listview的子条目的布局的id  resource
                new String[]{"name", "icon"}, 	// list数据中的map集合里的key from
                new int[]{R.id.tv_name, R.id.iv_icon}); // resource 中的id   to*/ 
        
    }
    
    private List<Map<String,Object>>  getList ()
    {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王五六叶子");
        map.put("icon", R.drawable.ic_binghua);
        list.add(map);
        //再新开一个map
        map = new HashMap<String, Object>();
        map.put("name", "嘿嘿");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "吼吼");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "呵呵");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "和鞥哼");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "妈的");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "够俄爱");
        map.put("icon", R.mipmap.ic_launcher);
        map = new HashMap<String, Object>();
        map.put("name", "妈的");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "够俄爱");
        map.put("icon", R.mipmap.ic_launcher);
        map = new HashMap<String, Object>();
        map.put("name", "妈的");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "够俄爱");
        map.put("icon", R.mipmap.ic_launcher);
        map = new HashMap<String, Object>();
        map.put("name", "妈的");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "够俄爱");
        map.put("icon", R.mipmap.ic_launcher);
        map = new HashMap<String, Object>();
        map.put("name", "妈的");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "够俄爱");
        map.put("icon", R.mipmap.ic_launcher);
        map = new HashMap<String, Object>();
        map.put("name", "妈的");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "够俄爱");
        map.put("icon", R.mipmap.ic_launcher);
        list.add(map);
        return list;
    }

}
