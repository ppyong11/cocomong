package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.BarcodeResDto;
import com.sw.cocomong.task.BarcodeTask;
import com.sw.cocomong.dto.FoodListItemDto;

import java.util.List;

public class FoodAddActivity extends AppCompatActivity {
    TextView title, category;
    ImageButton back, edit;
    Button save, delete;
    Button barcode, BtnCategory;
    TextView tv_barcodeNum;
    EditText foodName,  expire, memo;
    FoodListItemDto foodListItemDto;
    String categoryName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        tv_barcodeNum =findViewById(R.id.tv_barcodeNum);
        barcode= findViewById(R.id.btn_barcode);
        barcode.setOnClickListener(v->{
            String barcodeNum=tv_barcodeNum.getText().toString();
            BarcodeTask barcodeTask = new BarcodeTask(barcodeNum);
            try {
                BarcodeResDto result = barcodeTask.execute(barcodeNum).get();
                //Log.d("Barcode" , result);

                if (barcodeTask.getResponseCode() == 200) {
                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        title=findViewById(R.id.tv_infoTitle);
        back=findViewById(R.id.btn_back);
        edit=findViewById(R.id.btn_edit);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        foodName =findViewById(R.id.et_infoFoodName);

        category=findViewById(R.id.tv_category);
        BtnCategory=findViewById(R.id.btn_infoCategory);

        expire=findViewById(R.id.et_infoExpire);
        memo=findViewById(R.id.et_memo);

        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        foodName.setEnabled(true);
        BtnCategory.setEnabled(true);
        expire.setEnabled(true);
        memo.setEnabled(true);

        back.setOnClickListener(v->{
            finish();
        });

        save.setOnClickListener(v->{
            //Toast.makeText(getApplicationContext(),"save 눌림",Toast.LENGTH_SHORT).show();

            foodName.setEnabled(false);
            BtnCategory.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            foodListItemDto =new FoodListItemDto(foodName.getText().toString(),category.getText().toString(),expire.getText().toString(),memo.getText().toString());
            FoodListItemDto.getFoodListItems().add(foodListItemDto);

            save.setVisibility(View.GONE);
            finish();
        });

        BtnCategory.setOnClickListener(v->{
            Intent intentCategory = new Intent(FoodAddActivity.this, CategorySelectActivity.class);
            startActivityForResult(intentCategory,1212);
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


}
