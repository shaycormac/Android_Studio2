package com.itheima.binghua.githubdownload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**java中的字节码，源码，反码，补码
 * 
 * ⑴一个数为正，则它的原码、反码、补码相同
 ⑵一个数为负，刚符号位为1，其余各位是对原码取反，然后整个数加1
 byte是一个字节保存的，有8个位，即8个0、1。
 8位的第一个位是符号位，
 也就是说0000 0001代表的是数字1
 1000 0000代表的就是-1
 所以正数最大位0111 1111，也就是数字127
 负数最大为1111 1111，也就是数字-128

 上面说的是二进制原码，但是在java中采用的是补码的形式，下面介绍下什么是补码

 1、反码：
 一个数如果是正，则它的反码与原码相同；
 一个数如果是负，则符号位为1，其余各位是对原码取反；

 2、补码：利用溢出，我们可以将减法变成加法
 对于十进制数，从9得到5可用减法：
 9－4＝5    因为4+6＝10，我们可以将6作为4的补数
 改写为加法：
 9+6＝15（去掉高位1，也就是减10）得到5.

 对于十六进制数，从c到5可用减法：
 c－7＝5    因为7+9＝16 将9作为7的补数
 改写为加法：
 c+9＝15（去掉高位1，也就是减16）得到5.

 在计算机中，如果我们用1个字节表示一个数，一个字节有8位，超过8位就进1，在内存中情况为（100000000），进位1被丢弃。
，因此对于补码：
 ⑴一个数为正，则它的原码、反码、补码相同
 ⑵一个数为负，则符号位为1，其余各位是对原码取反，然后整个数加1

 - 1的原码为                10000001
 - 1的反码为                11111110
 + 1
 - 1的补码为                11111111

 0的原码为                 00000000
 0的反码为                 11111111（正零和负零的反码相同）
 +1
 0的补码为               100000000（舍掉打头的1，正零和负零的补码相同）
 * 
 * 
 * 
 */

public class Main2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = (TextView) findViewById(R.id.textview);
        TextView textView2 = (TextView) findViewById(R.id.textview2);
        //类似于得到请求的getParramers
        Intent intent = getIntent();
        String name =intent.getStringExtra("name");
        //比较关键了，将提交过来的字符串变成一个二进制的字符集合，经他们的十进制相加得到一个整数
        byte[] charname = name.getBytes();
        int numcount = 0;
        for (byte buuf : charname) {
            //转换成10进制相加！！如果不进行&0xff，那么当一个byte会转换成int时，由于int是32位，而byte只有8位这时会进行补位，
           // 例如补码11111111的十进制数为-1转换为int时变为11111111111111111111111111111111好多1啊，呵呵！即0xffffffff但是这个数是不对的，这种补位就会造成误差。
          //  和0xff相与后，高24比特就会被清0了，结果就对了。
           /* int i = buuf & 0xff;//将一个字符的二进制转换成十进制,而0xff默认是整形，Java中的一个byte，
           其范围是-128~127的，如果不进行&0xff，那么当一个byte会转换成int时，对于负数，会做位扩展，
           举例来说，一个byte的-1（即0xff），会被转换成int的-1（即0xffffffff），那么转化出的结果就不是我们想要的了。
           所以，一个byte跟0xff相与会先将那个byte转化成整形运算，这样，结果中的高的24个比特就总会被清0，于是结果总是我们想要的。byte是8位，一个字节，整数
           是32位，4个字节，
            numcount = numcount + i;*/
            numcount += buuf & 0xff;
        }
        //由于汉字的UTF-8编码从负数开始，而且还要考虑长字符创的问题，因此取余
        int last = numcount % 100;//(不够100的不变，取余么，大于100的，余数还是在100之内，由此可见取余，以及商的重要性！！)
        if (last <= 60)
        {

            textView2.setText("人品太差了，需要重新回炉一下");
        }else if (60 < last && last <= 80) {
            textView2.setText("人品还凑合，继续努力！");

        } else {
            textView2.setText("人品碉堡了！，干掉了"+last+"%的人群！！");
            
        } 
       textView.setText("你的人品值为："+last);
    }

}
