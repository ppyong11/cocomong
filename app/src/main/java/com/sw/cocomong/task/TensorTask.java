package com.sw.cocomong.task;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import android.util.Log;


import com.sw.cocomong.R;

//분류 및 카테고리 return
public class TensorTask {
    Interpreter tflite;
    Context context;
    Uri imgUri;
    static final int NUM_CLASSES = 4; // 모델의 클래스 수를 설정합니다.
    public static final String[] STRINGS_CLASSES = {"fruit", "livestock", "seafood", "vegetable"}; // 모델의 클래스를 설정합니다.

    //context 없으면 getAsstes, resource안됨, FoodAddSelectActivity에서 선택한 이미지 uri 받음
    //모델 로드 및 초기화 수행
    public TensorTask(Context context){
        this.context= context;
        // Load the TFLite model
        try {
            tflite = new Interpreter(loadModelFile());
            Log.d("load", "모델 불러오기 완료");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("load", "모델 불러오기 실패");
        }
    }

    //이미지 로드 및 전처리, 모델 실행 후 결과값 반환
    public int classifyBitmap(Bitmap bitmap){
        //촬영 후 분류
        if(bitmap == null){ //이미지 로드 실패 시 중단
            Log.d("classifyImage", "이미지 로드 실패");
            return -1;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
        Log.d("test", "bitmap 전환, 리사이즈 완료");

        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedBitmap);
        Log.d("test", "buffer");

        // Prepare output data
        float[][] output = new float[1][NUM_CLASSES]; // NUM_CLASSES is the number of classes
        Log.d("test", "1");

        // Run inference
        tflite.run(inputBuffer, output);
        Log.d("test", "2");
        // Process the output
        float[] probabilities = output[0];
        int maxIndex = getMaxIndex(probabilities);
        Log.d("TFLite", "Predicted class: " + STRINGS_CLASSES[maxIndex] + ", Confidence: " + 100*probabilities[maxIndex]);
        return maxIndex; //분류값 리턴
    }

    //이미지 로드 및 전처리, 모델 실행 후 결과값 반환
    public int classifyUri(Uri uri){
        //이미지 선택 후 분류
        Bitmap bitmap= loadImage(uri);
        if(bitmap == null){ //이미지 로드 실패 시 중단
            Log.d("classifyImage", "이미지 로드 실패");
            return -1;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
        Log.d("test", "bitmap 전환, 리사이즈 완료");

        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedBitmap);
        Log.d("test", "buffer");

        // Prepare output data
        float[][] output = new float[1][NUM_CLASSES]; // NUM_CLASSES is the number of classes
        Log.d("test", "1");

        // Run inference
        tflite.run(inputBuffer, output);
        Log.d("test", "2");
        // Process the output
        float[] probabilities = output[0];
        int maxIndex = getMaxIndex(probabilities);
        Log.d("TFLite", "Predicted class: " + STRINGS_CLASSES[maxIndex] + ", Confidence: " + 100*probabilities[maxIndex]);
        return maxIndex; //분류값 리턴
    }
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("20_plus.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private Bitmap loadImage(Uri uri) {
        try {
            // URI에서 비트맵으로 이미지 로드
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            if (bitmap == null) {
                Log.e("ImageLoad", "Failed to load image from path: " );
            } else {
                Log.d("ImageLoad", "Image loaded successfully from path: " );
            }
            return bitmap;
        } catch (Exception e) {
            Log.e("ImageLoad", "Exception while loading image from path: " , e);
            return null;
        }
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*224 * 224 * 3); // 300x300 이미지와 3 채널(RGB)을 위한 공간을 할당합니다.
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[224 * 224];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < 224; ++i) {
            for (int j = 0; j < 224; ++j) {
                int val = intValues[pixel++];
                byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f); // Red
                byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);  // Green
                byteBuffer.putFloat((val & 0xFF) / 255.0f);         // Blue
            }
        }
        return byteBuffer;
    }

    private int getMaxIndex(float[] probabilities) {
        int maxIndex = 0;
        float maxProbability = probabilities[0];
        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > maxProbability) {
                maxProbability = probabilities[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}