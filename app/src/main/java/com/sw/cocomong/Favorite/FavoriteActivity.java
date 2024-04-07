/*package com.sw.cocomong.Favorite;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.favorite_list);

        Intent intent = getIntent();
//        // 어댑터 연결
        ListView list = (ListView) findViewById(R.id.food_list);
        FoodAdapter adapter = new FoodAdapter(FavoriteActivity.this, android.R.layout.simple_list_item_1);

        adapter.add(new FoodListItem("양파","야채","24-4-13",true));
        adapter.add(new FoodListItem("감자","야채","24-4-13",true));
        adapter.add(new FoodListItem("마늘","카테고리2","24-4-13",true));

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //test
                String foodItem = adapter.getItem(position).toString();
                Toast.makeText(getBaseContext(), foodItem, Toast.LENGTH_SHORT).show();
                // 화면 전환 인텐트 하면 될 듯
                //Intent intent = new Intent(FavoriteActivity.this, FoodInfoActivity.class);
                //startActivity(intent);

            }
        });
    }

}
*/