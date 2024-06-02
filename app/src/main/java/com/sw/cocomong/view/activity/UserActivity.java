package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.dto.FoodListItemDto;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.foodtask.FoodListGetTask;
import com.sw.cocomong.view.adapter.FoodAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ListView list;
    TextView refName;
    Button refridge, foodAdd, favorite, recipe, sort, category;
    FoodAdapter foodAdapter, favAdapter, categoryAdapter;
    RefListItemDto refListItemDto;
    int foodPosition, refPosition;
    public List<FoodListItemDto> foodListItemDtos,favoriteList, categoryList;
    boolean isFavorite=false;
    boolean isCategory=false;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refPosition=extras.getInt("refPosition");  // 냉장고 위치 받아옴
        username=extras.getString("username");  // username 받아옴
        try {
            FoodListGetTask listGetTask = new FoodListGetTask(username, refPosition);  // foodlist 받아옴

        } catch (Exception e) {
            e.printStackTrace();
        }

        //refListItemDto= RefFoodMap.getRefListItemDtos().get(refPosition);
       // foodListItemDtos= RefFoodMap.getRefFoodMap().get(refListItemDto);
        //favoriteList = RefFoodMap.getRefFavMap().get(refListItemDto);

        refName=findViewById(R.id.ref_name);
        refName.setText(refListItemDto.getName());

        list = findViewById(R.id.list_food);
        foodAdd = findViewById(R.id.btn_foodAdd);
        favorite = findViewById(R.id.btn_listFavorite);
        recipe = findViewById(R.id.btn_mypage);
        refridge=findViewById(R.id.btn_refback);
        sort=findViewById(R.id.btn_sort);
        category=findViewById(R.id.btn_list_category);

        foodAdapter = new FoodAdapter(UserActivity.this, foodListItemDtos, refListItemDto);
        favAdapter = new FoodAdapter(UserActivity.this, favoriteList,refListItemDto);
        list.setAdapter(foodAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            foodPosition=position;
            Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
            foodIntent.putExtra("foodPosition",foodPosition);
            foodIntent.putExtra("refPosition",refPosition);
            startActivity(foodIntent);
        });

        foodAdd.setOnClickListener(v -> {
            list.setAdapter(foodAdapter);
            Intent foodAddIntent = new Intent(UserActivity.this, FoodAddSelectActivity.class);
            foodAddIntent.putExtra("refPosition", refPosition);
            startActivity(foodAddIntent);
        });


        favorite.setOnClickListener(v->{
            if(!isFavorite) {
                isFavorite = true;
                list.setAdapter(favAdapter);
                favorite.setText("돌아가기");
                favorite.setBackgroundColor(getResources().getColor(R.color.purple_200));
                favorite.setTextColor(getResources().getColor(R.color.white));
            }else {
                isFavorite=false;
                list.setAdapter(foodAdapter);
                favorite.setText("즐겨찾기");
                favorite.setBackgroundColor(getResources().getColor(R.color.purple_100));
                favorite.setTextColor(getResources().getColor(R.color.black));

            }
        });

        category.setOnClickListener(v->{
            if(!isCategory){

                Intent categoryIntent = new Intent(UserActivity.this, CategorySelectActivity.class);
                startActivityForResult(categoryIntent,1300);
            }else{
                isCategory=false;
                list.setAdapter(foodAdapter);
                category.setText("카테고리");
                category.setBackgroundColor(getResources().getColor(R.color.purple_100));
                category.setTextColor(getResources().getColor(R.color.black));
            }
        });

        recipe.setOnClickListener(v->{
            list.setAdapter(foodAdapter);
            Intent mypageIntent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(mypageIntent);
        });

        refridge.setOnClickListener(v->{
            Intent refIntent = new Intent(UserActivity.this, RefridgeActivity.class);
            startActivity(refIntent);
        });

        sort.setOnClickListener(v->{
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.sort_name){
                    Collections.sort(foodListItemDtos, Comparator.comparing(FoodListItemDto::getFoodname));
                    list.setAdapter(foodAdapter);
                } else if (p.getItemId()==R.id.sort_expire) {
                    Toast.makeText(this, "유통기한 정렬", Toast.LENGTH_SHORT).show();
                    Collections.sort(foodListItemDtos, Comparator.comparing(FoodListItemDto::getExpire));
                    list.setAdapter(foodAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1300&&resultCode==RESULT_OK){
            if(data!=null){
                String selectedCategory = data.getStringExtra("category");
                categoryList=new ArrayList<>();
                for(FoodListItemDto foodListItemDto : foodListItemDtos){
                    if(foodListItemDto.getCategory().equals(selectedCategory)) categoryList.add(foodListItemDto);
                }
                isCategory=true;
                categoryAdapter = new FoodAdapter(UserActivity.this, categoryList, refListItemDto);
                list.setAdapter(categoryAdapter);
                category.setText(selectedCategory);
                category.setBackgroundColor(getResources().getColor(R.color.purple_200));
                category.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

}