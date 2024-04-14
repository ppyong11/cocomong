package com.sw.cocomong.data;

import android.app.DownloadManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginTest {
    private static final String LOGIN_URL ="http://172.25.16.1:8080/join";

    URL url = new URL(LOGIN_URL);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


    public LoginTest() throws IOException {
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
    }
}
class Test{
    static final String LOGIN_URL ="http://172.25.16.1:8080/join";

    public static void main(String[] args) throws IOException {

        URL url = new URL(LOGIN_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        //POST 데이터 설정
        String postData="username=dahee&password=0111";

        OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
        //POST 데이터 전송
        osw.write(postData);
        osw.flush();
    }
}

