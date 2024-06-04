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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;
import com.sw.cocomong.dto.BarcodeResDto;
import com.sw.cocomong.task.BarcodeTask;
import com.sw.cocomong.task.foodtask.FoodAddTask;

import org.json.JSONException;

import java.util.regex.Pattern;

// 바코드를 통해서 음식을 추가함.
public class BarcodeScanner extends AppCompatActivity {

    ImageView foodimage;
    TextView title_tv, category_tv, barcodeTest_tv, barcode_tv;
    ImageButton back, edit;
    Button save, delete, btnCategory;
    EditText foodName_et, expire_et, memo_et;
    //FoodListItemDto foodListItemDto;
    //RefListItemDto refListItemDto;
    FoodResObj foodResObj;
    Bitmap foodImageBitmap=null;
    String refnum, refname, username;
    String dateRegex = "^(?:(?:19|20)\\d{2})/(0[1-9]|1[0-2])/(0[1-9]|1\\d|2[0-8]|29(?!/02)|30(?!/02|/04|/06|/09|/11)|31(?=/0[13578]|/1[02]))$|^(?:(?:19|20)(?:[02468][048]|[13579][26]))/02/29$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_info);
        startScan();

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refname=extras.getString("refname");
        username = extras.getString("username");
        refnum=extras.getString("refnum");

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


        btnCategory.setOnClickListener(v->{
            Intent intentCategory = new Intent(BarcodeScanner.this, CategorySelectActivity.class);
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
                foodResObj = new FoodResObj(null,foodname,username,expiredate,category,memo,"false",refname);

                try {
                    FoodAddTask foodAddTask = new FoodAddTask(foodResObj);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                save.setVisibility(View.GONE);
                finish();
            } else Toast.makeText(this, "유통기한을 다시 입력하세요",Toast.LENGTH_SHORT).show();
        });


        expire_et.addTextChangedListener(new TextWatcher(){
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
    }

    private void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a barcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode==1212&&resultCode==RESULT_OK){
            if(intent!=null){
                String selectedCategory = intent.getStringExtra("category");
                category_tv.findViewById(R.id.tv_category);
                category_tv.setText(selectedCategory);
            }
        }else{
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (result != null) {
                if (result.getContents() != null) {
                    String text = result.getContents();
                    BarcodeTask barcodeTask = new BarcodeTask(text);
                    try {
                        BarcodeResDto taskResult = barcodeTask.execute(text).get();

                        barcode_tv.setText(text);
                        foodName_et.setText(taskResult.getProductName());
                        category_tv.setText(barcodeCategory(taskResult.getCategory()));
                        memo_et.setText(taskResult.getDayCount());

                        if (barcodeTask.getResponseCode() == 200| taskResult==null) {
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    // TODO: 2024-05-20 수정하기
                }else{
                    String text = barcode_tv.getText().toString();
                    BarcodeTask barcodeTask = new BarcodeTask(text);
                    try {
                        BarcodeResDto taskResult = barcodeTask.execute(text).get();

                        barcode_tv.setText(text);
                        foodName_et.setText(taskResult.getProductName());
                        category_tv.setText(taskResult.getCategory());
                        memo_et.setText(taskResult.getDayCount());

                        if (barcodeTask.getResponseCode() == 200| taskResult==null) {
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private String barcodeCategory(String category){
        if(category.equals("과일")) return "과일";
        else if (category.equals("채소")) return "채소";
        else if (category.equals("유제품")) return "유제품";
        else if (category.equals("축산품")) return "축산품";
        else if (category.equals("해산물")) return "해산물";
        else if (category.equals("가공식품")) return "가공식품";
        else if (category.equals("음료수")) return "음료수";
        else return "기타";
    }
}