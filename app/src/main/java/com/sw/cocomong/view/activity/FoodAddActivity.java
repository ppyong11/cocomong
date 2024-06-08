package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.RefFoodMap;
import com.sw.cocomong.task.TensorTask;
import com.sw.cocomong.task.foodtask.FoodAddTask;

import org.json.JSONException;

import java.util.regex.Pattern;

// 사진으로 받아오는 액티비티
public class FoodAddActivity extends AppCompatActivity {

    ImageView foodimage;
    TextView title_tv, category_tv, barcode_tv, barcodeTest_tv;
    ImageButton back, edit;
    Button save, delete, btnCategory;
    EditText foodName_et, expire_et, memo_et;
    //FoodListItemDto foodListItemDto;
    //RefListItemDto refListItemDto;
    FoodResObj foodResObj;
    RefObj refObj;
    Bitmap foodImageBitmap;
    //int foodPosition, refPosition;
    String foodname, refname,refnum,username, imageUriString, filepath;
    Uri imageUri;
    static  final String[] CATEGORY= {"과일", "축산물", "해산물", "채소"};
    String dateRegex = "^(?:(?:19|20)\\d{2})/(0[1-9]|1[0-2])/(0[1-9]|1\\d|2[0-8]|29(?!/02)|30(?!/02|/04|/06|/09|/11)|31(?=/0[13578]|/1[02]))$|^(?:(?:19|20)(?:[02468][048]|[13579][26]))/02/29$";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);

        //갤러리, 촬영 때 받아온 extra
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refname=extras.getString("refname");
        //foodname은 안 받아오는디?
        foodname=extras.getString("foodname");
        refnum=extras.getString("refnum");
        username=extras.getString("username");
        imageUriString= extras.getString("uri");
        Log.d("FoodAddActivity", "uri 받음"+imageUriString);

        title_tv = findViewById(R.id.tv_infoTitle);
        back = findViewById(R.id.btn_back);
        edit = findViewById(R.id.btn_edit);
        save = findViewById(R.id.btn_save);
        delete = findViewById(R.id.btn_delete);
        foodName_et = findViewById(R.id.et_infoFoodName);
        btnCategory = findViewById(R.id.btn_infoCategory);
        category_tv = findViewById(R.id.tv_category);
        expire_et = findViewById(R.id.et_infoExpire);
        memo_et = findViewById(R.id.et_memo);
        barcodeTest_tv =findViewById(R.id.btn_barcode);
        barcode_tv =findViewById(R.id.tv_barcodeNum);

        foodimage = findViewById(R.id.food_image);

        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        foodName_et.setEnabled(true);
        btnCategory.setEnabled(true);
        expire_et.setEnabled(true);
        memo_et.setEnabled(true);

        //이미지 분류 실행
        if (!imageUriString.isEmpty())
            imagePredict(imageUriString);

//        foodImageBitmap=CameraCapture.moveCameraBitmap();
//        foodimage.setImageBitmap(foodImageBitmap);

        btnCategory.setOnClickListener(v->{
            Intent intentCategory = new Intent(FoodAddActivity.this, CategorySelectActivity.class);
            // FoodAddTask 실행?
            startActivityForResult(intentCategory,1212);
        });


        back.setOnClickListener(v -> {
            finish();
        });

        save.setOnClickListener(v -> {
            String expireCheck= expire_et.getText().toString();
            boolean isMatch = Pattern.matches(dateRegex,expireCheck);
            if(isMatch) {
                if (foodName_et.getText().length()!=0){
                    foodName_et.setEnabled(false);
                    category_tv.setEnabled(false);
                    btnCategory.setEnabled(false);
                    expire_et.setEnabled(false);
                    memo_et.setEnabled(false);

                    String foodname= foodName_et.getText().toString();
                    String expiredate= expire_et.getText().toString();
                    String category=category_tv.getText().toString();
                    String memo=memo_et.getText().toString();
                    //foodListItemDto = new FoodListItemDto(foodImageBitmap, foodName.getText().toString(), category.getText().toString(), expire.getText().toString(), memo.getText().toString(), false, refListItemDto.getRefId());
                    foodResObj = new FoodResObj(null,username,foodname,expiredate,category,memo,"false",refname, imageUriString);
                    try {
                        FoodAddTask foodAddTask = new FoodAddTask(foodResObj);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    save.setVisibility(View.GONE);
                    finish();
                } else Toast.makeText(this, "이름을 다시 입력하세요",Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "유통기한을 다시 입력하세요",Toast.LENGTH_SHORT).show();
        });

        expire_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (expire_et.isFocusable() && !s.toString().isEmpty()) {
                    int textlength = 0;
                    try {
                            textlength = expire_et.getText().toString().length();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            return;
                        }

                        String currentText = expire_et.getText().toString();

                        if (textlength == 4 && before != 1) {
                            currentText += "/";
                            expire_et.setText(currentText);
                            expire_et.setSelection(currentText.length());
                        } else if (textlength == 7 && before != 1) {
                            currentText += "/";
                            expire_et.setText(currentText);
                            expire_et.setSelection(currentText.length());
                        } else if (textlength == 5 && !currentText.contains("/")) {
                            currentText = currentText.substring(0, 4) + "." + currentText.substring(4);
                            expire_et.setText(currentText);
                            expire_et.setSelection(currentText.length());
                        } else if (textlength == 8 && !currentText.substring(7, 8).equals("/")) {
                            currentText = currentText.substring(0, 7) + "." + currentText.substring(7);
                            expire_et.setText(currentText);
                            expire_et.setSelection(currentText.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
            });

/*
        barcodeTest.setOnClickListener(v->{
            String barcodeNum=barcode.getText().toString();
            BarcodeTask barcodeTask = new BarcodeTask(barcodeNum);
            try {
                BarcodeResDto result = barcodeTask.execute(barcodeNum).get();

                foodName.setText(result.getProductName());
                category.setText(result.getCategory());
                category.setText(result.getCategory());
                memo.setText(result.getDayCount());

                if (barcodeTask.getResponseCode() == 200| result==null) {
                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();

            } catch (Exception e){
                e.printStackTrace();
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1212&&resultCode==RESULT_OK){
            if(data!=null){
                String selectedCategory = data.getStringExtra("category");
                category_tv.findViewById(R.id.tv_category);
                category_tv.setText(selectedCategory);
            }
        }
    }

    // TODO: 2024-05-13 바코드 인식 후 카테고리 설정해주는 로직
    public String setCategory(String categoryName){

        return "";
    }

    //이미지 가져오기, 촬영 선택 시 모델 실행
    protected void imagePredict(String uriString){
        imageUri= Uri.parse(uriString);
        // Context와 이미지 uri를 사용하여 TensorTask 인스턴스를 생성하고 초기화
        if (imageUri != null) {
            foodimage.setImageURI(imageUri); //분류하는 이미지 보이기
            // URI를 사용하여 이미지 분류 실행
            TensorTask tensorTask = new TensorTask(this);
            int classificationResult = tensorTask.classifyUri(imageUri);
            // 반환된 분류결과로 카테고리 설정
            category_tv.setText(CATEGORY[classificationResult]);
        }
    }
}