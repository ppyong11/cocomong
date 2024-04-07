package com.sw.cocomong.Favorite;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.FoodInfoActivity;
import com.sw.cocomong.R;

public class FavoriteActivity extends AppCompatActivity {
    String[] favFoodList;
    ArrayAdapter<String> favFoodAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        // 어댑터뷰, 데이터 연동하기.
        // DB


        // 어댑터 준비


        favFoodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favFoodList);

//        // 어댑터 연결
        ListView list = (ListView) findViewById(R.id.food_list);
        FoodAdapter adapter = new FoodAdapter(FavoriteActivity.this, android.R.layout.simple_list_item_1);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String foodItem = (String) adapter.getItem(position);
                Toast.makeText(getBaseContext(), favFoodList[position], Toast.LENGTH_SHORT).show();
                // 화면 전환 인텐트 하면 될 듯
                Intent intent = new Intent(FavoriteActivity.this, FoodInfoActivity.class);
                startActivity(intent);

            }
        });
    }

}
