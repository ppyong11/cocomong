package com.sw.cocomong.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sw.cocomong.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    Button join_btn;

    EditText id_edit;
    EditText pw_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        join_btn = (Button)findViewById(R.id.btn_join);
        login_btn =(Button) findViewById(R.id.btn_login);

        id_edit = (EditText)findViewById(R.id.editTextTextPersonName2);
        pw_edit = (EditText)findViewById(R.id.editTextTextPassword3);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join:
                join();
                Intent intent = new Intent(LoginActivity.this,);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                Intent intent2 = new Intent(LoginActivity.this,);
                startActivity(intent2);
                finish();
                break;
        }
    }

    void join(){
        Log.w("join", "join");
        try{
            String id = id_edit.getText().toString();
            String pw = pw_edit.getText().toString();

            CustomTask task = new CustomTask();
            String result = task.execute(id, pw).get();

            Intent intent2 = new Intent(LoginActivity.this);
            startActivity(intent2);
            finish();
        }catch (Exception e){

        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://58.224.91.191:8080/join");  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                // sendMsg = "id=" + strings[0] + "&pw=" + strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                sendMsg = "id=" +strings[0]+ "&pw=" +strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";

                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
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
}
