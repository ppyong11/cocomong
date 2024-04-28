package com.sw.cocomong.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;

public class LoginActivity extends AppCompatActivity {
    Button login;
    String name;
    String pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = findViewById(R.id.btn_login);
        name="dahee";
        pw="1234";

        login.setOnClickListener(v->{
            Toast.makeText(LoginActivity.this, "실행성공", Toast.LENGTH_SHORT).show();

        });
    }
}
