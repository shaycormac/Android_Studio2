package com.itheima.binghua.sqllite;

import android.app.Activity;
import android.os.Bundle;

import com.itheima.binghua.StudentDao.StudentDao;

public class MainSQLlite extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqllite);
        Student student = new Student("炳华儿", 'F', 88.5);
        StudentDao studentDao = new StudentDao(this);
        studentDao.addStudent(student);
        
    }
}
