package com.sw.cocomong.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.Object.RecipeObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.BackgroundService;
import com.sw.cocomong.task.NotificationService;
import com.sw.cocomong.task.foodtask.FoodListGetTask;
import com.sw.cocomong.task.foodtask.RecipeListGetTask;
import com.sw.cocomong.view.adapter.FoodAdapter;
import com.sw.cocomong.view.adapter.RecipeAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecipeListGetTask.RecipeListGetTaskListener {

    ListView list;
    TextView refName_tv;
    Button refridge, foodAdd, favorite, recipe, sort, category;
    RecipeAdapter recipeAdapter;
    List<RecipeObj> recipeObjs = new ArrayList<>();
    private static final int REQUEST_SYSTEM_ALERT_WINDOW = 123;

    String username,refname,refnum;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refname=extras.getString("refname");  // 냉장고 위치 받아옴
        username=extras.getString("username");  // username 받아옴

        try {
            new RecipeListGetTask(username, refname, this);  // foodlist 요청
            recipeObjs.forEach(recipeObj -> {
                if(recipeObj.getRecipe().equals("true")) recipeObjs.add(recipeObj);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        refName_tv=findViewById(R.id.ref_name);
        refName_tv.setText(refname);

        list = findViewById(R.id.list_recipe);
        foodAdd = findViewById(R.id.btn_foodAdd);
        favorite = findViewById(R.id.btn_listFavorite);
        recipe = findViewById(R.id.btn_recipe);
        refridge=findViewById(R.id.btn_refback);
        sort=findViewById(R.id.btn_sort);
        category=findViewById(R.id.btn_list_category);

        recipeAdapter = new RecipeAdapter(RecipeActivity.this, recipeObjs);
        ListAdapter RecipeAdapter;
        list.setAdapter(recipeAdapter);

        foodAdd.setVisibility(View.INVISIBLE);
        favorite.setVisibility(View.INVISIBLE);
        category.setVisibility(View.INVISIBLE);
        refridge.setVisibility(View.INVISIBLE);

        recipe.setOnClickListener(v->{
            Intent categoryIntent = new Intent(RecipeActivity.this, UserActivity.class);
            startActivity(categoryIntent);
        });

        sort.setOnClickListener(v->{
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.recipe_sort_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.sort_Fav){
                    Collections.sort(recipeObjs, Comparator.comparing(RecipeObj::getRecipe));
                    list.setAdapter(recipeAdapter);
                } else if (p.getItemId()==R.id.sort_exp) {
                    Toast.makeText(this, "유통기한 정렬", Toast.LENGTH_SHORT).show();
                    Collections.sort(recipeObjs, Comparator.comparing(RecipeObj::getRecipe));
                    list.setAdapter(recipeAdapter);
                }
                return false;
            });
            sortMenu.show();
        });
    }



    @Override
    public void onRecipeListReceived(List<RecipeObj> recipeObjs) {

    }
}