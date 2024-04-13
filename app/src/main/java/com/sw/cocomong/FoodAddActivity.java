package com.sw.cocomong;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.fooditem.FoodListItem;

public class FoodAddActivity extends AppCompatActivity {
    TextView title;
    ImageButton back, edit;
    Button save, delete;
    EditText foodName, category, expire, memo;
    FoodListItem foodListItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        title=findViewById(R.id.tv_infoTitle);
        back=findViewById(R.id.btn_back);
        edit=findViewById(R.id.btn_edit);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        foodName =findViewById(R.id.et_infoFoodName);
        category=findViewById(R.id.et_infoCategory);
        expire=findViewById(R.id.et_infoExpire);
        memo=findViewById(R.id.et_memo);

        delete.setVisibility(View.GONE);

        back.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(),MainFoodListActivity.foodListItems.get(2).getName(),Toast.LENGTH_SHORT).show();

        });

        edit.setOnClickListener(v->{
            save.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
            edit.setClickable(false);

            foodName.setEnabled(true);
            category.setEnabled(true);
            expire.setEnabled(true);
            memo.setEnabled(true);
        });

        save.setOnClickListener(v->{
            //Toast.makeText(getApplicationContext(),"save 눌림",Toast.LENGTH_SHORT).show();

            foodName.setEnabled(false);
            category.setEnabled(false);
            expire.setEnabled(false);
            memo.setEnabled(false);

            foodListItem=new FoodListItem(foodName.getText().toString(),category.getText().toString(),expire.getText().toString(),memo.getText().toString());
            MainFoodListActivity.foodListItems.add(foodListItem);

            save.setVisibility(View.GONE);

        });



    }

}
