package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.BarcodeResDto;
import com.sw.cocomong.dto.FoodListItemDto;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.BarcodeTask;
import com.sw.cocomong.view.activity.CameraCapture;

import java.sql.Time;
import java.util.Date;

// 사진을 통해서 음식을 추가함.
public class FoodAddActivity extends AppCompatActivity {

    ImageView foodimage;
    TextView title, category, barcode, barcodeTest;
    ImageButton back, edit;
    Button save, delete, btnCategory;
    EditText foodName, expire, memo;
    FoodListItemDto foodListItemDto;
    RefListItemDto refListItemDto;
    Bitmap foodImageBitmap;
    int foodPosition, refPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refPosition=extras.getInt("refPosition");
        refListItemDto= RefFoodMap.getRefListItemDtos().get(refPosition);

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
        barcodeTest=findViewById(R.id.btn_barcode);
        barcode=findViewById(R.id.tv_barcodeNum);

        foodimage = findViewById(R.id.food_image);

        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        foodName.setEnabled(true);
        btnCategory.setEnabled(true);
        expire.setEnabled(true);
        memo.setEnabled(true);

        foodImageBitmap=CameraCapture.moveBitmap();
        foodimage.setImageBitmap(foodImageBitmap);

        btnCategory.setOnClickListener(v->{
            Intent intentCategory = new Intent(FoodAddActivity.this, CategorySelectActivity.class);
            // FoodAddTask 실행?
            startActivityForResult(intentCategory,1212);
        });

        back.setOnClickListener(v -> {
            finish();
        });

        save.setOnClickListener(v -> {

            foodName.setEnabled(false);
            category.setEnabled(false);
            btnCategory.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            foodListItemDto = new FoodListItemDto(foodImageBitmap,foodName.getText().toString(), category.getText().toString(), expire.getText().toString(), memo.getText().toString());

            RefFoodMap.getFoodListItemDtos(refListItemDto).add(foodListItemDto);

            save.setVisibility(View.GONE);
            finish();
        });

        barcodeTest.setOnClickListener(v->{
            String barcodeNum=barcode.getText().toString();
            BarcodeTask barcodeTask = new BarcodeTask(barcodeNum);
            try {
                BarcodeResDto result = barcodeTask.execute(barcodeNum).get();

                foodName.setText(result.getProductName());
                category.setText(result.getCategory());
                category.setText(result.getCategory());
                memo.setText(result.getDayCount());

                if (barcodeTask.getResponseCode() == 200| result==null) {
                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();

            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1212&&resultCode==RESULT_OK){
            if(data!=null){
                String selectedCategory = data.getStringExtra("category");
                category.findViewById(R.id.tv_category);
                category.setText(selectedCategory);
            }
        }
    }

    // TODO: 2024-05-13 바코드 인식 후 카테고리 설정해주는 로직
    public String setCategory(String categoryName){

        return "";
    }
}
