package com.sw.cocomong;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FoodInfoActivity extends AppCompatActivity {
    ImageButton back;
    ImageButton edit;
    Button save;
    Button delete;
    TextView foodName;
    TextView category;
    TextView expire;
    TextView memo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        back=findViewById(R.id.btn_back);
        edit=findViewById(R.id.btn_edit);
        save=findViewById(R.id.btn_save);
        delete=findViewById(R.id.btn_delete);
        foodName =findViewById(R.id.tv_infoFoodName);
        category=findViewById(R.id.tv_infoCategory);
        expire=findViewById(R.id.tv_infoExpire);
        memo=findViewById(R.id.tv_memo);

        back.setOnClickListener(v->{

        });
        edit.setOnClickListener(v->{

        });
        delete.setOnClickListener(v->{

        });
        save.setOnClickListener(v->{

        });


    }

}
