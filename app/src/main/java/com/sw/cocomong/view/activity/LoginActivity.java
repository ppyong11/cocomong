package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.task.LoginTask;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    Button login, join;
    EditText name, pw, pwCheck;
    String username;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = findViewById(R.id.btn_login);
        join = findViewById(R.id.btn_join);

        name = findViewById(R.id.et_loginName);
        pw = findViewById(R.id.et_loginPw);
        pwCheck = findViewById(R.id.et_loginPwCheck);

        pwCheck.setVisibility(View.GONE);

        hideKeyboard();

        login.setOnClickListener(v -> {
            try {
                username = name.getText().toString();
                LoginTask loginTask = new LoginTask(name.getText().toString(), pw.getText().toString());
                String result = loginTask.execute(name.getText().toString(), pw.getText().toString()).get();
                // Log.w("받은값 (Login): ", result);

                if (loginTask.getResponseCode() == 200) {
                    SharedPreferences sh = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("username",username);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, RefridgeActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(this, "다시 입력하세요.", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        join.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        });
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }
}