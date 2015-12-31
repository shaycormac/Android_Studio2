
package com.itheima.binghua.mamabi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.binghua.mamabi.dao.StudentDao;
import com.itheima.binghua.mamabi.entities.Student;

import java.util.List;

/**
 * 总结：这个练习充分暴露了自己的问题症结所在，
 * 主要知识点：1.使用listview，将想要布局的类型放在另外一个布局文件中，listview还在主布局中
 * 而在lisetview的复写抽象类中，
 * 2.学会将一个布局文件xml变成一个对象 view
 * View view =null;
 *  LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
    view = layoutInflater.inflate(R.layout.listview, null);
 *这就得到了所要生成类型的xml的对象view控件，
 * 3.由于本题中，两个文本都是在新生成的布局文件中，因此得到他们应该是在view对象中，是这样的
 * TextView mTextView = (TextView) view.findViewById(R.id.text);
 TextView mTextView2 = (TextView) view.findViewById(R.id.text2);
 而非：这种是在content_main中去找，你去哪找？容易造成空指针异常抛出
 TextView mTextView = (TextView) findViewById(R.id.text);
 TextView mTextView2 = (TextView) findViewById(R.id.text2);
 * 4.别忘了，对于listview，来说，初次没有缓存对象，因此，条件语句是
 * if (convertView ==null) 
 * 而不是
 * if (view ==null) 
 * ,蛋疼！！！！！
 */

public class MainActivity extends Activity {

   // private LinearLayout mLinearLayout;
    private List<Student> mStudentList;
   
    private final String TAG = "LayInfalterDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        StudentDao studentDao = new StudentDao(this);
        for (int i = 0; i < 20; ++i) {

            studentDao.addStudent(new Student("冠希" + i, 'F', 20 + i));
            Log.e(TAG, "生成了一个对象");
        }
        mStudentList = studentDao.queryAll();


        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new myAdapter());
    }

    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStudentList.size();
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
        public View getView(int position, View convertView, ViewGroup parent) 
        {
           // mLinearLayout = (LinearLayout) findViewById(R.id.llu);
            View view = null;
            if (convertView ==null) {
                //布局填充其对象，将xml变成View控件对象，是整个xml哦，因此69行的代码才是R.layout.content_main！！
                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                view = layoutInflater.inflate(R.layout.listview, null);
                Log.i(TAG,  position+"这个位置新建对象" );
            } else {
                view = convertView;
                Log.i(TAG, position+"这个位置使用了缓存");
            }
            //一定要注意了！！！！！，由于这两个控件不是主布局里面的控件，而是listview里面的，而view正好又是这个布局xml的对象，因此可以用下列两行
            //代码找到这两个控件！！！
            TextView mTextView = (TextView) view.findViewById(R.id.text);
            TextView mTextView2 = (TextView) view.findViewById(R.id.text2);
            Student mStudent = mStudentList.get(position);
            mTextView.setText(mStudent.getName());
            mTextView2.setText(String.valueOf(mStudent.getScore()));
            Log.i(TAG, "正在输出对象！！");

            return view;
        }
    }
}
