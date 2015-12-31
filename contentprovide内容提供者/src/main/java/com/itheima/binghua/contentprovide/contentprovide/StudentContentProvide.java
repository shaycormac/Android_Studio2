package com.itheima.binghua.contentprovide.contentprovide;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.itheima.binghua.contentprovide.db.StudentSQLiteOpenHelper;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-18 23:01 GMT+8
 * @email 574583006@qq.com
 */
public class StudentContentProvide extends ContentProvider {
    //建立数据库连接，留给别的项目介入
    StudentSQLiteOpenHelper sso = null;
    //建立Uri匹配器
    private static UriMatcher uriMatcher;
    //建立主机名，别问我为什么，因为UriMatch需要啊，亲，就是包名+类名
    private static final String AUTHORITY = "com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide";
    //code即分机号的代号，为整形,操作person表添加的操作的uri匹配码
    private static final int STUDENT_INSERT_CODE = 0;

    private static final int STUDENT_DELETE_CODE = 1;

    private static final int STUDENT_UPDATE_CODE = 2;

    private static final int STUDENT_QUERYALL_CODE = 3;

    private static final int STUDENT_QUERYALL_ITEM_CODE = 4;

    //查看帮助文档，可知，建立匹配器
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加一些Uri,即（分机号）
        //content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/insert完整路径
        uriMatcher.addURI(AUTHORITY, "Student/insert", STUDENT_INSERT_CODE);
        //content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/delete完整路径
        uriMatcher.addURI(AUTHORITY, "Student/delete", STUDENT_DELETE_CODE);
        //content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/update完整路径
        uriMatcher.addURI(AUTHORITY, "Student/update", STUDENT_UPDATE_CODE);
        //content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/insert完整路径
        uriMatcher.addURI(AUTHORITY, "Student/insert", STUDENT_INSERT_CODE);
        //content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/queryall完整路径
        uriMatcher.addURI(AUTHORITY, "Student/queryall", STUDENT_QUERYALL_CODE);
        //content://com.itheima.binghua.contentprovide.contentprovide.StudentContentProvide/Student/query/#完整路径
        uriMatcher.addURI(AUTHORITY, "Student/query/#", STUDENT_QUERYALL_ITEM_CODE);
    }

    @Override
    public boolean onCreate() {
        //创建的时候就把数据库帮助对象实例化,从而对数据库进行增删改查！
        sso = new StudentSQLiteOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //select id,name,sex,score from Student where id=? name=? order by desc new String[]
        SQLiteDatabase database = sso.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case STUDENT_QUERYALL_CODE:  
                if (database.isOpen()) {
//                    database.close(); 这里不能有关闭数据库，否则游标集合丢失
                  Cursor cursor =   database.query("Student", projection, selection, selectionArgs, null, null, sortOrder);
                    return cursor;//返回游标集合
                }
                break;
            case STUDENT_QUERYALL_ITEM_CODE: 
                if (database.isOpen()) {
                    long id = ContentUris.parseId(uri);//查询的是单条数据, uri末尾出有一个id，得到这个id,从而就简单多了查寻
                   Cursor cursor =  database.query("Student", projection, "id=?", new String[]{String.valueOf(id)}, null, null, sortOrder);//获得这个游标值
                    return cursor;
                }
                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) { //这个复写方法目的在于判断查询的是一条语句还是多条语句，从而返回Cursor
        switch (uriMatcher.match(uri)) {
            case STUDENT_QUERYALL_CODE:
                //返回多条查询目录，是一个文件夹,多条的MIME-type
                return "vnd.android.cursor.dir/Student";

            case STUDENT_QUERYALL_ITEM_CODE:
                //返回单挑的MIME-TYPE
                return "vnd.android.cursor.item/Student";

            default:
                break;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //insert into Student (id,name,sex,score) values(?,?,?,?) new Object[]{}
        switch (uriMatcher.match(uri)) {
            case STUDENT_INSERT_CODE://说明code与添加学生匹配成功，获得权限
                //打开数据库，进行相应操作
                SQLiteDatabase database = sso.getWritableDatabase();
                if (database.isOpen()) {
                    //这样就是写死了
                   /* values.put("name", "张三");
                    values.put("sex", "F");
                    values.put("score", 89.5);*/
                    long id = database.insert("Student", null, values);//返回的是这条插入数据所在的id！！
                    database.close();
                    return ContentUris.withAppendedId(uri, id);//返回是一个uri,
                }
                break;

            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        // delete from Student where id=? and name=?  new Object[]{}

        switch (uriMatcher.match(uri)) 
        {
            case STUDENT_DELETE_CODE:
                SQLiteDatabase database = sso.getWritableDatabase();
                if (database.isOpen()) 
                {
                    int count = database.delete("Student", selection, selectionArgs);
                    database.close();
                    return count;//返回影响的行数！！
                }

                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        update Student set name =? where id =?   new Object[]{}
        switch (uriMatcher.match(uri)) {
            case STUDENT_UPDATE_CODE:
               SQLiteDatabase database =  sso.getWritableDatabase();
                if (database.isOpen()) {
                    int count = database.update("Student", values, selection, selectionArgs);
                    database.close();
                    return count;//返回影响的行数
                }
                break;
            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }
        return 0;
    }
}
