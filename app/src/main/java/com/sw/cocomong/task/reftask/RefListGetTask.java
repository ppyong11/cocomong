package com.sw.cocomong.task.reftask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String TAG = "RefListGetTask";
    private List<RefObj> refList;
    private final RefListGetTaskListener listener;

    public interface RefListGetTaskListener {
        void onRefListReceived(List<RefObj> refList);
    }

    public RefListGetTask(String username, RefListGetTaskListener listener) throws JSONException {
        this.listener = listener;
        //String url = "http://58.224.91.191:8080/ref/list/" + username;
        //String url = "http://192.168.236.1:8080/ref/list/"+username;  // dahee laptop
        String url = "http://192.168.219.144:8080/ref/list/"+username; //아현

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "응답실패");
                } else {
                    final String responseData = response.body().string();
                    Log.i(TAG, "응답성공" + responseData);
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        refList = objectMapper.readValue(responseData, new TypeReference<List<RefObj>>() {});
                        Log.i("tag", "refList" + refList.toString());

                        // Notify listener on the main thread
                        new Handler(Looper.getMainLooper()).post(() -> listener.onRefListReceived(refList));
                    } catch (IOException e) {
                        Log.e(TAG, "JSON parsing error", e);
                    }
                }
            }
        });
    }
}