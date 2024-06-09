package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.task.reftask.RefAllDeleteTask;
import com.sw.cocomong.task.usertask.UserWithdrawTask;

import org.json.JSONException;

public class SettingActivity extends Activity {
    Button  refClear, logout, withdraw;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_popup);
        Intent nameIntent=getIntent();
        Bundle nameExtras=nameIntent.getExtras();
        username=nameExtras.getString("username");

        logout=findViewById(R.id.btn_logout);
        refClear=findViewById(R.id.btn_refclear);
        withdraw=findViewById(R.id.btn_withdraw);

        logout.setOnClickListener(v->{
            SharedPreferences sh = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        refClear.setOnClickListener(v->{
            try {
                new RefAllDeleteTask(null,username);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            finish();
            // 등등등
        });
        withdraw.setOnClickListener(v->{
            try {
                new UserWithdrawTask(username);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            SharedPreferences sh = getSharedPreferences("UserSharedPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
