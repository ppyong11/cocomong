package com.sw.cocomong.task.reftask;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RefDeleteTask {
    public RefDeleteTask(String refnum, String username) throws JSONException {

        //String url = "http://192.168.236.1:8080/ref/"+refnum;  // dahee laptop
        String url = "http://172.29.164.224:8080/ref/"+refnum;

        //String url = "http://192.168.219.144:8080/ref/"+refnum; //아현
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).delete().build();
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
                }
            }
        });

    }
}
