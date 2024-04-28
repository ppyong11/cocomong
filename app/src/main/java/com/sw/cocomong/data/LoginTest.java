package com.sw.cocomong.data;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginTest {

}
class Test {

    static final String LOGIN_URL ="http://172.25.16.1:8080/join";


        // OKHttp 객체 생성
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        //MediaType mediaType = MediaType.parse("text/plain");

        // RequestBody 생성
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username","test")
                .addFormDataPart("password","1234")
                .build();

        // Post 객체 생성
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

    Test() throws IOException {

    }
}
