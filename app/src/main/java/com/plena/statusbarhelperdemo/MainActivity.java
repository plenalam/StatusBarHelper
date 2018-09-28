package com.plena.statusbarhelperdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.plena.statusbarhelper.StatusBarHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new StatusBarHelper(this).setSystemStatusBar()
        .setStatusBarLightMode(true)
        .setWindowFullScreen(false);
    }
}
