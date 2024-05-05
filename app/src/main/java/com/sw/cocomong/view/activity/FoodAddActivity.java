package com.sw.cocomong.view.activity;

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
import com.sw.cocomong.task.BarcodeTask;
import com.sw.cocomong.dto.FoodListItemDto;

public class FoodAddActivity extends AppCompatActivity {
    TextView title;
    ImageButton back, edit;
    Button save, delete;
    Button barcode;
    TextView tv_barcodeNum;
    EditText foodName, category, expire, memo;
    FoodListItemDto foodListItemDto;
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
                String result = barcodeTask.execute(barcodeNum).get();
                Log.d("Barcode" , result);

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
        category=findViewById(R.id.et_infoCategory);
        expire=findViewById(R.id.et_infoExpire);
        memo=findViewById(R.id.et_memo);

        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        foodName.setEnabled(true);
        category.setEnabled(true);
        expire.setEnabled(true);
        memo.setEnabled(true);

        back.setOnClickListener(v->{
            finish();
        });

        save.setOnClickListener(v->{
            //Toast.makeText(getApplicationContext(),"save 눌림",Toast.LENGTH_SHORT).show();

            foodName.setEnabled(false);
            category.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            foodListItemDto =new FoodListItemDto(foodName.getText().toString(),category.getText().toString(),expire.getText().toString(),memo.getText().toString());
            FoodListItemDto.getFoodListItems().add(foodListItemDto);

            save.setVisibility(View.GONE);
            finish();
        });
    }

}
