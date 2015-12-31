package com.itheima.binghua.secondactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class Main2 extends Activity implements View.OnClickListener{
    TimePicker timePicker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

         timePicker = (TimePicker) findViewById(R.id.timepicker);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.i("TagTime", "小时" + hourOfDay + "分钟" + minute);
            }
        });

    
    }

    @Override
    public void onClick(View v)
    {
        timePicker.setHour(11);
        timePicker.setIs24HourView(true);
        timePicker.setMinute(50);
        Log.d("TimePicker", "现在的时间是：" + timePicker.getHour() + ":" + timePicker.getMinute());

    }
}
