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

import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.foodtask.FoodEditTask;
import com.sw.cocomong.task.foodtask.FoodListGetTask;
import com.sw.cocomong.view.adapter.FoodAdapter;

import org.json.JSONException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ListView list;
    TextView refName_tv;
    Button refridge, foodAdd, favorite, recipe, sort, category;
    FoodAdapter foodAdapter, categoryAdapter,favAdapter;
    //RefListItemDto refListItemDto;
    //int foodPosition, refPosition;
    //public List<FoodListItemDto> foodListItemDtos,favoriteList, categoryList;
    // List<FoodObj> foodList=new ArrayList<>();
    List<FoodResObj> foodResObjs = new ArrayList<>();
    List<FoodResObj> favoriteList=new ArrayList<>();
    List<FoodResObj> categoryList;

    boolean isFavorite=false;
    boolean isCategory=false;
    String username,refname,refnum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        //refname=extras.getString("refname");  // 냉장고 위치 받아옴
        // username=extras.getString("username");  // username 받아옴
        // refnum=extras.getString("refnum");
        try {
            FoodListGetTask listGetTask = new FoodListGetTask("dahee", "냉장고1");  // foodlist 받아옴
            foodResObjs=listGetTask.getList();
            foodResObjs.forEach(foodResObj -> {
                if(foodResObj.getFavorite().equals("true")) favoriteList.add(foodResObj);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        refName_tv=findViewById(R.id.ref_name);
        refName_tv.setText(refname);

        list = findViewById(R.id.list_food);
        foodAdd = findViewById(R.id.btn_foodAdd);
        favorite = findViewById(R.id.btn_listFavorite);
        recipe = findViewById(R.id.btn_mypage);
        refridge=findViewById(R.id.btn_refback);
        sort=findViewById(R.id.btn_sort);
        category=findViewById(R.id.btn_list_category);

        //foodAdapter = new FoodAdapter(UserActivity.this, foodListItemDtos, refListItemDto);
        foodAdapter = new FoodAdapter(UserActivity.this, foodResObjs);
        favAdapter = new FoodAdapter(UserActivity.this, favoriteList);
        list.setAdapter(foodAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String foodname = foodResObjs.get(position).getFoodname();
            String foodid = foodResObjs.get(position).getFoodid();

            Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
            foodIntent.putExtra("username",username);
            foodIntent.putExtra("foodname", foodname);
            foodIntent.putExtra("refname",refname);
            foodIntent.putExtra("foodid",foodid);
            foodIntent.putExtra("refnum",refnum);
            startActivity(foodIntent);
        });

        foodAdd.setOnClickListener(v -> {
            list.setAdapter(foodAdapter);
            Intent foodAddIntent = new Intent(UserActivity.this, FoodAddSelectActivity.class);
            foodAddIntent.putExtra("username",username);
            foodAddIntent.putExtra("refname",refname);
            foodAddIntent.putExtra("refnum",refnum);
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
            refIntent.putExtra("username",username);
            startActivity(refIntent);
        });

        sort.setOnClickListener(v->{
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.sort_name){
                    Collections.sort(foodResObjs, Comparator.comparing(FoodResObj::getFoodname));
                    list.setAdapter(foodAdapter);
                } else if (p.getItemId()==R.id.sort_expire) {
                    Toast.makeText(this, "유통기한 정렬", Toast.LENGTH_SHORT).show();
                    Collections.sort(foodResObjs, Comparator.comparing(FoodResObj::getExpiredate));
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
                for(FoodResObj foodResObj : foodResObjs){
                    if(foodResObj.getCategory().equals(selectedCategory)) categoryList.add(foodResObj);
                }
                isCategory=true;
                categoryAdapter = new FoodAdapter(UserActivity.this, categoryList);
                list.setAdapter(categoryAdapter);
                category.setText(selectedCategory);
                category.setBackgroundColor(getResources().getColor(R.color.purple_200));
                category.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

}