package com.sw.cocomong.task.foodtask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.FoodResObj;
import com.sw.cocomong.R;

import org.checkerframework.checker.units.qual.C;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.ByteString;

public class FoodEditTask {
    FoodResObj foodResObj;

    private final FoodEditTaskListener listener;
    public interface FoodEditTaskListener{
        void onFoodEditReceived(FoodResObj foodResObj);
    }


    public FoodEditTask(FoodResObj foodResObj, FoodEditTaskListener listener) throws JSONException, IOException {
        this.listener = listener;
        String url = "http://192.168.236.1:8080/foods/"+foodResObj.getIdx();  // dahee laptop

        //request 작성
        OkHttpClient client = new OkHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();
        String foodObjJson = objectMapper.writeValueAsString(foodResObj);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),foodObjJson);
        Log.i("tag",requestBody.toString());

        Request request = new Request.Builder().url(url).put(requestBody).build();
        Log.i("tag",request.toString());
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
                }else {
                    //응답성공
                    final String responseData = response.body().string();
                    Log.i("tag", "응답성공" + responseData);
                    new Handler(Looper.getMainLooper()).post(()->listener.onFoodEditReceived(foodResObj));
                }
            }
        });
    }
}
