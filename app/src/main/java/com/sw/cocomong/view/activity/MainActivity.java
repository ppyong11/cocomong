package com.sw.cocomong.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sw.cocomong.R;


public class MainActivity extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);

        SharedPreferences sh = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
        username=sh.getString("username", "");

        if(username.isEmpty()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, RefridgeActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }
}
