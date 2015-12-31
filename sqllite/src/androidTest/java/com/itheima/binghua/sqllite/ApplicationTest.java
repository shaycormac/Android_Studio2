package com.itheima.binghua.sqllite;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.itheima.binghua.StudentDao.StudentDao;
import com.itheima.binghua.transaction.TestTransaction;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private final String TAG = "TestStudentDao";
    public ApplicationTest() 
    {
        super(Application.class);
    }

    /**
     * 在as提供的方法中测试
     * 
     */
    public void test () 
    {
        final int i = 2;
        final int j = 2;
        assertEquals(i, j);
    }
    public void testAddStudent () 
    {
        StudentDao studentDao = new StudentDao(getContext());
        studentDao.addStudent(new Student("小凡", 'F', 88.5));
        
    }
    public void testUpdateStudent ()
    {
        StudentDao studentDao = new StudentDao(getContext());
        studentDao.updateStudent(3, "谢伊寇马克");
        
    }
    public void testDeleteStudent ()
    {
        StudentDao studentDao = new StudentDao(getContext());
        studentDao.deleteStudent(5);
        
    }
    public void testQueryAllStudent () {
        StudentDao studentDao = new StudentDao(getContext());
        List<Student> studentList = studentDao.queryAll();
        for (Student student : studentList) {
            Log.i(TAG, student.toString());
        }
    }
    public void testQueryStudent ()
    {
        StudentDao studentDao = new StudentDao(getContext());
        Student student = studentDao.queryStudent(3);
        Log.i(TAG, student.toString());
    }
    
    public void testTransaction () {
        TestTransaction testTransaction = new  TestTransaction(getContext());
        //测试成功！！第一个人少了500，同时第二个人增加500！如果中间的侦测不到，就不执行！！
        testTransaction.testTransaction();
    }
    
    public void testEfficience () {
        TestTransaction test = new TestTransaction(getContext());
        test.testEfficiency();

    }
    
    public void testEfficienceTransaction ()
    {
        TestTransaction test = new TestTransaction(getContext());
        test.testEfficiencyTransaction();
        
    }
}