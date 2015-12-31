package com.itheima.binghua.listview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.binghua.listview.dao.StudentDao;
import com.itheima.binghua.listview.domain.Student;

import java.util.List;

public class MainListView extends Activity 
{
    private final String TAG = "使用缓存对象";
    private final String TAG1 = "使用新建对象";
    private List<Student> list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_view);
        //获取listView对象
        ListView listView = (ListView) findViewById(R.id.listview);
        //获取连接器
        
        listView.setAdapter(new MyAdapter() );
        
        
        
       
    }
    
    class MyAdapter extends BaseAdapter
    {
        List<Student> list1 = listStudent();

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            
            return list1.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return null;
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //第93到98行的目的在于建立一个TextView对象，是否新建一个对象取决于缓存里面是否有对象，第一次打开，没有，之后全部用缓存对象
            TextView tv = null;
            if (convertView == null) {
                tv = new TextView(MainListView.this);
                Log.i(TAG1, "新建对象" + position);
            } else {
                tv = (TextView) convertView;
                Log.i(TAG, "复用缓存" + position);
            }
            tv.setText(list1.get(position).toString());
            tv.setTextSize(20);
            
            return tv;
        }
    }
    public List<Student> listStudent () {
        
        StudentDao studentDao = new StudentDao(MainListView.this);
        Student student = null;
        for (int i = 0; i < 20; ++i) {
            student = new Student("绿帽子" + i, 'M', 56.2 + i);
            studentDao.addStudent(student);
        }
        list = studentDao.queryAll();
        return list;
    }
}
