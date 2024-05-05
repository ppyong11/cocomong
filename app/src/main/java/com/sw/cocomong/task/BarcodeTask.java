package com.sw.cocomong.task;

import android.os.AsyncTask;
import android.util.Log;


import com.sw.cocomong.dto.BarcodeResDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BarcodeTask extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    BarcodeResDto barcodeResDto;
    private static final String BARCODE_API_KEY="38dbcef5223a4a4ebd09";
    private static final String SERVICED_ID_CATEGORY="I2570";  // 유통바코드 서비스(카테고리)
    private static final String SERVICED_ID_EXPIRE="C005";  // 바코드연계제품정보 서비스(유통기한)
    private static final String DATA_TYPE="json";  // 요청파일타입
    private static int START_IDX=1;  // 요청시작위치
    private static int END_IDX=1;  // 요청종료위치
    private String barcodeNum;
    HttpURLConnection conn;
    public BarcodeTask(String barcodeNum){
        this.barcodeNum = barcodeNum;
    }
    @Override
    protected String doInBackground(String... strings) {
        String str;
        String barcodeNum = strings[0];
        try {
            // http://openapi.foodsafetykorea.go.kr/api/keyId/serviceId/dataType/startIdx/endIdx/PRDT_NM=값&BRCD_NO=값
            sendMsg="http://openapi.foodsafetykorea.go.kr/api"+setExpireUrl();
            //sendMsg = "https://openapi.foodsafetykorea.go.kr/api/38dbcef5223a4a4ebd09/I2570/json/1/5/BRCD_NO=8801382124849";
            URL url = new URL(sendMsg);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            this.conn=conn;
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            if(conn.getResponseCode() == conn.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null ) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {    // 통신이 실패한 이유를 찍기위한 로그
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // TODO: 2024-05-04 필요한 인자값 저장하기.

        try {
            JSONArray jsonArray=new JSONArray(receiveMsg);

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String productName = jsonObject.getString("PRDLST_NM");
                String dayCount = jsonObject.getString("POG_DAYCNT");
                String category = jsonObject.getString("PRDLST_DCNM");

                barcodeResDto.setProductName(productName);
                barcodeResDto.setDayCount(dayCount);
                barcodeResDto.setCategory(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }
    public int getResponseCode() throws IOException {
        return conn.getResponseCode();
    }
    public String setCategoryUrl(){
        return  "/" + BARCODE_API_KEY +
                "/" + SERVICED_ID_CATEGORY +
                "/" + DATA_TYPE +
                "/" + START_IDX +
                "/" + END_IDX +
                "/" + "BRCD_NO=" + barcodeNum;
    }
    public String setExpireUrl(){
        return  "/" + BARCODE_API_KEY +
                "/" + SERVICED_ID_CATEGORY +
                "/" + DATA_TYPE +
                "/" + START_IDX +
                "/" + END_IDX +
                "/" + "BAR_CD=" + barcodeNum;
    }
}
