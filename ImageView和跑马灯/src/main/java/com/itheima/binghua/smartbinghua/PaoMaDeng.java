package com.itheima.binghua.smartbinghua;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-18 19:54 GMT+8
 * @email 574583006@qq.com
 */
public class PaoMaDeng extends TextView
{
    public PaoMaDeng(Context context) {
        super(context);
    }

    public PaoMaDeng(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaoMaDeng(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean isFocused () {
        return true;
    }
    

}
