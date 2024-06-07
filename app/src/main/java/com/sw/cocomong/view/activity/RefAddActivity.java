package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.reftask.RefAddTask;
import com.sw.cocomong.task.reftask.RefListGetTask;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RefAddActivity extends Activity implements RefListGetTask.RefListGetTaskListener {
    EditText et_refName;
    Button btn_cancel, btn_ok;
    // RefListItemDto refListItemDto;
    RefObj refObj;
    List<RefObj> refObjs;
    List<String> refNames;
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
            finish();
        });

        btn_ok.setOnClickListener(v->{
            String refName = et_refName.getText().toString();
            refObjs.forEach(ref-> refNames.add(ref.getRefName()));

            if(refNames.contains(refName)) Toast.makeText(this,"같은 이름의 냉장고가 있습니다.",Toast.LENGTH_SHORT).show();
            else{
                try {
                    RefAddTask refAddTask = new RefAddTask(et_refName.getText().toString(),username);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                finish();
            }

        });
    }

    @Override
    public void onRefListReceived(List<RefObj> refList) {
        refObjs.clear();
        refObjs.addAll(refList);
    }
}
