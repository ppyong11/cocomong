package com.sw.cocomong.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;
import com.sw.cocomong.view.item.FoodListItem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class FoodAddActivity extends AppCompatActivity {
    TextView title;
    ImageButton back, edit;
    Button save, delete;
    EditText foodName, category, expire, memo;
    FoodListItem foodListItem;


    //사진 추가기능
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri captureUri;
    private ImageView foodPhoto;
    private int idView;
    private String absolutePath;

    private ActivityResultLauncher<Intent> resultLauncher;


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

        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        foodName.setEnabled(true);
        category.setEnabled(true);
        expire.setEnabled(true);
        memo.setEnabled(true);

        back.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(), MainFoodListActivity.foodListItems.get(2).getName(),Toast.LENGTH_SHORT).show();
            finish();

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
            finish();
        });

        foodPhoto = (ImageButton)this.findViewById(R.id.food_image);
        //foodPhoto.setOnClickListener(this);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==0){
                    Log.i("디버깅","디버깅 확인");
                }
            }
        });

    }

    //카메라로 이미지 가져오기
    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" +String.valueOf(System.currentTimeMillis()) + ".jpg";
        captureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, captureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    //앨번에서 이미지 가져오기
    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK) return;

        switch(requestCode){
            case PICK_FROM_ALBUM:{
                captureUri = data.getData();
                Log.d("SmartWheel",captureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:{
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(captureUri,"image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-date", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE:{
                if(resultCode != RESULT_OK) return;

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    foodPhoto.setImageBitmap(photo);

                    storeCropImage(photo, filePath);
                    absolutePath = filePath;
                    break;
                }
                File f = new File(captureUri.getPath());
                if(f.exists()) f.delete();
            }
        }
    }


    public void onClick(View v){
        idView = v.getId();
        if(v.getId() == R.id.food_image){
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .show();
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/smartWheel";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists()) directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
