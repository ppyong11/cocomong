package com.sw.cocomong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refridge_list);
    }

    // 명시적인 인텐트 구조
    /*
    Intent intent = new Intent(this, NextActivity.class);

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }
     */
}