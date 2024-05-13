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
import com.sw.cocomong.dto.FoodListItemDto;
import com.sw.cocomong.view.activity.CameraCapture;

public class FoodAddActivity extends AppCompatActivity {

    ImageView foodimage;
    TextView title, category;
    ImageButton back, edit;
    Button save, delete;
    EditText foodName, expire, memo;
    FoodListItemDto foodListItemDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        title=findViewById(R.id.tv_infoTitle);
        back=findViewById(R.id.btn_back);
        edit=findViewById(R.id.btn_edit);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        foodName =findViewById(R.id.et_infoFoodName);
        category=findViewById(R.id.tv_category);
        expire=findViewById(R.id.et_infoExpire);
        memo=findViewById(R.id.et_memo);

        foodimage=findViewById(R.id.food_image);


        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        foodName.setEnabled(true);
        category.setEnabled(true);
        expire.setEnabled(true);
        memo.setEnabled(true);

        foodimage.setImageBitmap(CameraCapture.moveBitmap());

        back.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(), UserActivity.foodListItemDtos.get(2).getName(),Toast.LENGTH_SHORT).show();
            finish();

        });

        save.setOnClickListener(v->{
            //Toast.makeText(getApplicationContext(),"save 눌림",Toast.LENGTH_SHORT).show();

            foodName.setEnabled(false);
            category.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);


            foodListItemDto=new FoodListItemDto(foodName.getText().toString(),category.getText().toString(),expire.getText().toString(),memo.getText().toString());
            UserActivity.foodListItemDtos.add(foodListItemDto);

            save.setVisibility(View.GONE);
            finish();
        });

    }

}
