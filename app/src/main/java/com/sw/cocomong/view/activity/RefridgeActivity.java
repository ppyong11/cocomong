package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.foodtask.RecipeListGetTask;
import com.sw.cocomong.task.reftask.RefListGetTask;
import com.sw.cocomong.view.adapter.RefAdapter;

import org.json.JSONException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class RefridgeActivity extends AppCompatActivity implements RefListGetTask.RefListGetTaskListener{

    ListView list;
    ImageButton refAdd, setting;

    RefAdapter refAdapter;
    List<RefObj> refObjs=new ArrayList<>();
    String username;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refridge_list);
        Intent nameIntent=getIntent();
        Bundle nameExtras=nameIntent.getExtras();
       // username=nameExtras.getString("username");
        username="greatcloud13";
        try {
            new RefListGetTask(username, this);
            //refList=refListGetTask.getList();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        list = findViewById(R.id.list_ref);
        refAdd = findViewById(R.id.btn_refplus);
        setting=findViewById(R.id.btn_setting);


        refAdapter = new RefAdapter(RefridgeActivity.this, refObjs );
        list.setAdapter(refAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RefridgeActivity.this, UserActivity.class);
            intent.putExtra("refname", refObjs.get(position).getRefName());
            //intent.putExtra("refnum",refObjs.get(position).getRefnum());
            intent.putExtra("username",username);
            startActivity(intent);

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Intent intent = new Intent(RefridgeActivity.this, RefDeleteActivity.class);
                intent.putExtra("refname", refObjs.get(position).getRefName());
                //intent.putExtra("refnum",refObjs.get(position).getRefnum());
                intent.putExtra("username",username);
                startActivity(intent);
                refAdapter.notifyDataSetChanged();

                // 이벤트 처리 종료 , 여기만 리스너 적용시키고 싶으면 true , 아니면 false
                return true;
            }
        });

        refAdd.setOnClickListener(v -> {
            Intent intent = new Intent(RefridgeActivity.this, RefAddActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        });

        setting.setOnClickListener(v->{
            Intent intent = new Intent(RefridgeActivity.this, SettingActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefListReceived(List<RefObj> refObjs) {
        this.refObjs.clear();
        this.refObjs.addAll(refObjs);
        updateRefUI();
    }
    private void updateRefUI() {
        refAdapter.notifyDataSetChanged();
    }

}
