package com.sw.cocomong.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;


import com.sw.cocomong.dto.BarcodeResDto;
import com.sw.cocomong.dto.FoodListItemDto;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FoodAddTask extends AsyncTask<String, Void, BarcodeResDto> {
    String sendMsg, receiveMsg;
    FoodListItemDto foodListItemDto;
    HttpURLConnection conn;

    String foodname, refNum, expiredate, category, memo;
    public FoodAddTask(){

    }
    @Override
    protected BarcodeResDto doInBackground(String... strings) {
        String str;
        String barcodeNum = strings[0];

        try {
            sendMsg="http://58.224.91.191:8080/foods/post";
            URL url = new URL(sendMsg);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            this.conn=conn;
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            /*{
                ”id” : id
                “foodname” : “식재료 이름”
                ”ref_num” : 냉장고 번호
                “expiredate” : “유통기한”
                “category” : “카테고리”
                “memo” : “메모”
                }

             */
            if(conn.getResponseCode() == conn.HTTP_OK) {

                try {
                String jsonString = "{\"id\": 1, \"foodname\": \"식재료 이름\", \"ref_num\": 123, \"expiredate\": \"유통기한\", \"category\": \"카테고리\", \"memo\": \"메모\"}";
                InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                // 응답처리
                // JsonReader jsonReader = new JsonReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                JsonReader jsonReader = new JsonReader(inputStreamReader);
// JSON 객체로 파싱
                JSONTokener tokener = new JSONTokener(inputStreamReader.getEncoding());
                JSONObject jsonObject = new JSONObject(tokener);

                // 값 추출
                int id = jsonObject.getInt("id");
                String foodname = jsonObject.getString("foodname");
                int refNum = jsonObject.getInt("ref_num");
                String expiredate = jsonObject.getString("expiredate");
                String category = jsonObject.getString("category");
                String memo = jsonObject.getString("memo");

                // 테스트를 위해 값 출력
                System.out.println("id: " + id);
                System.out.println("foodname: " + foodname);
                System.out.println("ref_num: " + refNum);
                System.out.println("expiredate: " + expiredate);
                System.out.println("category: " + category);
                System.out.println("memo: " + memo);
                }catch (Exception e){
                     e.printStackTrace();
                }

            }

            else {    // 통신이 실패한 이유를 찍기위한 로그
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(conn!=null){
                conn.disconnect();
                System.out.println("---------conn.disconeect()-------");
            }
        }
        //foodListItemDto=new FoodListItemDto( ,foodname,refNum, expiredate, category, memo);
        //return foodListItemDto;
        return null;
    }
    public int getResponseCode() throws IOException {
        return conn.getResponseCode();
    }
}
