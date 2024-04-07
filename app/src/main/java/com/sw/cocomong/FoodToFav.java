package com.sw.cocomong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FoodToFav extends AppCompatActivity {

    ListView list;

    String[] foodname = {
            "양파",
            "마늘",
            "돼지고기"
    };

    String[] foodcart = {
            "채소",
            "채소",
            "육류"
    };

    String[] fooddate = {
            "2025.02.01",
            "2024.05.01",
            "2022.03.02"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_item);

        Button btn_star = (Button)findViewById(R.id.btn_star);

        CustomList adapter = new CustomList(FoodToFav.this);
        list=(ListView) findViewById(R.id.list_food);
        list.setAdapter((ListAdapter) adapter);


        btn_star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodToFav.this, Favorite.class);
                startActivity(intent);
            }

        });

    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context ) {
            super(context, R.layout.favorite_item, foodname);
            this.context = context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.favorite_item, null, true);
            TextView food_name = (TextView) rowView.findViewById(R.id.food_name);
            TextView food_cart = (TextView) rowView.findViewById(R.id.category);
            TextView food_date = (TextView) rowView.findViewById(R.id.food_date);

            food_name.setText(foodname[position]);
            food_cart.setText(foodcart[position]);
            food_date.setText(fooddate[position]);

            return rowView;
        }
    }

}
