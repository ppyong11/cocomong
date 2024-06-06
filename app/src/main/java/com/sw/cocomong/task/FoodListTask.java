/*package com.sw.cocomong.task;

import android.os.AsyncTask;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FoodListTask extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    String refName;
    HttpURLConnection conn;
    public FoodListTask(String refName){
        this.refName=refName;
    }

    @Override
    // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
    protected String doInBackground(String... strings) {
        try {
            String str;
            //URL url = new URL("http://58.224.91.191:8080/join");  // 어떤 서버에 요청할지(localhost 안됨.)
            //URL url = new URL("http://121.181.25.225:8080/join");
            URL url = new URL("http://121.150.122.246:8080/join");  // dahee laptop

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            this.conn=conn;

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            // 서버에 보낼 값 포함해 요청함.
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "username=" + strings[0] + "&password=" + strings[1];

            osw.write(sendMsg);
            osw.flush();

            // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {    // 통신이 실패한 이유를 찍기위한 로그
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 서버에서 보낸 값을 리턴합니다.
        return receiveMsg;
    }
}
*/