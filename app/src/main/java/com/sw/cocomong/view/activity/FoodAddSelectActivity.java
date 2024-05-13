package com.sw.cocomong.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sw.cocomong.R;

import java.io.File;
import java.util.ArrayList;

public class FoodAddSelectActivity extends AppCompatActivity {





    ImageButton photo, barcode;

    private String TAG = FoodAddSelectActivity.class.getSimpleName();
    private Context context = FoodAddSelectActivity.this;
    final int PERMISSION_REQUEST_CODE = 1;


    @SuppressLint("MissingInflatedId")
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
                    finish();

                } else if (p.getItemId()==R.id.select_camera) {
                    Toast.makeText(this, "카메라 찍기 선택", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodAddSelectActivity.this, CameraCapture.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            });
            sortMenu.show();
        });

        barcode.setOnClickListener(v->{
            Toast.makeText(this, "바코드 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FoodAddSelectActivity.this, FoodAddActivity.class);
            startActivity(intent);
            finish();
        });
        checkPermission();
    }

    public static String[] requiredPermissionList = new String[]{  //필요한 권한들
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
    };

    ArrayList<String> neededPermissionList = new ArrayList<>(); //권한 요청이 필요한 리스트

    /* 권한 요청 체크 */
    public void checkPermission() {

        //안드로이드 OS 버전이 23 이상일 경우 - 권한 허용 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String thisPermission : requiredPermissionList) {
                //해당 권한이 있는지 확인
                if (ContextCompat.checkSelfPermission(context, thisPermission) != PackageManager.PERMISSION_GRANTED) {
                    neededPermissionList.add(thisPermission);   //권한허용이 필요한 경우 neededPermissionList 리스트에 추가해준다
                }
            }

            if(neededPermissionList.size() > 0) {
                initView();  //권한허용이 필요한 경우 initView() 에서 권한 요청 팝업을 띄워준다
            } else {
                goNext(true);
            }

        } else {  //안드로이드 OS 버전이 23 이상이 아닐 경우 - 권한 허용 불필요
            goNext(true);
        }
    }

    /* View 설정 */
    public void initView(){
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onRequestPermissionsCheck()){
                    goNext(true);
                }
            }
        });

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onRequestPermissionsCheck()){
                    goNext(true);
                }
            }
        });
    }


    /* 권한 요청 코드 -> ~ 권한 허용하시겠습니까? 팝업 표시 */
    public boolean onRequestPermissionsCheck() {
        if (!neededPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, neededPermissionList.toArray(new String[neededPermissionList.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }


    /* 권한 요청에 대한 팝업 결과(허용/거부) 처리 */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> deniedPermission = new ArrayList<String>();  //권한 요청이 거부된 리스트

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        Log.d(TAG, "***** onRequestPermissionsResult() - result : " + i);
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 거부 - 권한허용이 필요한 거부된 경우 deniedPermission 리스트에 추가해준다
                            deniedPermission.add(requiredPermissionList[i]);
                        }
                    }

                    if (deniedPermission.size() < 1) {
                        goNext(true);
                    } else {
                        goNext(false);
                    }
                }
                return;
        }
    }


    /* 권한 요청에 따른 결과 Toast 표시 */
    public void goNext(boolean successFlag){
        Log.d(TAG, "***** goNext() - successFlag : "+successFlag);
        if(successFlag) {
            Toast.makeText(context, "모든 권한을 허용했네요!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "모든 권한을 허용해야합니다.", Toast.LENGTH_SHORT).show();
        }
    }





}

