package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.FoodListItemDto;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.task.foodtask.FoodDeleteTask;
import com.sw.cocomong.task.foodtask.FoodDetailTask;
import com.sw.cocomong.task.foodtask.FoodEditTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.regex.Pattern;

// foodList를 통해서 들어와서 수정
public class FoodInfoActivity extends AppCompatActivity implements FoodDetailTask.FoodDetailTaskListener, FoodEditTask.FoodEditTaskListener {
    TextView title, category;
    ImageButton back, edit;
    Button save, delete, btnCategory;
    EditText foodName_et, expire, memo;
    //FoodListItemDto foodListItemDto;
   // RefListItemDto refListItemDto;
    ImageView foodImage;
    Bitmap foodImageBitmap;
    //int foodPosition, refPosition;
    String dateRegex = "^(?:(?:19|20)\\d{2})/(0[1-9]|1[0-2])/(0[1-9]|1\\d|2[0-8]|29(?!/02)|30(?!/02|/04|/06|/09|/11)|31(?=/0[13578]|/1[02]))$|^(?:(?:19|20)(?:[02468][048]|[13579][26]))/02/29$";
    String username, foodname, refname,foodid;
    //FoodObj foodObj;
    FoodResObj foodResObj;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username = extras.getString("username");
        refname = extras.getString("refname");
        foodname = extras.getString("foodname");
        foodid = extras.getString("foodid");
        //refListItemDto=RefFoodMap.getRefListItemDtos().get(refPosition);
        try {
            new FoodDetailTask(foodid,this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        foodImage = findViewById(R.id.food_image);
        title = findViewById(R.id.tv_infoTitle);
        back = findViewById(R.id.btn_back);
        edit = findViewById(R.id.btn_edit);
        save = findViewById(R.id.btn_save);
        delete = findViewById(R.id.btn_delete);
        foodName_et = findViewById(R.id.et_infoFoodName);
        btnCategory = findViewById(R.id.btn_infoCategory);
        category = findViewById(R.id.tv_category);
        expire = findViewById(R.id.et_infoExpire);
        memo = findViewById(R.id.et_memo);

        save.setVisibility(View.GONE);
        delete.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);

        foodName_et.setEnabled(false);
        btnCategory.setEnabled(false);
        expire.setEnabled(false);
        memo.setEnabled(false);


        back.setOnClickListener(v -> {
            finish();
        });

        edit.setOnClickListener(v -> {
            save.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);

            foodName_et.setEnabled(true);
            btnCategory.setEnabled(true);
            expire.setEnabled(true);
            memo.setEnabled(true);
        });

        btnCategory.setOnClickListener(v -> {
            Intent intentCategory = new Intent(FoodInfoActivity.this, CategorySelectActivity.class);
            // FoodAddTask 실행?
            startActivityForResult(intentCategory, 1212);
        });

        save.setOnClickListener(v -> {
            String expireCheck=expire.getText().toString();
            boolean isMatch = Pattern.matches(dateRegex,expireCheck);
            if(isMatch) {
                foodName_et.setEnabled(false);
                category.setEnabled(false);
                btnCategory.setEnabled(false);
                expire.setEnabled(false);
                memo.setEnabled(false);

                //foodListItemDto = new FoodListItemDto(foodImageBitmap, foodName_et.getText().toString(), category.getText().toString(), expire.getText().toString(), memo.getText().toString(), false, refListItemDto.getRefId());
                //RefFoodMap.getFoodListItemDtos(refListItemDto).set(foodPosition,foodListItemDto);
                foodResObj.setFoodname(foodName_et.getText().toString());
                foodResObj.setCategory(category.getText().toString());
                foodResObj.setExpiredate(expire.getText().toString());
                foodResObj.setMemo(memo.getText().toString());

                try {
                    new FoodEditTask(foodResObj,this);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 저장 버튼 사라지기
                save.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                finish();
            } else Toast.makeText(this, "유통기한을 다시 입력하세요",Toast.LENGTH_SHORT).show();
        });
        expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (expire.isFocusable() && !s.toString().isEmpty()) {
                    int textlength = 0;
                    try {
                        textlength = expire.getText().toString().length();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }

                    String currentText = expire.getText().toString();

                    if (textlength == 4 && before != 1) {
                        currentText += "/";
                        expire.setText(currentText);
                        expire.setSelection(currentText.length());
                    } else if (textlength == 7 && before != 1) {
                        currentText += "/";
                        expire.setText(currentText);
                        expire.setSelection(currentText.length());
                    } else if (textlength == 5 && !currentText.contains("/")) {
                        currentText = currentText.substring(0, 4) + "." + currentText.substring(4);
                        expire.setText(currentText);
                        expire.setSelection(currentText.length());
                    } else if (textlength == 8 && !currentText.substring(7, 8).equals("/")) {
                        currentText = currentText.substring(0, 7) + "." + currentText.substring(7);
                        expire.setText(currentText);
                        expire.setSelection(currentText.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        delete.setOnClickListener(v -> {
            // 데이터 삭제
            //RefFoodMap.getFoodListItemDtos(refListItemDto).remove(foodPosition);
            try {
                FoodDeleteTask foodDeleteTask = new FoodDeleteTask(foodResObj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 액티비티 종료
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1212 && resultCode == RESULT_OK) {
            if (intent != null) {
                String selectedCategory = intent.getStringExtra("category");
                category.findViewById(R.id.tv_category);
                category.setText(selectedCategory);
            }
        }
    }

    @Override
    public void onFoodDetailReceived(FoodResObj foodResObj) {
        this.foodResObj=foodResObj;

        foodImage = findViewById(R.id.food_image);
        title = findViewById(R.id.tv_infoTitle);
        back = findViewById(R.id.btn_back);
        edit = findViewById(R.id.btn_edit);
        save = findViewById(R.id.btn_save);
        delete = findViewById(R.id.btn_delete);
        foodName_et = findViewById(R.id.et_infoFoodName);
        btnCategory = findViewById(R.id.btn_infoCategory);
        category = findViewById(R.id.tv_category);
        expire = findViewById(R.id.et_infoExpire);
        memo = findViewById(R.id.et_memo);


        // foodImageBitmap = foodObj.getFoodImage();
        foodImage.setImageBitmap(foodImageBitmap);
        title.setText(foodResObj.getFoodname());
        foodName_et.setText(foodResObj.getFoodname());
        category.setText(foodResObj.getCategory());
        expire.setText(foodResObj.getExpiredate());
        memo.setText(foodResObj.getMemo());
    }

    @Override
    public void onFoodEditReceived(FoodResObj foodResObj) {
        this.foodResObj=foodResObj;

        foodImage = findViewById(R.id.food_image);
        title = findViewById(R.id.tv_infoTitle);
        back = findViewById(R.id.btn_back);
        edit = findViewById(R.id.btn_edit);
        save = findViewById(R.id.btn_save);
        delete = findViewById(R.id.btn_delete);
        foodName_et = findViewById(R.id.et_infoFoodName);
        btnCategory = findViewById(R.id.btn_infoCategory);
        category = findViewById(R.id.tv_category);
        expire = findViewById(R.id.et_infoExpire);
        memo = findViewById(R.id.et_memo);


        // foodImageBitmap = foodObj.getFoodImage();
        foodImage.setImageBitmap(foodImageBitmap);
        title.setText(foodResObj.getFoodname());
        foodName_et.setText(foodResObj.getFoodname());
        category.setText(foodResObj.getCategory());
        expire.setText(foodResObj.getExpiredate());
        memo.setText(foodResObj.getMemo());
    }
}