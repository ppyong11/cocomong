package com.sw.cocomong.view.activity;

//import static com.sw.cocomong.view.activity.FoodAddActivity.foodimage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.sw.cocomong.R;


public class CameraCapture extends Activity {

    static CameraSurfaceView surfaceView;
    ImageView food_image_test;
    Button image_ok, image_deny;

    static Bitmap resizedBitmap; //bitmap 객체 저장
    String method;

    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_surface);


        //FoodAdd에서 method값 받아옴
        method= getIntent().getStringExtra("method");


        food_image_test=findViewById(R.id.photo_test);
        image_ok=findViewById(R.id.photo_ok);
        image_deny=findViewById(R.id.photo_deny);

        image_ok.setVisibility(View.GONE);
        image_deny.setVisibility(View.GONE);

        /*카메라 띄우기*/
        //food_image=findViewById(R.id.food_image);
        surfaceView=findViewById(R.id.surfaceView);

        Button button = findViewById(R.id.btn_takePhoto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카메라 사진 캡쳐
                capture();
                button.setVisibility(View.GONE);
                image_ok.setVisibility(View.VISIBLE);
                image_deny.setVisibility(View.VISIBLE);
                food_image_test.setVisibility(View.VISIBLE);

            }
        });

        image_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraCapture.this, FoodAddActivity.class);
                intent.putExtra("method", method);
                startActivity(intent);
                finish();
            }
        });

        image_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.VISIBLE);
                image_ok.setVisibility(View.GONE);
                image_deny.setVisibility(View.GONE);
                food_image_test.setVisibility(View.GONE);

            }
        });

    }

    public void capture() {
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8; // 1/8사이즈로 보여주기
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options); //data 어레이 안에 있는 데이터 불러와서 비트맵에 저장

                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                // 이미지 크기 조정 및 회전
                resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                resizedBitmap = Bitmap.createBitmap(resizedBitmap, 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight(), matrix, true);

                food_image_test.setImageBitmap(resizedBitmap); // 이미지뷰에 사진 보여주기
                camera.startPreview();
            }
        });
    }
}