package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.Object.RecipeObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.Food;
import com.sw.cocomong.task.foodtask.FoodListGetTask;
import com.sw.cocomong.task.foodtask.RecipeListGetTask;
import com.sw.cocomong.view.adapter.FoodAdapter;
import com.sw.cocomong.view.adapter.RecipeAdapter;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UserActivity extends AppCompatActivity implements FoodListGetTask.FoodListGetTaskListener, RecipeListGetTask.RecipeListGetTaskListener {

    ListView list;
    TextView refName_tv;
    EditText search_et;
    ImageButton search_btn;
    Button refridge, foodAdd, favorite, recipe, sort, category;
    FoodAdapter foodAdapter, categoryAdapter,favAdapter,favCategoryAdapter, expCategoryAdapter,searchFoodAdapter;
    RecipeAdapter recipeAdapter, favRecipeAdapter, expRecipeAdapter,searchRecipeAdapter;
    List<FoodResObj> foodResObjs = new ArrayList<>();
    public static List<FoodResObj> favoriteList=new ArrayList<>();
    List<FoodResObj> categoryList=new ArrayList<>();
    List<RecipeObj> recipeObjs = new ArrayList<>();
    List<RecipeObj> favRecipeObjs = new ArrayList<>();
    List<FoodResObj> favCategoryList = new ArrayList<>();
    List<FoodResObj> expCategoryList = new ArrayList<>();
    List<RecipeObj> expRecipeList = new ArrayList<>();
    List<FoodResObj> searchFoodList=new ArrayList<>();
    List<RecipeObj> searchRecipeList=new ArrayList<>();

    boolean isFavorite=false;
    boolean isCategory=false;
    boolean isRecipe=false;
    boolean isFavRecipe=false;
    String username,refname,refnum;

    Calendar calendar = Calendar.getInstance();
    // 일자를 정수로 변환
    int currentDayInt = calendar.get(Calendar.DAY_OF_MONTH);

    //데이터 갱신용 count
    int count=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();


        refname=extras.getString("refname");  // 냉장고 위치 받아옴
        username=extras.getString("username");  // username 받아옴
        try {
            new FoodListGetTask(username, refname,this);
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
        recipe = findViewById(R.id.btn_recipe);
        refridge=findViewById(R.id.btn_refback);
        sort=findViewById(R.id.btn_sort);
        category=findViewById(R.id.btn_list_category);
        search_btn=findViewById(R.id.search_btn);
        search_et=findViewById(R.id.search_et);

        foodAdapter = new FoodAdapter(UserActivity.this, foodResObjs);
        favAdapter = new FoodAdapter(UserActivity.this, favoriteList);
        categoryAdapter = new FoodAdapter(UserActivity.this, categoryList);
        recipeAdapter = new RecipeAdapter(UserActivity.this, recipeObjs);
        favRecipeAdapter = new RecipeAdapter(this, favRecipeObjs);
        favCategoryAdapter = new FoodAdapter(this, favCategoryList);
        expCategoryAdapter=new FoodAdapter(this, expCategoryList);
        searchFoodAdapter=new FoodAdapter(this,searchFoodList);



        list.setAdapter(foodAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String foodname;
            String foodid;
            String filepath;
            if(isCategory){
                foodname=categoryList.get(position).getFoodname();
                foodid = categoryList.get(position).getIdx().toString();
                filepath= foodResObjs.get(position).getFilepath();
                Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
                foodIntent.putExtra("username",username);
                foodIntent.putExtra("foodname", foodname);
                foodIntent.putExtra("refname",refname);
                foodIntent.putExtra("foodid",foodid);
                foodIntent.putExtra("filepath", filepath);
                startActivity(foodIntent);
            }else if(isFavorite){
                foodname=favoriteList.get(position).getFoodname();
                foodid = favoriteList.get(position).getIdx().toString();
                filepath= foodResObjs.get(position).getFilepath();
                Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
                foodIntent.putExtra("username",username);
                foodIntent.putExtra("foodname", foodname);
                foodIntent.putExtra("refname",refname);
                foodIntent.putExtra("foodid",foodid);
                foodIntent.putExtra("filepath", filepath);
                startActivity(foodIntent);
            }else if(isRecipe) {
                if(list.getAdapter().equals(expRecipeAdapter)) {
                    String link = expRecipeList.get(position).getRecipeLink();
                    Intent recipeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(recipeIntent);
                } else if (list.getAdapter().equals(favAdapter)) {
                    String link = favRecipeObjs.get(position).getRecipeLink();
                    Intent recipeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(recipeIntent);
                }else {
                    String link = recipeObjs.get(position).getRecipeLink();
                    Intent recipeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(recipeIntent);
                }
            }else {
                if(list.getAdapter().equals(searchFoodAdapter)){
                    Log.d("현재 어댑터: ", list.getAdapter().getClass().getSimpleName());
                    foodname = searchFoodList.get(position).getFoodname();
                    foodid = searchFoodList.get(position).getIdx().toString();
                    filepath= searchFoodList.get(position).getFilepath();
                    Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
                    foodIntent.putExtra("username",username);
                    foodIntent.putExtra("foodname", foodname);
                    foodIntent.putExtra("refname",refname);
                    foodIntent.putExtra("foodid",foodid);
                    foodIntent.putExtra("filepath", filepath);
                    startActivity(foodIntent);
                }else {
//                    Log.d("항목 누름", "" + count);
//                    if (count == 1) {
//                        list.setAdapter(searchFoodAdapter);
//                        Log.d("현재 어댑터: ", list.getAdapter().getClass().getSimpleName());
//                    }
                    Log.d("현재 어댑터: ", list.getAdapter().getClass().getSimpleName());
                    foodname = foodResObjs.get(position).getFoodname();
                    foodid = foodResObjs.get(position).getIdx().toString();
                    filepath = foodResObjs.get(position).getFilepath();
                    Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
                    foodIntent.putExtra("username", username);
                    foodIntent.putExtra("foodname", foodname);
                    foodIntent.putExtra("refname", refname);
                    foodIntent.putExtra("foodid", foodid);
                    foodIntent.putExtra("filepath", filepath);
                    startActivity(foodIntent);
                }
            }
        });


        foodAdd.setOnClickListener(v -> {
            if(isCategory) {
                isCategory=false;
                list.setAdapter(foodAdapter);
                category.setText("카테고리");
                category.setBackgroundColor(getResources().getColor(R.color.purple_100));
                category.setTextColor(getResources().getColor(R.color.black));
            }else if(isFavorite){
                isFavorite=false;
                list.setAdapter(foodAdapter);
                favorite.setText("즐겨찾기");
                favorite.setBackgroundColor(getResources().getColor(R.color.purple_100));
                favorite.setTextColor(getResources().getColor(R.color.black));
            }else if(isRecipe){
                isRecipe=false;
                list.setAdapter(foodAdapter);
                recipe.setBackgroundColor(getResources().getColor(R.color.purple_100));
                recipe.setTextColor(getResources().getColor(R.color.black));
            }
            list.setAdapter(foodAdapter);
            Intent foodAddIntent = new Intent(UserActivity.this, FoodAddSelectActivity.class);
            foodAddIntent.putExtra("username",username);
            foodAddIntent.putExtra("refname",refname);
            foodAddIntent.putExtra("refnum",refnum);
            startActivity(foodAddIntent);
        });


        favorite.setOnClickListener(v->{
            favoriteList.clear();
            for(FoodResObj food : foodResObjs){
                if(food.getFavorite().equals("true"))
                    favoriteList.add(food);
            }
            if(!isFavorite) {
                if(isCategory) {
                    isCategory=false;
                    list.setAdapter(foodAdapter);
                    category.setText("카테고리");
                    category.setBackgroundColor(getResources().getColor(R.color.purple_100));
                    category.setTextColor(getResources().getColor(R.color.black));
                }else if (isRecipe){
                    isRecipe=false;
                    list.setAdapter(foodAdapter);
                    recipe.setBackgroundColor(getResources().getColor(R.color.purple_100));
                    recipe.setTextColor(getResources().getColor(R.color.black));
                }
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
            if(!isRecipe) {
                if(isCategory) {
                    isCategory=false;
                    list.setAdapter(foodAdapter);
                    category.setText("카테고리");
                    category.setBackgroundColor(getResources().getColor(R.color.purple_100));
                    category.setTextColor(getResources().getColor(R.color.black));
                }else if(isFavorite){
                    isFavorite=false;
                    list.setAdapter(foodAdapter);
                    favorite.setText("즐겨찾기");
                    favorite.setBackgroundColor(getResources().getColor(R.color.purple_100));
                    favorite.setTextColor(getResources().getColor(R.color.black));
                }
                isRecipe = true;
                list.setAdapter(recipeAdapter);
                recipe.setBackgroundColor(getResources().getColor(R.color.purple_200));
                recipe.setTextColor(getResources().getColor(R.color.white));
                new RecipeListGetTask(this);
            }else{
                isRecipe=false;
                list.setAdapter(foodAdapter);
                recipe.setBackgroundColor(getResources().getColor(R.color.purple_100));
                recipe.setTextColor(getResources().getColor(R.color.black));
            }
        });

        refridge.setOnClickListener(v->{
            Intent refIntent = new Intent(UserActivity.this, RefridgeActivity.class);
            refIntent.putExtra("username",username);
            startActivity(refIntent);
        });

        sort.setOnClickListener(v->{
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            if(isRecipe){
                getMenuInflater().inflate(R.menu.recipe_sort_menu, sortMenu.getMenu());
                sortMenu.setOnMenuItemClickListener(p->{
                    if(p.getItemId()==R.id.sort_name){
                        Collections.sort(recipeObjs, Comparator.comparing(RecipeObj::getRecipename));
                        list.setAdapter(recipeAdapter);
                    } else if (p.getItemId()==R.id.sort_expire) {
                        list.setAdapter(expRecipeAdapter);
                    }else {
                        list.setAdapter(favRecipeAdapter);
                    }
                    return false;
                });
                sortMenu.show();
            }else if(isCategory){
                getMenuInflater().inflate(R.menu.recipe_sort_menu, sortMenu.getMenu());
                sortMenu.setOnMenuItemClickListener(p->{
                    if(p.getItemId()==R.id.sort_name){
                        Collections.sort(categoryList, Comparator.comparing(FoodResObj::getFoodname));
                        list.setAdapter(categoryAdapter);
                    } else if (p.getItemId()==R.id.sort_expire) {
                        list.setAdapter(expCategoryAdapter);
                    }else {
                        list.setAdapter(favCategoryAdapter);
                    }
                    return false;
                });
                sortMenu.show();
            }
            else{
                getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu());
                sortMenu.setOnMenuItemClickListener(p->{
                    if(p.getItemId()==R.id.sort_name){
                        if(isFavorite){
                            Collections.sort(favoriteList, Comparator.comparing(FoodResObj::getFoodname));
                            list.setAdapter(favAdapter);
                        }else if(isCategory){
                            Collections.sort(categoryList, Comparator.comparing(FoodResObj::getFoodname));
                            list.setAdapter(categoryAdapter);
                        }else {
                            Collections.sort(foodResObjs, Comparator.comparing(FoodResObj::getFoodname));
                            list.setAdapter(foodAdapter);
                        }
                    } else if (p.getItemId()==R.id.sort_expire) {
                        if(isFavorite){
                            Collections.sort(favoriteList, Comparator.comparing(FoodResObj::getExpiredate));
                            list.setAdapter(favAdapter);
                        }else if(isCategory){
                            Collections.sort(categoryList, Comparator.comparing(FoodResObj::getExpiredate));
                            list.setAdapter(categoryAdapter);
                        }else {
                            Collections.sort(foodResObjs, Comparator.comparing(FoodResObj::getExpiredate));
                            list.setAdapter(foodAdapter);
                        }
                    }
                    return false;
                });
                sortMenu.show();
            }
        });


        search_btn.setOnClickListener(v->{
            count= 0;
            Log.d("btn 누름", ""+count);
            searchFoodList.clear();
            searchRecipeList.clear();

            String search=search_et.getText().toString();
            if (isRecipe){
                if(!search.isEmpty()){
                    count += 1;
                    Log.d("검색함", ""+count);
                    for(RecipeObj recipeObj : recipeObjs){
                        if(recipeObj.getRecipename().equals(search)) searchRecipeList.add(recipeObj);
                    }
                    searchRecipeAdapter=new RecipeAdapter(this,searchRecipeList);
                    list.setAdapter(searchRecipeAdapter);
                }else {
                    list.setAdapter(foodAdapter);
                }
            }
            else{
                if(!search.isEmpty()){
                    count += 1;
                    Log.d("겅색함", ""+count);
                    for(FoodResObj food : foodResObjs){
                        if(food.getFoodname().equals(search)) searchFoodList.add(food);
                    }
                    searchFoodAdapter=new FoodAdapter(this,searchFoodList);
                    list.setAdapter(searchFoodAdapter);
                    Log.d("caeate", "mm");

                }else {
                    Log.d("엥", "ㅋㅋ");
                    list.setAdapter(foodAdapter);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            new FoodListGetTask(username, refname,this);  // foodlist 요청
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        foodAdapter.notifyDataSetChanged();
        favAdapter.notifyDataSetChanged();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        categoryList.clear();
        favCategoryList.clear();
        expCategoryList.clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        String monthStr = dateFormat.format(new Date());

        if (requestCode==1300&&resultCode==RESULT_OK){
            if(data!=null){
                String selectedCategory = data.getStringExtra("category");
                for(FoodResObj foodResObj : foodResObjs){
                    if(foodResObj.getCategory().equals(selectedCategory)) {
                        categoryList.add(foodResObj);
                        //favCategoryList 추가
                        if(foodResObj.getFavorite().equals("true")) favCategoryList.add(foodResObj);
                        // expCategoryList 추가
                        String foodExp = foodResObj.getExpiredate();
                        String[] parts = foodExp.split("/");
                        String foodMonth = parts[0] + "/" + parts[1]; //"YYYY/MM"추출
                        Integer foodDay = Integer.parseInt(parts[2]); //DD 추출
                        if (foodMonth.equals(monthStr)) {
                            //음식이 exp가 이번 달이라면
                            if (foodDay >= currentDayInt && foodDay <= currentDayInt + 3) {
                                //유통기한이 오늘이거나 오늘 날짜 +3일 이내라면
                                expCategoryList.add(foodResObj);
                            }
                        }
                    }
                }
                Log.i("favCategoryList",favCategoryList.toString());
                Log.i("expCategoryList",expCategoryList.toString());
                isCategory=true;
                if(isFavorite){
                    isFavorite=false;
                    list.setAdapter(foodAdapter);
                    favorite.setText("즐겨찾기");
                    favorite.setBackgroundColor(getResources().getColor(R.color.purple_100));
                    favorite.setTextColor(getResources().getColor(R.color.black));
                } else if(isRecipe){
                    isRecipe=false;
                    list.setAdapter(foodAdapter);
                    recipe.setBackgroundColor(getResources().getColor(R.color.purple_100));
                    recipe.setTextColor(getResources().getColor(R.color.black));
                }
                Log.i("tag",categoryList.toString());
                categoryAdapter = new FoodAdapter(UserActivity.this, categoryList);
                favCategoryAdapter=new FoodAdapter(this, favCategoryList);
                expCategoryAdapter = new FoodAdapter(this, expCategoryList);
                list.setAdapter(categoryAdapter);
                category.setText(selectedCategory);
                category.setBackgroundColor(getResources().getColor(R.color.purple_200));
                category.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public void onFoodListReceived(List<FoodResObj> foodResObjs) {
        this.foodResObjs = foodResObjs;
        // Update UI with the received food list
        updateUI();
    }
    private void updateUI() {
        foodAdapter = new FoodAdapter(this, foodResObjs);
        favAdapter = new FoodAdapter(UserActivity.this, favoriteList);
        recipeAdapter=new RecipeAdapter(this, recipeObjs);
        favRecipeAdapter = new RecipeAdapter(this, favRecipeObjs);
        favCategoryAdapter = new FoodAdapter(this, favCategoryList);
        expCategoryAdapter=new FoodAdapter(this, expCategoryList);
        expRecipeAdapter=new RecipeAdapter(this,expRecipeList);
        searchFoodAdapter=new FoodAdapter(this,searchFoodList);


        if(isCategory){
            list.setAdapter(categoryAdapter);
        }else if(isRecipe){
            list.setAdapter(recipeAdapter);
        }else
            list.setAdapter(foodAdapter);
    }

    @Override
    public void onRecipeListReceived(List<RecipeObj> recipeObjs) {
        this.recipeObjs.clear();
        this.favRecipeObjs.clear();
        this.expRecipeList.clear();


        for(FoodResObj food : foodResObjs) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
            String monthStr = dateFormat.format(new Date());
            String foodExp = food.getExpiredate();
            String[] parts = foodExp.split("/");
            String foodMonth = parts[0] + "/" + parts[1]; //"YYYY/MM"추출
            int foodDay = Integer.parseInt(parts[2]); //DD 추출

            for (RecipeObj recipeObj : recipeObjs) {
                if (recipeObj.getMain().equals(food.getFoodname())) {
                    this.recipeObjs.add(recipeObj);

                    if (foodMonth.equals(monthStr)) {
                        //음식이 exp가 이번 달이라면
                        if (foodDay >= currentDayInt && foodDay <= currentDayInt + 3) {
                            //유통기한이 오늘이거나 오늘 날짜 +3일 이내라면
                            expRecipeAdapter.add(recipeObj);
                        }
                    }
                }
                else if (recipeObj.getSub1().equals(food.getFoodname())){
                    this.recipeObjs.add(recipeObj);

                    if (foodMonth.equals(monthStr)) {
                        //음식이 exp가 이번 달이라면
                        if (foodDay >= currentDayInt && foodDay <= currentDayInt + 3) {
                            //유통기한이 오늘이거나 오늘 날짜 +3일 이내라면
                            expRecipeAdapter.add(recipeObj);
                        }
                    }
                }
                else if (recipeObj.getSub2().equals(food.getFoodname())){
                    this.recipeObjs.add(recipeObj);

                    if (foodMonth.equals(monthStr)) {
                        //음식이 exp가 이번 달이라면
                        if (foodDay >= currentDayInt && foodDay <= currentDayInt + 3) {
                            //유통기한이 오늘이거나 오늘 날짜 +3일 이내라면
                            expRecipeAdapter.add(recipeObj);
                        }
                    }
                }
                if(food.getFavorite().equals("true")){
                    if (recipeObj.getMain().equals(food.getFoodname())) this.favRecipeObjs.add(recipeObj);
                    else if (recipeObj.getSub1().equals(food.getFoodname()))
                        this.favRecipeObjs.add(recipeObj);
                    else if (recipeObj.getSub2().equals(food.getFoodname()))
                        this.favRecipeObjs.add(recipeObj);
                }
            }
        }

        updateUI();

    }
}