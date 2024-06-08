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

import com.sw.cocomong.Object.FoodExpObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.Object.RecipeObj;
import com.sw.cocomong.R;
import com.sw.cocomong.task.BackgroundService;
import com.sw.cocomong.task.NotificationService;
import com.sw.cocomong.task.foodtask.FoodListGetTask;
import com.sw.cocomong.task.foodtask.RecipeListGetTask;
import com.sw.cocomong.task.reftask.RefListGetTask;
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
    List<RecipeObj> recipeList = new ArrayList<>();
    String username,refname,refnum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        refname = extras.getString("refname");  // 냉장고 위치 받아옴
        username = extras.getString("username");  // username 받아옴

        try {
            new RecipeListGetTask(username, refname,this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        refName_tv = findViewById(R.id.ref_name);
        refName_tv.setText(refname);

        list = findViewById(R.id.list_recipe);
        foodAdd = findViewById(R.id.btn_foodAdd);
        favorite = findViewById(R.id.btn_listFavorite);
        recipe = findViewById(R.id.btn_recipe);
        refridge = findViewById(R.id.btn_refback);
        sort = findViewById(R.id.btn_recsort);
        category = findViewById(R.id.btn_list_category);


        recipeAdapter = new RecipeAdapter(RecipeActivity.this, recipeObjs);
        list.setAdapter(recipeAdapter);


        //Log.d("recipeActivity",recipeObjs.get(0).toString());

        foodAdd.setVisibility(View.INVISIBLE);
        favorite.setVisibility(View.INVISIBLE);
        category.setVisibility(View.INVISIBLE);
        refridge.setVisibility(View.INVISIBLE);

        recipe.setOnClickListener(v->{
            Intent Intent = new Intent(RecipeActivity.this, UserActivity.class);
            Intent.putExtra("username",username);
            Intent.putExtra("refname",refname);
            startActivity(Intent);

        });



        sort.setOnClickListener(v->{
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.recipe_sort_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.sort_Fav){
                    Collections.sort(recipeObjs, Comparator.comparing(RecipeObj::getRecipe));
                    list.setAdapter(recipeAdapter);
                } else if (p.getItemId()==R.id.sort_exp) {
                    Collections.sort(recipeObjs, Comparator.comparing(RecipeObj::getRecipe));
                    list.setAdapter(recipeAdapter);
                }
                return false;
            });
            sortMenu.show();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            new RecipeListGetTask(username, refname,this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeListReceived(List<RecipeObj> recipeObjs) {
        this.recipeObjs = recipeObjs;
    }

}

