package com.sw.cocomong.task.foodtask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cocomong.Object.FoodResObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FoodListGetTask {

    //static String url = "http://58.224.91.191:8080/foods/list/asc";
    //static String url = "http://121.181.25.225:8080/foods/list/asc";
    //String url = "http://192.168.236.1:8080/foods/list/asc";  // dahee laptop
    String url = "http://118.139.210.235:8080/foods/list/asc";  // chae laptop

    private List<FoodResObj> foodResObjs;
    private final FoodListGetTaskListener listener;

    public interface FoodListGetTaskListener {
        void onFoodListReceived(List<FoodResObj> foodResObjs);
    }

    public FoodListGetTask(String username, String refname, FoodListGetTaskListener listener) throws JSONException, IOException {
        this.listener = listener;

        //Request에 사용할 JSON 작성
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("refname", refname);
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
                    final String responseData = response.body().string();
                    Log.i("tag", "응답성공"+responseData);
                    ObjectMapper objectMapper = new ObjectMapper();
                    foodResObjs = objectMapper.readValue(responseData, new TypeReference<List<FoodResObj>>() {});

                    Log.i("tag", "foodResObj" + foodResObjs.toString());

                    // Notify listener on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> listener.onFoodListReceived(foodResObjs));
                }
            }
        });
    }
}