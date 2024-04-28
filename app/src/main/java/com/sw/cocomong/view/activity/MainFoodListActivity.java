package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.view.adapter.FoodAdapter;
import com.sw.cocomong.view.item.FoodListItem;

import java.util.ArrayList;
import java.util.List;

public class MainFoodListActivity extends AppCompatActivity {

    ListView list;
    Button foodAdd, favorite;

    FoodAdapter foodAdapter;
    // 리스트.
    FoodListItem onion = new FoodListItem("양파", "채소", "24-4-13");
    FoodListItem garlic = new FoodListItem("마늘", "채소", "24-5-1");

    public static List<FoodListItem> foodListItems=new ArrayList<>();
    public static List<FoodListItem> favoriteItems=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        list = findViewById(R.id.list_food);
        foodAdd = findViewById(R.id.btn_foodAdd);
        favorite = findViewById(R.id.btn_listFavorite);


        // 데이터 추가
        foodListItems.add(onion);
        foodListItems.add(garlic);


        foodAdapter = new FoodAdapter(MainFoodListActivity.this, foodListItems);
        list.setAdapter(foodAdapter);



        foodAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainFoodListActivity.this, FoodAddActivity.class);
            startActivity(intent);
        });

        favorite.setOnClickListener(v->{
            Intent intent = new Intent(MainFoodListActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });

        // TODO: 2024-04-14 리스트의 항목별 클릭 안되는 오류 잡기.
        list.setOnItemClickListener((parent, view, position, id) -> {
            FoodListItem foodListItem=foodListItems.get(position);
            Toast.makeText(MainFoodListActivity.this,foodListItem.getName(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainFoodListActivity.this, FoodInfoActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        foodAdapter.notifyDataSetChanged();
    }
}