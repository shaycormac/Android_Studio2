package com.itheima.binghua.malegebi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlActivity extends Activity {
    private static final String TAG = "testXml";
    Person person;


    //创建一个集合，用来储存person类
    private List<Person> getList() {
        List<Person> list = new ArrayList<Person>();
        for (int i = 0; i < 10; ++i) {
            list.add(new Person(10000 + i, "李逍遥" + i, 15 + i));
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.button1);
        /**
         * 这个监听器实现了将一个集合中的person对象，依序列化格式，写进本地的一个xml文件中。
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获得序列化对象
                XmlSerializer serializer = Xml.newSerializer();
                try {
                    //开启一个写流。写到本地的外储存卡中，记住读写权限哦！！
                    File file = new File(Environment.getExternalStorageDirectory(), "person.xml");
                    FileOutputStream ops = new FileOutputStream(file);
                    // 指定序列化对象输出的位置和编码
                    serializer.setOutput(ops, "utf-8");
                    //实现开头,写开始 <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
                    serializer.startDocument("utf-8", true);

                    //总体的人类<Persons>
                    serializer.startTag(null, "persons");
                    for (Person person : getList()) {
                        //开始写人<person>
                        serializer.startTag(null, "person");
                        //人带的属性<person id="">
                        serializer.attribute(null, "id", String.valueOf(person.getId()));
                        //写人的名字<name>
                        serializer.startTag(null, "name");
                        serializer.text(person.getName());
                        serializer.endTag(null, "name");//</name>
                        //写人的年龄<age>
                        serializer.startTag(null, "age");
                        serializer.text(String.valueOf(person.getAge()));
                        serializer.endTag(null, "age");
                        //结束写人的标签
                        serializer.endTag(null, "person");
                        Log.i(TAG, person.toString());
                    }
                    serializer.endTag(null, "persons");
                    serializer.endDocument(); //结束

                    // Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    //  Toast.makeText(this, "输出错误", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //这个监听器实现了将设备中的某一xml文件读取出来，存到一个集合中去
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person2 = null;
                List<Person> personList = null;
                String id = null;
                String tagname = null;
                try {
                    //获取解析器对象
                    XmlPullParser xmlPullParser = Xml.newPullParser();
                    //创建读取流
                    File file = new File(Environment.getExternalStorageDirectory(), "person.xml");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    xmlPullParser.setInput(fileInputStream, "utf-8");
                    //这是解析器读取标签的时候，回调方法事件，根据名字不同，读取出来做出不同的操作
                    int typeinvent = xmlPullParser.getEventType();
                    //循环体在解析文档，只要没到结束，就会执行下去
                    while (typeinvent != XmlPullParser.END_DOCUMENT) {
                        tagname = xmlPullParser.getName();
                        switch (typeinvent) {
                            case XmlPullParser.START_TAG:
                                if ("persons".equals(tagname)) {
                                    //解析这个根标签时候，创建集合，存取person类
                                    personList = new ArrayList<Person>();
                                    //解析完<persons>,跳出swit，执行寻找下一个头标签
                                } else if ("person".equals(tagname)) {
                                    //解析这个标签的时候，读取到person类，需要创建实例对象,用来储存
                                    person2 = new Person();
                                    //由于这里面含有属性，因此，获取属性，并且赋给person类
                                     id = xmlPullParser.getAttributeValue(null, "id");
                                    person2.setId(Integer.parseInt(id));
                                    //解析完<person>,代码执行到break,跳出swit，执行寻找下一个头标签
                                }else if ("name".equals(tagname)) {
                                    person2.setName(xmlPullParser.nextText());
                                    //解析完<name>,代码执行到break,跳出swit，执行寻找下一个头标签

                                }else if ("age".equals(tagname)) {
                                    person2.setAge(Integer.parseInt(xmlPullParser.nextText()));
                                }
                                break;
                            //当解析标签尾部的时候，执行事件，其实按顺序，应该最先执行的尾部标签是name,接着age,person,最后才是persons
                            case XmlPullParser.END_TAG:
                                if ("name".equals(tagname)) {
                                    Log.i(TAG, "获得了名字属性！！");
                                }else if ("age".equals(tagname)) {
                                    Log.i(TAG, "获得了年龄属性！！");
                                }else if ("person".equals(tagname)) {
                                    personList.add(person2);
                                    Log.i(TAG, "获得了人的属性！！");
                                }else if ("persons".equals(tagname)) {
                                    for (Person person3:personList) {
                                        Log.i(TAG, person3.toString());
                                    }
                                }
                                break;
                            default:
                                break;

                        }
                        typeinvent = xmlPullParser.next();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        });

    }


    /**
     * 读取当地的xml,回写到一个集合中，并显示出来
     */


   /* public List<Person> local2Xml() {
        //和上面类似，先开辟一个读流，先要获取权限哦
        File file = new File(Environment.getExternalStorageDirectory(), "person.xml");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            //获取pull解析器对象
            XmlPullParser xmlPullParser = Xml.newPullParser();
            // 指定解析的文件和编码格式
            xmlPullParser.setInput(fileInputStream, "utf-8");
            int eventType = xmlPullParser.getEventType();//获取事件类型，可能开始标签，也可能结束标签
            //将取出来的人放到容器中去
            List<Person> list2 = null;
            Person person2 = null;
            String id; //作为人的属性
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equals("persons")) {
                            list2 = new ArrayList<Person>();
                        } else if (tagname.equals("person2")) {
                            person2 = new Person();
                            id = xmlPullParser.getAttributeValue(null, "id");
                            person2.setId(Integer.parseInt(id));
                        } else if (tagname.equals("name")) {
                            person2.setName(xmlPullParser.nextText());
                        } else if (tagname.equals("age")) {
                            person2.setAge(Integer.parseInt(xmlPullParser.nextText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equals("name")) {
                            Log.e("姓名！！", person2.getName());
                        } else if (tagname.equals("age")) {
                            Log.e("年龄！！", String.valueOf(person2.getAge()));
                        } else if (tagname.equals("person2")) {
                            list2.add(person2);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();

            }
            return list2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }*/

}
