package com.sw.cocomong.task;

import android.os.AsyncTask;

import com.sw.cocomong.dto.BarcodeDto;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class BarcodeTask extends AsyncTask<BarcodeDto, Void, String> {
    private static final String BARCOD_API_KEY="38dbcef5223a4a4ebd09";

    // TODO: 2024-05-03 try-catch문 더러운거 치우기
    @Override
    protected String doInBackground(BarcodeDto... barcodeDtos) {
        URL url = null;
        BarcodeDto dto = barcodeDtos[0];
        try {
            // /{keyId}/{serviceId}/{dataType}/{startIdx}/{endIdx}
            String str;
            url = new URL("http://openapi.foodsafetykorea.go.kr/api/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(dto.toUrlString());
            osw.flush();        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
