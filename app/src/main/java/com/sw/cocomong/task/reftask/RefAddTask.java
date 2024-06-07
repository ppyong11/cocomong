package com.sw.cocomong.task.reftask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.sw.cocomong.Object.RefObj;
import com.sw.cocomong.R;

import org.checkerframework.checker.units.qual.C;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RefAddTask {

    String url = "http:/192.168.236.1:8080/ref/create";  //dahee laptop
    //String url = "http://192.168.219.144:8080/ref/create"; //아현
    public RefAddTask(String refname, String username) throws JSONException {
        JSONObject data = new JSONObject();

        data.put("username",username);
        data.put("refname",refname);

        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), data.toString());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    Log.i("tag","response fail");
                }else{
                    Log.i("tag","response success");
                    final String responseData=response.body().string();
                    Log.i("tag",responseData);
                }
            }
        });
    }
}
