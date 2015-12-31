package com.itheima.binghua.smshelper;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.binghua.smshelper.entities.ContactPerson;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    private List<ContactPerson> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
       //通过内容提供者，得到所有手机联系人，定义一个实体接收,将实体放进list中，再将list放进Listview中展示给用户看，定义一个方法
        //得到Uri
       Uri uri= Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uri2 =Uri.parse("content://com.android.contacts/data");
        mList = getDataFromProvider(uri,uri2);
        mList.set(0, new ContactPerson("大王", "5556", "山西朔州"));
        for (ContactPerson contactPerson : mList) 
        {
            System.out.println(contactPerson);
        }
        //得到实体后，放在listview中
        ListView listView = (ListView) findViewById(R.id.list_person);
        //listView.setAdapter(new ArrayAdapter<String>(this, R.layout.content_contacts,list));
        BaseAdapter adapter = new myBaseAdapter();
        for (int i = 0; i < adapter.getCount(); ++i) {

            System.out.println(adapter.isEnabled(i));
        }
        listView.setAdapter(new myBaseAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("data", mList.get(position).getTele());
                setResult(1, intent);
                finish();

            }
        });
    }

    //该方法将得到所有联系人，并将它们放进实体中
    private List<ContactPerson> getDataFromProvider(Uri uriofraw_contact, Uri uriofdata) {
        ContentResolver c = getContentResolver();
        //先从第一个raw_contact表查询
       
        //第一层，获得了id,紧接着去火的第二层
        Cursor cursor = c.query(uriofraw_contact, new String[]{"_id"}, null, null, null);
        List<ContactPerson> list = new ArrayList<>();
        while (cursor.moveToNext()) 
        {
            if (cursor !=null && cursor.getCount()>0) 
            {
                String id = cursor.getString(0);
                Cursor cc = c.query(uriofdata, new String[]{"mimetype", "data1"}, "raw_contact_id =?", new String[]{id}, null);
                ContactPerson contactPerson = new ContactPerson();
                while (cc.moveToNext()) 
                {
                    if (cc != null && cc.getCount() > 0)
                    {
                        String num = cc.getString(0);
                        String data = cc.getString(1);
                        if (num.equals("vnd.android.cursor.item/email_v2")) {
                            contactPerson.setEmail(data);
                            
                        }else if (num.equals("vnd.android.cursor.item/phone_v2")) {
                            contactPerson.setTele(data);
                            
                        }else if (num.equals("vnd.android.cursor.item/name")) 
                        {
                            contactPerson.setName(data);
                        }

                    }

                }
                cc.close();
                list.add(contactPerson);
            }
        }
        cursor.close();
        return list;
    }

     class myBaseAdapter extends BaseAdapter
     {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ContactPerson contactPerson = mList.get(position);
            
            if (convertView != null) {
                view = convertView;
            } else {
                LayoutInflater inflater =Contacts.this.getLayoutInflater();
                view = inflater.inflate(R.layout.listcontact, null);
            }
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView tele = (TextView) view.findViewById(R.id.tele);
            
            name.setText(contactPerson.getName()+":");
            tele.setText(contactPerson.getTele());
           
            return view;
        }
    }
}
