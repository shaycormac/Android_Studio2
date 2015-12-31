package com.itheima.binghua.mamabi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.binghua.mamabi.dao.StudentDao;
import com.itheima.binghua.mamabi.entities.Student;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Student mStudent;
    StudentDao mStudentDao;
    private ListView mListView;
    private List<Student> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        mStudentDao = new StudentDao(this);
        for (int i = 0; i < 25; ++i) {
            mStudent = new Student("炳华儿" + i, 'H', 56.2 + i);
            mStudentDao.addStudent(mStudent);
        }
        mListView = (ListView) findViewById(R.id.listview2);
        mList = mStudentDao.queryAll();
        BaseAdapter baseAdapter = new myAdapter();
        mListView.setAdapter(baseAdapter);
        
    }


    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        //这只是某一个单元项
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                //将布局变成一个对象,学会处理
                LayoutInflater layout = Main2Activity.this.getLayoutInflater();
                //这个方法将布局文件变成了view对象，学会啊！！
                view = layout.inflate(R.layout.listview2, null);
                Log.d("TAG", position+"位置创建了新的对象");
            } else {
                view = convertView;
                Log.d("TAG", position+"位置调用了缓存对象");
            }
            TextView textView1 = (TextView) view.findViewById(R.id.text21);
            TextView textView2 = (TextView) view.findViewById(R.id.text22);
            textView1.setText(mList.get(position).getName());
            textView2.setText(String.valueOf(mList.get(position).getScore()));
            return view ;
        }
    }

}
