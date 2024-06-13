package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    List<RefObj> refObjs=new ArrayList<>();
    List<String> refNames=new ArrayList<>();
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
        hideKeyboard();

        try {
            new RefListGetTask(username,this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        Log.i("tag",refNames.toString());
        btn_cancel.setOnClickListener(v->{
            finish();
        });

        btn_ok.setOnClickListener(v->{
            String refName = et_refName.getText().toString();

            if (refNames.contains(refName)) {
                // refName이 refNames에 포함되어 있는 경우
                Toast.makeText(this, "같은 이름의 냉장고가 있습니다.", Toast.LENGTH_SHORT).show();
            } else if (refName.equals("")) {
                Toast.makeText(this, "냉장고 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            }else{
                // refName이 refNames에 포함되어 있지 않은 경우
                try {
                    RefAddTask refAddTask = new RefAddTask(refName, username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    @Override
    public void onRefListReceived(List<RefObj> refList) {
        refObjs.clear();
        refObjs.addAll(refList);
        for(RefObj ref : refObjs){
            refNames.add(ref.getRefName());
        }
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