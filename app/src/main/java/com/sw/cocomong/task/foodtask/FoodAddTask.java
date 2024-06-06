package com.sw.cocomong.task.foodtask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.sw.cocomong.Object.FoodResObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FoodAddTask {
    //static String url = "http://58.224.91.191:8080/foods/post"; 차차
    //static String url = "http://121.181.25.225:8080/foods/post"; 다희
    static String url = "http://192.168.219.144:8080/foods/post"; //아현
    public FoodAddTask(FoodResObj foodResObj) throws JSONException {

        //Request에 사용할 JSON 작성
        JSONObject data = new JSONObject();
        //data.put("username", foodResObj.getUsername());
        data.put("username","dahee");
        data.put("foodname", foodResObj.getFoodname());
        data.put("expiredate", foodResObj.getExpiredate());
        data.put("category", foodResObj.getCategory());
        data.put("memo", foodResObj.getMemo());
        data.put("favorite", foodResObj.getFavorite());
        //data.put("refname", foodResObj.getRefname());
        data.put("refname","냉장고1");
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), data.toString());

        //request 작성
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();

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
