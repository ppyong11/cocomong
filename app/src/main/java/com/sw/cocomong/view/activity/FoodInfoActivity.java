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
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;

// foodList를 통해서 들어와서 수정
public class FoodInfoActivity extends AppCompatActivity {
    TextView title, category;
    ImageButton back, edit;
    Button save, delete, btnCategory;
    EditText foodName, expire, memo;
    FoodListItemDto foodListItemDto;
    RefListItemDto refListItemDto;
    ImageView foodImage;
    Bitmap foodImageBitmap;
    int foodPosition, refPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        foodPosition = extras.getInt("foodPosition");
        refPosition = extras.getInt("refPostition");
        refListItemDto=RefFoodMap.getRefListItemDtos().get(refPosition);

        foodListItemDto = RefFoodMap.getFoodListItemDtos(refListItemDto).get(foodPosition);

        foodImage = findViewById(R.id.food_image);
        title = findViewById(R.id.tv_infoTitle);
        back = findViewById(R.id.btn_back);
        edit = findViewById(R.id.btn_edit);
        save = findViewById(R.id.btn_save);
        delete = findViewById(R.id.btn_delete);
        foodName = findViewById(R.id.et_infoFoodName);
        btnCategory = findViewById(R.id.btn_infoCategory);
        category = findViewById(R.id.tv_category);
        expire = findViewById(R.id.et_infoExpire);
        memo = findViewById(R.id.et_memo);

        save.setVisibility(View.GONE);
        delete.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);

        foodName.setEnabled(false);
        btnCategory.setEnabled(false);
        expire.setEnabled(false);
        memo.setEnabled(false);

        foodImageBitmap = foodListItemDto.getFoodImage();
        foodImage.setImageBitmap(foodImageBitmap);
        title.setText(foodListItemDto.getFoodname());
        foodName.setText(foodListItemDto.getFoodname());
        category.setText(foodListItemDto.getCategory());
        expire.setText(foodListItemDto.getExpire());
        memo.setText(foodListItemDto.getMemo());

        back.setOnClickListener(v -> {
            finish();
        });

        edit.setOnClickListener(v -> {
            save.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

            foodName.setEnabled(true);
            btnCategory.setEnabled(true);
            expire.setEnabled(true);
            memo.setEnabled(true);
        });

        btnCategory.setOnClickListener(v -> {
            Intent intentCategory = new Intent(FoodInfoActivity.this, CategorySelectActivity.class);
            // FoodAddTask 실행?
            startActivityForResult(intentCategory, 1212);
        });

        save.setOnClickListener(v -> {

            // 변경불가
            foodName.setEnabled(false);
            btnCategory.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            // foodListItems에 데이터 추가
            foodListItemDto = new FoodListItemDto(foodImageBitmap, foodName.getText().toString(), category.getText().toString(), expire.getText().toString(), memo.getText().toString(), false, refListItemDto.getRefId());
            RefFoodMap.getFoodListItemDtos(refListItemDto).set(foodPosition, foodListItemDto);

            // 저장 버튼 사라지기
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

            finish();
        });

        delete.setOnClickListener(v -> {
            // 데이터 삭제
            RefFoodMap.getFoodListItemDtos(refListItemDto).remove(foodPosition);

            // 액티비티 종료
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1212 && resultCode == RESULT_OK) {
            if (intent != null) {
                String selectedCategory = intent.getStringExtra("category");
                category.findViewById(R.id.tv_category);
                category.setText(selectedCategory);
            }
        }
    }
}