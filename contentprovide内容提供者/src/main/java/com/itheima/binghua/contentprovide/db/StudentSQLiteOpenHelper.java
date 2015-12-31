package com.itheima.binghua.contentprovide.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-14 17:28 GMT+8
 * @email 574583006@qq.com
 * 数据库的建立过程，新建一个类，继承SQLliteOpenHelper ,重写她的方法，以及必须重写构造函数
 * 里面有四个参数，分别是上下文context，要建的的表的名称，游标工程factory，以及最后的版本，必须大于零！！
 */
public class StudentSQLiteOpenHelper extends SQLiteOpenHelper 
{
    private static final String TAG = "StudentSQLliteOpen";   
    public StudentSQLiteOpenHelper(Context context) 
    {
        //该构造方法自定义
        super(context, "studentForprovide.db", null, 1);
    }

    /**
     * 
     * 数据库第一次创建时回调此方法
     * 初始化一些表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //操作数据库，由于构造函数已经把表的名称建好，下面要做的就是向表中添加数据的基本类型,
        //这就是已经创建好了表，并给这个表穿件了几个基本的数据类型，下面只要添加数据之后，就可以增删改查了
        String sql = "create table Student(id integer primary key,name varchar(40),sex char,score double);";
        db.execSQL(sql);

    }

    /**
     * 
     * 
     * @param db
     * @param oldVersion
     * @param newVersion
     * 数据库的版本号更新时回调此方法,
     * 更新数据库的内容(删除表, 添加表, 修改表)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        if (oldVersion==1&&newVersion==2) 
        {
            Log.i(TAG, "数据更新了！！");
            //在表中再添加一个列属性，譬如身高
            db.execSQL("alter table Student add balance integer;");

        }else if (oldVersion==2&&newVersion==3) {
            //do something 
        }

    }
}
