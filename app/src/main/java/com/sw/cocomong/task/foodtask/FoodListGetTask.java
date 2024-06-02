package com.sw.cocomong.task.foodtask;


import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cocomong.Object.FoodObj;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FoodListGetTask {
    //static String url = "http://58.224.91.191:8080/foods/list/asc";
    static String url = "localhost:8080/join";
    ArrayList<FoodObj> foodList = new ArrayList<>();
    public FoodListGetTask(String username, int refnum) throws JSONException, IOException {

        //Request에 사용할 JSON 작성
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("refnum", refnum);
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
                    ObjectMapper objectMapper = new ObjectMapper();
                    FoodObj[] foodArray = objectMapper.readValue(responseData, FoodObj[].class);
                    foodList.addAll(Arrays.asList(foodArray));

                    Log.i("tag", responseData);
                }
            }
        });
    }
    public ArrayList<FoodObj> getList(){
        return foodList;
    }
}
