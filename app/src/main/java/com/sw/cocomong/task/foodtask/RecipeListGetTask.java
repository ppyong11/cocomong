package com.sw.cocomong.task.foodtask;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.Object.RecipeObj;
import com.sw.cocomong.Object.RefObj;

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

public class RecipeListGetTask {

    private List<RecipeObj> recipeList;
    private final RecipeListGetTaskListener listener;

    public interface RecipeListGetTaskListener{
        void onRecipeListReceived(List<RecipeObj> recipeObjs);
    }


    public RecipeListGetTask(String username, String refname, RecipeListGetTaskListener listener) throws JSONException, IOException {
        this.listener = listener;

        //Log.i("tag","실행됨");
        //String url = "http://58.224.91.191:8080/recipe/get";
        //String url = "http://121.181.25.225:8080/recipe/get";
        //String url = "http://192.168.236.1:8080/recipe/get";  // dahee laptop
        String url = "http://118.139.210.1:8080/recipe/get";  // chae laptop


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
                if (!response.isSuccessful()) {
                    //응답실패
                    Log.i("RecipeListGetTask", "응답실패");
                } else {
                    //응답성공
                    final String responseData = response.body().string();
                    Log.i("RecipeListGetTask", "응답성공"+responseData);
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        recipeList = objectMapper.readValue(responseData, new TypeReference<List<RecipeObj>>() {});

                        // Notify listener on the main thread
                        new Handler(Looper.getMainLooper()).post(() -> listener.onRecipeListReceived(recipeList));

                    } catch (IOException e) {
                        Log.e("tag", "JSON parsing error", e);
                    }}


            }
        });

    }
}