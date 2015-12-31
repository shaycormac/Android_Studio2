package com.itheima.binghua.transaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.itheima.binghua.db.StudentSQLliteOpenHelper;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-16 17:45 GMT+8
 * @email 574583006@qq.com
 * 事务的重要性，效率要比不用高出10倍，尤其查询多量数据的时候，
 * 事务主要应用的场景即两者一致性，打个比方，去银行汇款，我的钱少了，对方的钱多了，要一致，任何一方不同步，就不可以。
 */
public class TestTransaction {
    private final String TAG = "数据在变化！！";
    private StudentSQLliteOpenHelper sso = null;

    //通过这两步初步建立了一个帮助对象，从而在下面实现各种功能
    public TestTransaction(Context context) {
        sso = new StudentSQLliteOpenHelper(context);
    }

    public void testTransaction() {
        //这一步骤执行完，数据库做了两个动作，一个是先查找是否有要建立的数据库，以及是否更新
       SQLiteDatabase database = sso.getWritableDatabase();
        Log.i(TAG, "Update Method is Running!");
        //建立一个事务，从开头到判断是否成功，到fianlly里面写上关闭事务，一个不能少！！,模版这是
        if (database.isOpen()) {
            try {
                database.beginTransaction();
                //完成注释这两步骤，便有钱啦，可以用食物进行操作啦
               /* database.execSQL("update Student set balance =? where id=?;", new Integer[]{1000, 1});
                database.execSQL("update Student set balance =? where id=?;", new Integer[]{1000,2});*/
                database.execSQL("update Student set balance =balance -500 where id=?;",new Integer[]{1});
                //故意抛出一个异常，使下面的代码不会执行，当事务没侦测到成功的时候，数据不会变化
                //int i = 10 / 0;
                database.execSQL("update Student set balance =balance +500 where id=?;",new Integer[]{2});
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
        database.close();

    }

    //下面这个方法测试事物的效率，假如添加1万条数据，所用的时间，不用事务和用事务所花费的时间
    public void testEfficiency() {
        SQLiteDatabase database = sso.getWritableDatabase();
        if (database.isOpen()) {
            long time1 =System.currentTimeMillis();
            for (int i=0;i<4000;++i)
            {
                database.execSQL("insert into Student(name,sex,score,balance) values(?,?,?,?);",new Object[]{"wori",'F',80.8,100+i});
            }
            long time2 =System.currentTimeMillis();
            long time = time2 - time1;
            Log.i(TAG,"该程序执行共花费了"+ String.valueOf(time/1000)+"秒"); //花费4秒
            database.close();
        }
    }
    public void testEfficiencyTransaction() {
        SQLiteDatabase database = sso.getWritableDatabase();
        if (database.isOpen()) {
            // 1. 记住当前的时间
            long time1 =System.currentTimeMillis();
            // 2. 开始添加数据
            try {
                database.beginTransaction();
                for (int i=0;i<4000;++i)
                {
                    database.execSQL("insert into Student(name,sex,score,balance) values(?,?,?,?);",new Object[]{"操你妈",'M',80.8,400+i});
                }
                database.setTransactionSuccessful();
            } finally {
             database.endTransaction();
            }
            // 3. 记住结束时间, 计算耗时时间
            long time2 =System.currentTimeMillis();
            long time = time2 - time1;
            Log.i(TAG,"该程序执行共花费了"+ String.valueOf(time)+"豪秒"); //花费0.474秒，可见使用事务的碉堡了之处！！！
            database.close();
        }
    }
        
    }
