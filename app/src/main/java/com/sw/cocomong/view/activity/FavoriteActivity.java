/*
package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.view.adapter.FoodAdapter;
import com.sw.cocomong.dto.FoodListItemDto;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    FoodAdapter foodAdapter;
    ListView list;

    RefListItemDto refListItemDto;
    int refPosition;
    public static List<FoodListItemDto> favoriteList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refPosition=extras.getInt("refPosition");
        refListItemDto= RefFoodMap.getRefListItemDtos().get(refPosition);

        favoriteList = RefFoodMap.getRefFavMap().get(refListItemDto);

        list=findViewById(R.id.list_food);
        //foodAdapter = new FoodAdapter(FavoriteActivity.this, favoriteList,refListItemDto);
        foodAdapter = new FoodAdapter(FavoriteActivity.this, favoriteList);
        list.setAdapter(foodAdapter);

    }
}
*/
