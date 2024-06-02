package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.view.adapter.RefAdapter;
import com.sw.cocomong.dto.RefListItemDto;

import java.util.ArrayList;
import java.util.List;

public class RefridgeActivity extends AppCompatActivity {

    ListView list;
    ImageButton refAdd;

    RefAdapter refAdapter;
    List<RefListItemDto> refListItemDtos=RefFoodMap.getRefListItemDtos();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refridge_list);

        list = findViewById(R.id.list_ref);
        refAdd = findViewById(R.id.btn_refplus);


        refAdapter = new RefAdapter(RefridgeActivity.this, refListItemDtos );
        list.setAdapter(refAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(RefridgeActivity.this, refListItemDtos.get(position).getName(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RefridgeActivity.this, UserActivity.class);
            intent.putExtra("refPosition", position);
            startActivity(intent);

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Intent intent = new Intent(RefridgeActivity.this, RefDeleteActivity.class);
                intent.putExtra("refPosition", position);
                startActivity(intent);
                refAdapter.notifyDataSetChanged();

                // 이벤트 처리 종료 , 여기만 리스너 적용시키고 싶으면 true , 아니면 false
                return true;
            }
        });



        refAdd.setOnClickListener(v -> {
            Intent intent = new Intent(RefridgeActivity.this, RefAddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refAdapter.notifyDataSetChanged();
    }
}
