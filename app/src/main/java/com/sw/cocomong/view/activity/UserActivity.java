package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.FoodListItemDto;
import com.sw.cocomong.view.adapter.FoodAdapter;
import com.sw.cocomong.view.activity.FoodAddActivity;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    ListView list;
    Button refridge, foodAdd, favorite, mypage, sort;
    FoodAdapter foodAdapter;

    FoodListItemDto foodListItem;

    Bitmap imageBitap;
    // 리스트.
    //FoodListItem onion = new FoodListItem("양파", "채소", "24-4-13");
    //FoodListItem garlic = new FoodListItem("마늘", "채소", "24-5-1");

    public static List<FoodListItemDto> foodListItemDtos = FoodListItemDto.getFoodListItems();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        list = findViewById(R.id.list_food);
        foodAdd = findViewById(R.id.btn_foodAdd);
        favorite = findViewById(R.id.btn_listFavorite);
        mypage = findViewById(R.id.btn_mypage);
        refridge=findViewById(R.id.btn_refback);
        sort=findViewById(R.id.btn_sort);


        // 데이터 추가
        //foodListItems.add(onion);
        // foodListItems.add(garlic);

        foodAdapter = new FoodAdapter(UserActivity.this, foodListItemDtos);
        list.setAdapter(foodAdapter);


        // TODO: 2024-04-14 리스트의 항목별 클릭 안되는 오류 잡기.
        list.setOnItemClickListener((parent, view, position, id) -> {

            foodListItem = foodListItemDtos.get(position);
            Toast.makeText(UserActivity.this, "음식 선택",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserActivity.this, FoodInfoActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
        });

        foodAdd.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, FoodAddSelectActivity.class);
            startActivity(intent);
        });

        favorite.setOnClickListener(v->{
            //foodAdapter = new FoodAdapter(MainFoodListActivity.this, FoodListItem.getFavoriteItems());
            //list.setAdapter(foodAdapter);
            Intent intent = new Intent(UserActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });

        mypage.setOnClickListener(v->{
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        refridge.setOnClickListener(v->{
            Intent intent = new Intent(UserActivity.this, RefridgeActivity.class);
            startActivity(intent);
        });

        sort.setOnClickListener(v->{
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.sort_name){
                    Toast.makeText(this, "이름순 정렬", Toast.LENGTH_SHORT).show();
                } else if (p.getItemId()==R.id.sort_expire) {
                    Toast.makeText(this, "유통기한 정렬", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
            sortMenu.show();
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        foodAdapter.notifyDataSetChanged();
    }

    /*public static class MainFoodListActivity extends AppCompatActivity {

        ListView list;
        Button refridge, foodAdd, favorite, mypage, sort;
        FoodAdapter foodAdapter;

        Bitmap imageBitap;
        // 리스트.
        //FoodListItem onion = new FoodListItem("양파", "채소", "24-4-13");
        //FoodListItem garlic = new FoodListItem("마늘", "채소", "24-5-1");

        public static List<FoodListItemDto> foodListItemDtos = FoodListItemDto.getFoodListItems();



        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.food_list);

            list = findViewById(R.id.list_food);
            foodAdd = findViewById(R.id.btn_foodAdd);
            favorite = findViewById(R.id.btn_listFavorite);
            mypage = findViewById(R.id.btn_mypage);
            refridge=findViewById(R.id.btn_refback);
            sort=findViewById(R.id.btn_sort);


            // 데이터 추가
            //foodListItems.add(onion);
           // foodListItems.add(garlic);

            foodAdapter = new FoodAdapter(MainFoodListActivity.this, foodListItemDtos);
            list.setAdapter(foodAdapter);



            // TODO: 2024-04-14 리스트의 항목별 클릭 안되는 오류 잡기.
            list.setOnItemClickListener((parent, view, position, id) -> {

                FoodListItemDto foodListItem=foodListItemDtos.get(position);
                Toast.makeText(MainFoodListActivity.this, "음식 선택",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainFoodListActivity.this, FoodInfoActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            });

            foodAdd.setOnClickListener(v -> {
                Intent intent = new Intent(MainFoodListActivity.this, FoodAddSelectActivity.class);
                startActivity(intent);
            });

            favorite.setOnClickListener(v->{
                //foodAdapter = new FoodAdapter(MainFoodListActivity.this, FoodListItem.getFavoriteItems());
                //list.setAdapter(foodAdapter);
                Intent intent = new Intent(MainFoodListActivity.this, FavoriteActivity.class);
                startActivity(intent);
            });

            mypage.setOnClickListener(v->{
                Intent intent = new Intent(MainFoodListActivity.this, LoginActivity.class);
                startActivity(intent);
            });

            refridge.setOnClickListener(v->{
                Intent intent = new Intent(MainFoodListActivity.this, RefridgeActivity.class);
                startActivity(intent);
            });

            sort.setOnClickListener(v->{
                final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
                getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
                sortMenu.setOnMenuItemClickListener(p->{
                    if(p.getItemId()==R.id.sort_name){
                        Toast.makeText(this, "이름순 정렬", Toast.LENGTH_SHORT).show();
                    } else if (p.getItemId()==R.id.sort_expire) {
                        Toast.makeText(this, "유통기한 정렬", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                });
                sortMenu.show();
            });



        }


        @Override
        protected void onResume() {
            super.onResume();
            foodAdapter.notifyDataSetChanged();
        }
    }*/



}
