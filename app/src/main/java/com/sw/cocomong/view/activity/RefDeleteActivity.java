package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;

public class RefDeleteActivity extends Activity {
    Button btn_yes, btn_no;
    RefListItemDto refListItemDto;
    int refPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_choice);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refPosition=extras.getInt("refPosition");
        //refListItemDto= RefFoodMap.getRefListItemDtos().get(refPosition);

        btn_yes=findViewById(R.id.btn_yes);
        btn_no=findViewById(R.id.btn_no);


        btn_no.setOnClickListener(v->{
            finish();
        });

        btn_yes.setOnClickListener(v->{

            RefFoodMap.getRefListItemDtos().remove(refPosition);
            finish();
        });
    }
}
