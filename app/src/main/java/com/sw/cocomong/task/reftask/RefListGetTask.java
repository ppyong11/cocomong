package com.sw.cocomong.task.reftask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cocomong.Object.FoodObj;
import com.sw.cocomong.Object.RefObj;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RefListGetTask {


    ArrayList<RefObj> reflist=new ArrayList<>();
    public RefListGetTask(String username) throws JSONException {

        String url = "http://58.224.91.191:8080/ref/list/"+username;

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
                    //응답 실패
                    Log.i("tag", "응답실패");
                }else{
                    //응답성공
                    final String responseData = response.body().string();
                    Log.i("tag", "응답성공"+responseData);
                }
            }
        });

    }
    public ArrayList<RefObj> getList(){
        return reflist;
    }
}
