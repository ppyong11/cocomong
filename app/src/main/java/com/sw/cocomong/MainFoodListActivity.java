package com.sw.cocomong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.fooditem.FoodAdapter;
import com.sw.cocomong.fooditem.FoodListItem;

import java.util.ArrayList;
import java.util.List;

public class MainFoodListActivity extends AppCompatActivity {
    ListView list;
    Button foodAdd;

    // 리스트.
    FoodListItem onion = new FoodListItem("양파", "채소", "24-4-13", true);
    FoodListItem garlic = new FoodListItem("마늘", "채소", "24-5-1", false);

    public static List<FoodListItem> foodListItems=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        list = findViewById(R.id.list_food);
        foodAdd = findViewById(R.id.btn_foodAdd);

        // 데이터 추가
        foodListItems.add(onion);
        foodListItems.add(garlic);

        FoodAdapter foodAdapter = new FoodAdapter(MainFoodListActivity.this, foodListItems);
        list.setAdapter(foodAdapter);

        foodAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainFoodListActivity.this, FoodAddActivity.class);
            startActivity(intent);
        });

        // 안됨.
        list.setOnItemClickListener((parent, view, position, id) -> {
            FoodListItem foodListItem=foodListItems.get(position);
            Toast.makeText(MainFoodListActivity.this,foodListItem.getName(),Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(MainFoodListActivity.this, FoodInfoActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);*/
        });

    }
}