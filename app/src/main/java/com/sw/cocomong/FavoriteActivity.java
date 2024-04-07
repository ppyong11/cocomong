package com.sw.cocomong;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        // 어댑터뷰, 데이터 연동하기.
        // DB
        List<String> favFoodList = new ArrayList<>();
        favFoodList.add("양파");
        favFoodList.add("마늘");
        favFoodList.add("고기");

        // 어댑터 준비

        ArrayAdapter<String> favFoodAdapter;
        favFoodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, favFoodList);

        // 어댑터 연결
        ListView list = (ListView) findViewById(R.id.food_list);
        list.setAdapter(favFoodAdapter);
        list.setOnItemClickListener(foodClickListener);

    }
    AdapterView.OnItemClickListener foodClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = (String) parent.getAdapter().getItem(position);
            Toast.makeText(getBaseContext(), item ,Toast.LENGTH_SHORT).show();
            // 화면 전환 인텐트 하면 될 듯
        }
    };

}
