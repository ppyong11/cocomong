package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;

import java.util.ArrayList;

public class RefAddActivity extends Activity {
    EditText et_refName;
    Button btn_cancel, btn_ok;
    RefListItemDto refListItemDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ref_popup);

        et_refName=findViewById(R.id.et_refName);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_ok=findViewById(R.id.btn_ok);

        et_refName.setEnabled(true);

        btn_cancel.setOnClickListener(v->{
            finish();
        });

        btn_ok.setOnClickListener(v->{
            refListItemDto =new RefListItemDto(et_refName.getText().toString());
            RefFoodMap.getRefFoodMap().put(refListItemDto,new ArrayList<>());
            RefridgeActivity.refListItemDtos.add(refListItemDto);
            finish();
        });

    }
}
