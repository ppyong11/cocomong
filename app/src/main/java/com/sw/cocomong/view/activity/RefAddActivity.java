package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.foodtask.FoodDeleteTask;
import com.sw.cocomong.task.reftask.RefAddTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class RefAddActivity extends Activity {
    EditText et_refName;
    Button btn_cancel, btn_ok;
    // RefListItemDto refListItemDto;
    RefObj refObj;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ref_popup);

        Intent nameIntent=getIntent();
        Bundle nameExtras=nameIntent.getExtras();
        username=nameExtras.getString("username");

        et_refName=findViewById(R.id.et_refName);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_ok=findViewById(R.id.btn_ok);

        et_refName.setEnabled(true);

        btn_cancel.setOnClickListener(v->{
            try {
                RefAddTask refAddTask = new RefAddTask("test1", "test2");
                FoodDeleteTask foodDeleteTask = new FoodDeleteTask(31);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finish();
        });

        btn_ok.setOnClickListener(v->{


        });
    }
}
