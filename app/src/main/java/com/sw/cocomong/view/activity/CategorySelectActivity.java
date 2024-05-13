package com.sw.cocomong.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sw.cocomong.R;

public class CategorySelectActivity extends Activity {

    Button fruit, vegetable, dairy, meat, seafood, processedFood, beverage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_popup);


        fruit=findViewById(R.id.btn_fruit);
        vegetable=findViewById(R.id.btn_vegetable);
        dairy=findViewById(R.id.btn_Dairy);
        meat=findViewById(R.id.btn_Meat);
        seafood=findViewById(R.id.btn_Seafood);
        processedFood=findViewById(R.id.btn_ProcessedFood);
        beverage=findViewById(R.id.btn_Beverage);

        fruit.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","과일");
            setResult(RESULT_OK,resultIntent);
            finish();
        });
        vegetable.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","채소");
            setResult(RESULT_OK,resultIntent);
            finish();
        });

        dairy.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","유제품");
            setResult(RESULT_OK,resultIntent);
            finish();
        });
        meat.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","육류 및 가공 육류");
            setResult(RESULT_OK,resultIntent);
            finish();
        });
        seafood.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","해산물");
            setResult(RESULT_OK,resultIntent);
            finish();
        });
        processedFood.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","가공식품");
            setResult(RESULT_OK,resultIntent);
            finish();
        });
        beverage.setOnClickListener(v->{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("category","음료수");
            setResult(RESULT_OK,resultIntent);
            finish();
        });


    }
}
