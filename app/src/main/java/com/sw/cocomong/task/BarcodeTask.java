package com.sw.cocomong.task;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;


import com.sw.cocomong.dto.BarcodeResDto;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BarcodeTask extends AsyncTask<String, Void, BarcodeResDto> {
    String sendMsg, receiveMsg;
    BarcodeResDto barcodeResDto;
    private static final String BARCODE_API_KEY="38dbcef5223a4a4ebd09";
    private static final String SERVICED_ID_CATEGORY="I2570";  // 유통바코드 서비스(카테고리)
    private static final String SERVICED_ID_EXPIRE="C005";  // 바코드연계제품정보 서비스(유통기한)
    private static final String DATA_TYPE="xml";  // 요청파일타입
    private static int START_IDX=1;  // 요청시작위치
    private static int END_IDX=1;  // 요청종료위치
    private String barcodeNum;
    HttpURLConnection conn;
    String tagName="";
    String product="";
    String pog_daycnt="";
    String category="";
    String resultCode="";
    String resultMessage="";

    public BarcodeTask(String barcodeNum){
        // 코카콜라 바코드 번호 = "8801094013004"
        this.barcodeNum = barcodeNum;
    }
    @Override
    protected BarcodeResDto doInBackground(String... strings) {
        String str;
        String barcodeNum = strings[0];

        try {
            sendMsg="http://openapi.foodsafetykorea.go.kr/api"+setExpireUrl();

            URL url = new URL(sendMsg);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            this.conn=conn;
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            // 응답처리
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            if(conn.getResponseCode() == conn.HTTP_OK) {
                int eventType = parser.getEventType();

                try {
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                tagName = parser.getName();
                                switch (tagName) {
                                    case "PRDLST_NM":
                                        parser.next();
                                        product = parser.getText() != null ? parser.getText() : "";
                                        break;
                                    case "POG_DAYCNT":
                                        parser.next();
                                        pog_daycnt = parser.getText() != null ? parser.getText() : "";
                                        break;
                                    case "PRDLST_DCNM":
                                        parser.next();
                                        category = parser.getText() != null ? parser.getText() : "";
                                        break;
                                    case "CODE":
                                        parser.next();
                                        resultCode = parser.getText() != null ? parser.getText() : "";
                                        break;
                                    case "MSG":
                                        parser.next();
                                        resultMessage = parser.getText() != null ? parser.getText() : "";
                                        break;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {    // 통신이 실패한 이유를 찍기위한 로그
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;

        } finally {
            if(conn!=null){
                conn.disconnect();
                System.out.println("---------conn.disconeect()-------");
            }
        }
        barcodeResDto=new BarcodeResDto(product, pog_daycnt, category);
        return barcodeResDto;
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
                "/" + SERVICED_ID_EXPIRE +
                "/" + DATA_TYPE +
                "/" + START_IDX +
                "/" + END_IDX +
                "/" + "BAR_CD=" + barcodeNum;
    }
}
