package com.itheima.binghua.secondactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class MainSeekBar extends AppCompatActivity {

    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seek_bar);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setMax(100);
        mSeekBar.setProgress(10);
        mSeekBar.setSecondaryProgress(20);
        mSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                     
                        System.out.println("progress:" + progress + "--fromUser:" + fromUser);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        System.out.println("调用了start方法");

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        System.out.println("调用了stop方法");

                    }
                });
       
    }
    
    public void Go (View view) {

        mSeekBar.incrementProgressBy(10);
        mSeekBar.incrementSecondaryProgressBy(20);
        
    }

}
