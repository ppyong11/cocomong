package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.task.LoginTask;

public class LoginActivity extends AppCompatActivity {
    Button login, join, findPw;
    EditText name, pw, pwCheck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = findViewById(R.id.btn_login);
        join = findViewById(R.id.btn_join);
        findPw = findViewById(R.id.btn_findPw);

        name = findViewById(R.id.et_loginName);
        pw = findViewById(R.id.et_loginPw);
        pwCheck = findViewById(R.id.et_loginPwCheck);

        pwCheck.setVisibility(View.GONE);

        login.setOnClickListener(v -> {
            try {

                LoginTask loginTask = new LoginTask(name.getText().toString(),pw.getText().toString());

                String result= loginTask.execute(name.getText().toString(),pw.getText().toString()).get();
                // Log.w("받은값 (Login): ", result);

                Intent intent = new Intent(LoginActivity.this,RefridgeActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        join.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        });
    }
}
