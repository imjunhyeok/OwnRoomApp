package com.example.jun.httpcmm;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jun on 2017-05-30.
 */
interface  OnCompletionListener {
    void onComplete(String result);
}

// AsyncTask<param, progress, result>
// param : background 작업 시 필요한 데이터 타입 저장
// progress : background 작업 중 진행 상황을 표현하는데 사용되는 데이터 타입 지정
// result : 작업의 결과로 리턴 할 데이터의 타입 지정
public class RestfulGetAPI extends AsyncTask<String, Void, String> {
    OnCompletionListener listener = null;

    public RestfulGetAPI(OnCompletionListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlStr = params[0];
        URL url = null;
        String xml  = "";
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 서버 Response Data를 JSON 형식의 타입으로 요청
            //conn.setRequestProperty("Accept", "application/json");

            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].split("=");
                conn.setRequestProperty(pair[0], pair[1]);
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while((line = reader.readLine()) != null){
                xml += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null)
            listener.onComplete(result);
    }
}
