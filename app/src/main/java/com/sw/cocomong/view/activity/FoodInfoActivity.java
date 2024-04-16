package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.view.adapter.FoodListItem;


public class FoodInfoActivity extends AppCompatActivity {
    TextView title;
    ImageButton back, edit;
    Button save, delete;
    EditText foodName, category, expire, memo;
    FoodListItem foodListItem;
    int position;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        position=extras.getInt("position");



        title=findViewById(R.id.tv_infoTitle);
        back=findViewById(R.id.btn_back);
        edit=findViewById(R.id.btn_edit);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        foodName =findViewById(R.id.et_infoFoodName);
        category=findViewById(R.id.et_infoCategory);
        expire=findViewById(R.id.et_infoExpire);
        memo=findViewById(R.id.et_memo);

        save.setVisibility(View.GONE);
        delete.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);

        foodName.setEnabled(false);
        category.setEnabled(false);
        expire.setEnabled(false);
        memo.setEnabled(false);

        back.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(), MainFoodListActivity.foodListItems.get(2).getName(),Toast.LENGTH_SHORT).show();
        });


        edit.setOnClickListener(v->{
            save.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
            edit.setClickable(false);

            foodName.setEnabled(true);
            category.setEnabled(true);
            expire.setEnabled(true);
            memo.setEnabled(true);
        });

        save.setOnClickListener(v->{
            //Toast.makeText(getApplicationContext(),"save 눌림",Toast.LENGTH_SHORT).show();

            // 변경불가
            foodName.setEnabled(false);
            category.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            // foodListItems에 데이터 추가
            foodListItem=new FoodListItem(foodName.getText().toString(),category.getText().toString(),expire.getText().toString(),memo.getText().toString());
            MainFoodListActivity.foodListItems.set(position,foodListItem);

            // 저장 버튼 사라지기
            save.setVisibility(View.GONE);
            edit.setClickable(true);
            delete.setVisibility(View.VISIBLE);
        });

        delete.setOnClickListener(v->{
            // 데이터 삭제
            MainFoodListActivity.foodListItems.remove(position);

            // 액티비티 종료
            finish();
        });



    }

}