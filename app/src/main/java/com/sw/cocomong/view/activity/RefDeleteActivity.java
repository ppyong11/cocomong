package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.reftask.RefDeleteTask;

import org.json.JSONException;

import java.sql.Ref;

public class RefDeleteActivity extends Activity {
    Button btn_yes, btn_no;
    //RefListItemDto refListItemDto;
    //int refPosition;
    RefObj refObj;
    String refnum,username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_choice);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refnum=extras.getString("refnum");
        username=extras.getString("username");
        //refListItemDto= RefFoodMap.getRefListItemDtos().get(refPosition);


        btn_yes=findViewById(R.id.btn_yes);
        btn_no=findViewById(R.id.btn_no);


        btn_no.setOnClickListener(v->{
            finish();
        });

        btn_yes.setOnClickListener(v->{
            try {
                RefDeleteTask refDeleteTask = new RefDeleteTask(refnum);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            finish();
        });
    }
}
