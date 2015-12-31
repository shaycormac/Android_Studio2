package com.itheima.binghua.listview.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.binghua.listview.db.StudentSQLliteOpenHelper;
import com.itheima.binghua.listview.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-14 17:52 GMT+8
 * @email 574583006@qq.com
 * 相当于MVC设计模式下的dao层，对数据的增删改查
 */
public class StudentDao {
    //这两步骤目的在于创建一个帮助对象，而由于帮助对象需要构造函数里面的上下文，因此在此方法的构造函数中创建
    private StudentSQLliteOpenHelper sso = null;

    public StudentDao(Context context) {
        sso = new StudentSQLliteOpenHelper(context);

    }
    //增删改查的模版实际上是一样的，即建立数据库，连接数据库，增删改查，关闭数据库

    public void addStudent(Student student) {
        SQLiteDatabase datbase = sso.getWritableDatabase();//注意啦，当这个方法调用时候，在你的内存中，创建数据库表/data/data/包名/databases/firstSQLite.db
        if (datbase.isOpen()) {
            datbase.execSQL("insert into Student (name,sex,score) values(?,?,?);", new Object[]{student.getName(), student.getSex(), student.getScore()});
            //注意，如果上面一句出现异常，程序终止，相当于打开了SQLite，却未关闭，抛出未关闭异常
            datbase.close();
        }

    }

    public void updateStudent(int id, String name) {
        SQLiteDatabase database = sso.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("update Student set name=? where id = ?;", new Object[]{name, id});
            database.close();

        }

    }

    public void deleteStudent(int id) {
        SQLiteDatabase database = sso.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("delete from Student where id =?;", new Integer[]{id});
            database.close();

        }

    }

    //查询单个数据，就简单多了
    public Student queryStudent(int id) {
        SQLiteDatabase database = sso.getReadableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("select id,name ,sex,score from Student where id=?;", new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                /**
                 * 注意啦，这里游标cursor得到的column列数是依照你的查询语句所列出来的，例如，你要是
                 * 写出select name ,,score from Student where id=?;就会抛出异常，找不到相应的列数，同理查询所有一样
                 */
                String name = cursor.getString(1);
                double score = cursor.getDouble(3);
                Student student = new Student(name, 'F', score);
                database.close();
                return student;
            }
            database.close();

        }
        return null;
    }

    //查询比较特殊，首先是读取数据库，其次，使用了游标这个概念！
    public List<Student> queryAll() {
        SQLiteDatabase database = sso.getReadableDatabase();
        if (database.isOpen()) {
            //得到一个游标集合，默认从-1开始，从而需要判断
            Cursor cursor = database.rawQuery("select id,name,sex,score from Student;", null);
            if (cursor != null && cursor.getCount() > 0) {
                List<Student> studentList = new ArrayList<Student>();
                int id;
                String name;
                Student student = new Student();
                double score;
                while (cursor.moveToNext()) {
                    id = cursor.getInt(0);// 取第0列的数据 id
                    name = cursor.getString(1);
                    score = cursor.getDouble(3);
                    studentList.add(new Student(name, 'M', score));
                }
                database.close();
                return studentList; //相当于执行到这儿，提前结束程序！！
            }
            database.close();
        }
        return null;
    }

}
