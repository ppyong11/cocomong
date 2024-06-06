package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefListItemDto;
import com.sw.cocomong.task.BackgroundService;
import com.sw.cocomong.task.NotificationService;
import com.sw.cocomong.task.foodtask.FoodDeleteTask;
import com.sw.cocomong.task.foodtask.FoodDetailTask;
import com.sw.cocomong.task.foodtask.FoodEditTask;
import com.sw.cocomong.task.foodtask.FoodListGetTask;
import com.sw.cocomong.task.reftask.RefListGetTask;
import com.sw.cocomong.view.adapter.FoodAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements FoodListGetTask.FoodListGetTaskListener {

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
    private static final int REQUEST_SYSTEM_ALERT_WINDOW = 123;

    boolean isFavorite=false;
    boolean isCategory=false;
    String username,refname,refnum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refname=extras.getString("refname");  // 냉장고 위치 받아옴
        username=extras.getString("username");  // username 받아옴
/*
        // SharedPreferences 데이터 불러오기
        FoodSharedPreference foodSharedPreference = new FoodSharedPreference(this);
        Map<String, String> foodData = foodSharedPreference.getFoodData();

        // 데이터가 없는 경우
        if (foodData == null || foodData.isEmpty()) {
            Toast.makeText(this, "저장된 식재료 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (Map.Entry<String, String> entry : foodData.entrySet()) {
                String foodName = entry.getKey();
                String expireDate = entry.getValue();
                System.out.println("Food Name: " + foodName + ", Expire Date: " + expireDate);
            }
        }
*/
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
        Log.d("UserActivity", "BackgroundService 시작됨");


        try {
            new FoodListGetTask(username, refname,this);  // foodlist 요청
            //new RefListGetTask("dahee",this);                    // 냉장고 정보 받아옴
           // FoodDetailTask foodDetailTask = new FoodDetailTask(27);                                  //음식상세정보
            //foodResObjs=listGetTask;
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

        //foodAdapter = new FoodAdapter(UserActivity.this, foodListItemDtos, refListItemDto);
        foodAdapter = new FoodAdapter(UserActivity.this, foodResObjs);
        favAdapter = new FoodAdapter(UserActivity.this, favoriteList);
        list.setAdapter(foodAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String foodname = foodResObjs.get(position).getFoodname();
            String foodid = foodResObjs.get(position).getIdx().toString();

            Intent foodIntent = new Intent(UserActivity.this, FoodInfoActivity.class);
            foodIntent.putExtra("username",username);
            foodIntent.putExtra("foodname", foodname);
            foodIntent.putExtra("refname",refname);
            foodIntent.putExtra("foodid",foodid);
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
        try {
            new FoodListGetTask(username, refname,this);  // foodlist 요청
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    //백그라운드 알림 권한
    // 시스템 오버레이 권한을 확인하고 요청합니다.
    private void checkSystemAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                requestAlertWindowPermission();
            } else {
                // 이미 권한이 있는 경우 알림을 표시합니다.
                showNotification("test", "이미 권한 있음");
            }
        }
    }

    // 알림을 표시합니다.
    private void showNotification(String title, String message) {
        NotificationService.showNotification(this, title, message);
    }

    // 권한 요청 후 처리를 위한 메서드입니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SYSTEM_ALERT_WINDOW) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여되면 알림을 표시합니다.
                showNotification("test", "권한 부여됨");
            }
        }
    }

    // 시스템 오버레이 권한을 요청합니다.
    private void requestAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_SYSTEM_ALERT_WINDOW);
            } else {
                // 이미 권한이 있는 경우 알림을 표시합니다.
                showNotification("test", "이미 권한 있음");
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
        for (FoodResObj foodResObj : foodResObjs) {
            if (foodResObj.getFavorite().equals("true")) {
                favoriteList.add(foodResObj);
            }
        }
        foodAdapter = new FoodAdapter(this, foodResObjs);
        list.setAdapter(foodAdapter);
    }
}