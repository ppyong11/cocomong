package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.FoodListItemDto;

public class FoodInfoActivity extends AppCompatActivity {
    TextView title, category;
    ImageButton back, edit;
    Button save, delete, BtnCategory;
    EditText foodName, expire, memo;
    FoodListItemDto foodListItemDto;
    ImageView foodImage;
    Bitmap foodImageBitmap;
    int foodPosition;
    int refPosition;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        foodPosition=extras.getInt("foodPosition");
        refPosition=extras.getInt("refPostition");
        foodListItemDto=FoodListItemDto.getFoodListItems().get(foodPosition);

        foodImage=findViewById(R.id.food_image);
        title=findViewById(R.id.tv_infoTitle);
        back=findViewById(R.id.btn_back);
        edit=findViewById(R.id.btn_edit);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        foodName =findViewById(R.id.et_infoFoodName);
        BtnCategory =findViewById(R.id.btn_infoCategory);
        category=findViewById(R.id.tv_category);
        expire=findViewById(R.id.et_infoExpire);
        memo=findViewById(R.id.et_memo);

        save.setVisibility(View.GONE);
        delete.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);

        foodName.setEnabled(false);
        BtnCategory.setEnabled(false);
        expire.setEnabled(false);
        memo.setEnabled(false);

        foodImageBitmap= foodListItemDto.getFoodImage();
        foodImage.setImageBitmap(foodImageBitmap);
        title.setText(foodListItemDto.getFoodname());
        foodName.setText(foodListItemDto.getFoodname());
        category.setText(foodListItemDto.getCategory());
        expire.setText(foodListItemDto.getExpire());
        memo.setText(foodListItemDto.getMemo());

        back.setOnClickListener(v->{
            finish();
        });

        edit.setOnClickListener(v->{
            save.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

            foodName.setEnabled(true);
            BtnCategory.setEnabled(true);
            expire.setEnabled(true);
            memo.setEnabled(true);
        });

        save.setOnClickListener(v->{

            // 변경불가
            foodName.setEnabled(false);
            BtnCategory.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            // foodListItems에 데이터 추가
            foodListItemDto =new FoodListItemDto(foodImageBitmap,foodName.getText().toString(), refPosition, category.getText().toString(),expire.getText().toString(),memo.getText().toString());
            FoodListItemDto.getFoodListItems().set(foodPosition, foodListItemDto);

            // 저장 버튼 사라지기
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

            finish();
        });

        delete.setOnClickListener(v->{
            // 데이터 삭제
            FoodListItemDto.getFoodListItems().remove(foodPosition);

            // 액티비티 종료
            finish();
        });

    }

}