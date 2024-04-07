package com.sw.cocomong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.refridge_list);
        setContentView(R.layout.favorite_list);
        //startActivity(new Intent(this, FavoriteActivity.class));
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
