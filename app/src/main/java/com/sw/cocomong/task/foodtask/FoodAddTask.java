package com.sw.cocomong.task.foodtask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.sw.cocomong.Object.FoodResObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FoodAddTask {
    static String url = "http://192.168.236.1:8080/foods/post"; // dahee laptop

    public FoodAddTask(FoodResObj foodResObj, String file) throws JSONException {
        File img = new File(file);

        // Create JSON data part
        JSONObject data = new JSONObject();
        data.put("username", foodResObj.getUsername());
        data.put("foodname", foodResObj.getFoodname());
        data.put("expiredate", foodResObj.getExpiredate());
        data.put("category", foodResObj.getCategory());
        data.put("memo", foodResObj.getMemo());
        data.put("favorite", foodResObj.getFavorite());
        data.put("refname", foodResObj.getRefname());

        // Create the request body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("request", data.toString())
                .addFormDataPart("itemName", "test"); // Replace "test" with the actual item name if needed

        // Add file part if file is provided
        if (img.exists()) {
            builder.addFormDataPart("file", img.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), img));
        }

        RequestBody requestBody = builder.build();

        // Build the request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // Send the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //응답 실패
                    Log.i("tag", "응답실패");
                } else {
                    // 응답 성공
                    Log.i("tag", "응답성공");
                    final String responseData = response.body().string();
                    Log.i("tag", responseData);
                }
            }
        });
    }
}