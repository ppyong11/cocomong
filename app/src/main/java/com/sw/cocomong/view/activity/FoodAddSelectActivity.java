package com.sw.cocomong.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sw.cocomong.R;
import com.sw.cocomong.task.TensorTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodAddSelectActivity extends AppCompatActivity {

    ImageButton photo, barcode;
    static String uriString; //foodAdd액티비티에서 참조할 uri

    private String TAG = FoodAddSelectActivity.class.getSimpleName();
    private Context context = FoodAddSelectActivity.this;
    final int PERMISSION_REQUEST_CODE = 1;
    String refname,username,refnum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_add_select_popup);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refname=extras.getString("refname");
        username = extras.getString("username");
        refnum=extras.getString("refnum");

        photo=findViewById(R.id.btn_photo);
        barcode=findViewById(R.id.btn_barcode);

        photo.setOnClickListener(v->{
            Toast.makeText(this, "사진 클릭", Toast.LENGTH_SHORT).show();
            final PopupMenu sortMenu = new PopupMenu(getApplicationContext(),v);
            getMenuInflater().inflate(R.menu.food_photo_select_menu, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(p->{
                if(p.getItemId()==R.id.select_photo){
                    Toast.makeText(this, "사진 가져오기 선택", Toast.LENGTH_SHORT).show();
                    //갤러리 이동
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                    galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    galleryIntent.putExtra("refname",refname);
                    galleryIntent.putExtra("username",username);
                    galleryIntent.putExtra("refnum",refnum);
                    activityResultLauncher.launch(galleryIntent);

                } else if (p.getItemId()==R.id.select_camera) {
                    Toast.makeText(this, "카메라 찍기 선택", Toast.LENGTH_SHORT).show();
                    Intent cameraIntent = new Intent(FoodAddSelectActivity.this, CameraCapture.class);
                    cameraIntent.putExtra("refname",refname);
                    cameraIntent.putExtra("username",username);
                    cameraIntent.putExtra("refnum",refnum);
                    cameraIntent.putExtra("method","camera");
                    startActivity(cameraIntent);
                    finish();
                }
                return false;
            });
            sortMenu.show();
        });

        barcode.setOnClickListener(v->{
            Toast.makeText(this, "바코드 클릭", Toast.LENGTH_SHORT).show();
//            IntentIntegrator integrator = new IntentIntegrator(FoodAddSelectActivity.this);
//            integrator.setOrientationLocked(false); //스캔 방향전환을 위한 설정
//            integrator.setCaptureActivity(BarcodeScanner.class);
//            integrator.initiateScan();
            Intent barcodeIntent = new Intent(FoodAddSelectActivity.this, BarcodeScanner.class);
            barcodeIntent.putExtra("refname",refname);
            barcodeIntent.putExtra("username",username);
            barcodeIntent.putExtra("refnum",refnum);
            startActivity(barcodeIntent);
            finish();
        });
        checkPermission();
    }
    //ActivityResultLauncher 초기화, 갤러리 이동 인텐트, img to bitmap 후 넘김
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // URI를 FoodAddActivity로 전달
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Uri uri = intent.getData();
                            //FoodAddActivity에서 갤러리, 카메라 선택에 따라 달리 구현하기 위해 데이터 넘겨줌
                            if (uri != null) {
                                Log.d("uri", "uri 받아옴");
                                // URI를 String으로 변환하여 TensorTask로 전달
                                uriString = uri.toString();
                                Log.d("uri", uriString);
                                Intent foodAddIntent = new Intent(FoodAddSelectActivity.this, FoodAddActivity.class);
                                foodAddIntent.putExtra("method", "gallery");
                                foodAddIntent.putExtra("refname",refname);
                                foodAddIntent.putExtra("username",username);
                                foodAddIntent.putExtra("refnum",refnum);
                                startActivity(foodAddIntent);
                            }
                        }
                    }
                }
            }
    );

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // 스캔 취소됨
                // 여기에서 스캔 취소 시 처리할 작업을 추가할 수 있습니다.
            } else {
                // 스캔 성공
                String scannedData = result.getContents();
                // 여기에서 스캔된 데이터를 처리할 작업을 추가할 수 있습니다.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}