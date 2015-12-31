package com.itheima.binghua.secondactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class MainProgressBar extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_progress_bar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(100);
        
    }

    public void first(View view) {
        //第一进度条,每次增加十个
        mProgressBar.incrementProgressBy(10);
    }
    public void second(View view) {
        //第二进度条，每次增加20个
        mProgressBar.incrementSecondaryProgressBy(20);
    }

}
