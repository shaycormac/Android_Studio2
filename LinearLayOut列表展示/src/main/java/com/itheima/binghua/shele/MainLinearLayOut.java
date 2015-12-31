package com.itheima.binghua.shele;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.binghua.shele.dao.StudentDao;
import com.itheima.binghua.shele.domain.Student;

import java.util.List;

public class MainLinearLayOut extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //得到整个布局的控件
       LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llout);
        StudentDao studentDao = new StudentDao(this);
        Student student = null;
        for (int i = 0;i<20;++i) {
            student = new Student("冠希哥" + i, 'M', 56.2 + i);
            studentDao.addStudent(student);
            
        }
        List<Student> studentList = studentDao.queryAll();
        //先判断得到集合是否为空，再进行演示！！
        if (studentList !=null) 
        {
            TextView textView = null;
            for (Student student1 : studentList) {
                textView = new TextView(this);
                textView.setText(student1.toString());
                textView.setTextColor(Color.BLUE);
                linearLayout.addView(textView);

            }
        }
    }
}
