package com.sw.cocomong.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.view.adapter.FoodAdapter;
import com.sw.cocomong.view.item.FoodListItem;

public class FavoriteActivity extends AppCompatActivity {
    FoodAdapter foodAdapter;
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        list=findViewById(R.id.list_food);
        foodAdapter = new FoodAdapter(FavoriteActivity.this, FoodListItem.getFavoriteItems());
        list.setAdapter(foodAdapter);

    }
}
