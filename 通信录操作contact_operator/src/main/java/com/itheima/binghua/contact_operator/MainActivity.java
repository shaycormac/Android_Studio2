package com.itheima.binghua.contact_operator;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {
    /**
     * 需要操作的两个uri
     * 1."com.android.contacts/raw_contacts"
     * 2."raw_contacts/data"
     * 
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    
    
    private void queryContacts () {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uri1 = Uri.parse("content://com.android.contacts/data");
       Cursor cursor= getContentResolver().query(uri, new String[]{"contact_id"}, null, null, null);
        Cursor cursor1 = null;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) 
            {
            String id = cursor.getString(0);
            // System.out.println(id);
            cursor1 = getContentResolver().query(uri1, new String[]{"mimetype_id", "data1"}, "raw_contact_id=?", new String[]{id}, null);
            if (cursor1 != null && cursor1.getCount() > 0) 
            {
                while (cursor1.moveToNext()) 
                {

                    String id1 = cursor1.getString(0);
                    if (id1.equals("vnd.android.cursor.item/name")) {
                        String name = cursor1.getString(1);
                    }else if (id1.equals("vnd.android.cursor.item/phone_v2")) {
                        String telephone = cursor1.getString(1);
                    }else if (id1.equals("vnd.android.cursor.item/email_v2")) {
                        String emaile = cursor1.getString(1);
                    }else if (id1.equals("vnd.android.cursor.item/postal-address_v2")) {
                        String address = cursor1.getString(1);
                    }
                }

            }
                System.out.println();
                cursor1.close();
                
            }
        }
        cursor.close();
    }
}
