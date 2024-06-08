package com.sw.cocomong.task.foodtask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FoodDetailTask {
    FoodResObj foodResObj;
    private final FoodDetailTaskListener listener;

    public interface FoodDetailTaskListener {
        void onFoodDetailReceived(FoodResObj foodResObj);
    }

    public FoodDetailTask(String foodid, FoodDetailTaskListener listener) throws JSONException, IOException {
        this.listener = listener;

        //String url = "http://58.224.91.191:8080/foods/"+foodid;
        ///String url = "http://192.168.236.1:8080/foods/"+foodid;  // dahee laptop
        String url = "http://192.168.219.144:8080/foods/"+foodid; //아현
        //request 작성
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();

        //응답 콜백
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    //응답실패
                    Log.i("tag", "응답실패");
                }else{
                    //응답성공
                    final String responseData = response.body().string();
                    Log.i("tag", "응답성공"+responseData);
                    ObjectMapper objectMapper = new ObjectMapper();
                    foodResObj = objectMapper.readValue(responseData, new TypeReference<FoodResObj>() {});
                    new Handler(Looper.getMainLooper()).post(() -> listener.onFoodDetailReceived(foodResObj));

                }
            }
        });

    }
}