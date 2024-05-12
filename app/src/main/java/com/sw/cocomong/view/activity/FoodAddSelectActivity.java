package com.sw.cocomong.view.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;

public class FoodAddSelectActivity extends AppCompatActivity {

    ImageButton photo, barcode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_add_select_popup);

        photo=findViewById(R.id.btn_photo);
        barcode=findViewById(R.id.btn_barcode);

        photo.setOnClickListener(v->{
            Toast.makeText(this, "사진 클릭", Toast.LENGTH_SHORT).show();
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.food_photo_select_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.select_photo){
                    Toast.makeText(this, "사진 가져오기 선택", Toast.LENGTH_SHORT).show();
                } else if (p.getItemId()==R.id.select_camera) {
                    Toast.makeText(this, "카메라 찍기 선택", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
            sortMenu.show();
        });

        barcode.setOnClickListener(v->{
            Toast.makeText(this, "바코드 클릭", Toast.LENGTH_SHORT).show();
        });
    }
}
