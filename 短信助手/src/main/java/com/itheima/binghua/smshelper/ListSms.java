package com.itheima.binghua.smshelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListSms extends AppCompatActivity 
{

    private String[] mStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sms);
        ListView listView = (ListView) findViewById(R.id.list_array);
        /*LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.smsunit, null);
        EditText sms = (EditText) view.findViewById(R.id.sms_edittext);*/
        mStrings = new String[]{"女孩儿哭的泪流满面：“你太自私了，从来不在乎我的感受，只知道顾着自己。”男孩儿赶紧捧起她的脸，安慰道：“宝贝别哭好不好，看你流泪我心好痛。”女孩一把甩开男孩的手：“都这个时候了你还只想着自己心会痛！”","相爱的结局，要么是分手，要么是习惯。但实际上，相爱，是可以一直爱下去的。只是爱到底实在太难，太容易被生活阻碍。但如果真有勇气，那就不需要将就。不要经过谁的世界，而要两个人一起，把人生度尽。","有些人爱你，只是想要占有你。他根本不在乎你有什么好，也不关心你的生活，只是要占有你的一切而已。而真正爱你的人，不会想要束缚你，只是看着你的生活，关心着你的心情，爱你而不强迫你。愿你在爱情里自由自在。","情是一种深度，友情是一种广度，而爱情则是一种纯度。亲情是一种没有条件、不求回报的阳光沐浴；友情是一种浩荡宏大、可以安然栖息的理解堤岸；而爱情则是一种神秘无边、可以使歌至到忘情的心灵照耀。人","我们总在不设防的时候喜欢上一些人。也许只是一个温和的笑容，一句关切的问候。可能未曾谋面，可能志趣并不相投，可能不在一个高度，却牢牢地放在心上了。冥冥中该来则来，无处可逃。","是那个人，不说他也懂；不是那个人，说了也没用。是那个人，不解释也没关系；不是那个人，解释也多余。是那个人，不留他也不走；不是那个人，留也留不住。是那个人，不等自然会遇到；不是那个人，原地也会走丢。","woaini"};

        listView.setAdapter(new myAdapet<String>(this, R.layout.smsunit, R.id.sms_textview, mStrings));//设置适配器
        listView.setOnItemClickListener(new myOnItemClickListener());//设置listview中点击某个项目的事件！！

    }

    class myAdapet<String> extends ArrayAdapter<String>
    {
        //第三个参数是也是寻找所要赋值的TextView在R文件中的地址！！

        public myAdapet(Context context, int resource, int textViewResourceId, String[] objects)
        {
            super(context, resource, textViewResourceId, objects);
        }
    }

    class myOnItemClickListener implements AdapterView.OnItemClickListener 
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //得到点击该位置的短信内容
            String sms = mStrings[position];
            //创建一个意图，将本内容复制给前面一个页面
            Intent intent = new Intent();
            intent.putExtra("data", sms);
            //设置数据
            setResult(0, intent);
            //关闭当前页面，并且回传数据 onActivityResult().
            finish();

        }
    }

    

}
