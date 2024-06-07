package com.sw.cocomong.view.activity;

//import static com.sw.cocomong.view.activity.FoodAddActivity.foodimage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.sw.cocomong.R;


public class CameraCapture extends Activity {

    static CameraSurfaceView surfaceView;
    ImageView food_image_test;
    Button image_ok, image_deny;
    String refname, username, refnum; //FoodAddSelectActivity에서 보내는 값 받기

    static Bitmap resizedBitmap; //bitmap 객체 저장

    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_surface);


        //FoodAdd에서 보낸 값 받아옴, foodAddActivity로 보낼 것
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        refname=extras.getString("refname");
        refnum=extras.getString("refnum");
        username=extras.getString("username");

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
                String imageUri= saveImageToGallery(resizedBitmap);
                Intent foodAddIntent = new Intent(CameraCapture.this, FoodAddActivity.class);
                foodAddIntent.putExtra("uri", imageUri);
                foodAddIntent.putExtra("refname",refname);
                foodAddIntent.putExtra("username",username);
                foodAddIntent.putExtra("refnum",refnum);
                startActivity(foodAddIntent);
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
    // 갤러리에 이미지 저장하고 uri 스트링 반환
    private String saveImageToGallery(Bitmap bitmap) {
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Cocomong_Image",
                "Image captured by Cocomong app"
        );
        // 갤러리에 이미지 저장 후 해당 이미지의 URI를 반환받음
        if (savedImageURL != null) {
            // 이미지 저장에 성공한 경우
            Log.d("CameraCapture", savedImageURL.toString());
            // savedImageURL을 사용하여 갤러리에 저장된 이미지에 대한 작업을 수행할 수 있음
        } else {
            // 이미지 저장에 실패한 경우
        }
        return savedImageURL;
    }
}