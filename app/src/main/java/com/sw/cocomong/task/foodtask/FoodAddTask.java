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
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FoodAddTask {
    //static String url = "http://58.224.91.191:8080/foods/post";
    //static String url = "http://58.224.91.191:8080:8080/foods/post";
    String url = "http://192.168.236.1:8080/foods/post";  // dahee laptop
    public FoodAddTask(FoodResObj foodResObj, String file) throws JSONException {

        File img = new File(file);

        //Request에 사용할 JSON 작성
        JSONObject data = new JSONObject();
        //data.put("username", foodResObj.getUsername());
        data.put("username",foodResObj.getUsername());
        data.put("foodname", foodResObj.getFoodname());
        data.put("expiredate", foodResObj.getExpiredate());
        data.put("category", foodResObj.getCategory());
        data.put("memo", foodResObj.getMemo());
        data.put("favorite", foodResObj.getFavorite());
        data.put("refname", foodResObj.getRefname());
        //data.put("refname","냉장고1");
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), data.toString());

        //request 작성
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("request", null, RequestBody.create(MediaType.parse("application/json"), data.toString()) )
                .addFormDataPart("itemName", "test")
                .addFormDataPart("file" , img.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),
                        img)).build();

        Request request = new Request.Builder().url(url).method("post", body).build();

        // 응답 콜백
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
                }else{
                    // 응답 성공
                    Log.i("tag", "응답성공");
                    final String responseData = response.body().string();
                    Log.i("tag", responseData);
                }

            }
        });

    }

}
