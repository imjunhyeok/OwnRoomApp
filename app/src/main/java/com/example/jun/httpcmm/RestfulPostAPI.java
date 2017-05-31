package com.example.jun.httpcmm;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 3330MT on 2017-05-31.
 */

// AsyncTask<param, progress, result>
// param : background 작업 시 필요한 데이터 타입 저장
// progress : background 작업 중 진행 상황을 표현하는데 사용되는 데이터 타입 지정
// result : 작업의 결과로 리턴 할 데이터의 타입 지정
public class RestfulPostAPI extends AsyncTask<String, Void, String>{
    OnCompletionListener listener = null;

    public RestfulPostAPI(OnCompletionListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlStr = params[0];
        URL url = null;
        String xml = "";

        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 서버 Response Data를 JSON 형식의 타입으로 요청
            conn.setRequestProperty("Accept", "application/json");
            // 타입설정(text/html) 형식으로 전송 (Request Body 전달 시 application/json로 서버에 전달)
            conn.setRequestProperty("Content-type", "application/json");
            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
            conn.setDoOutput(true);
            // InputStream으로 서버로부터 응답을 받겠다는 옵션
            conn.setDoInput(true);

            OutputStream os = conn.getOutputStream();
            os.write(params[1].getBytes("euc-kr"));
            os.flush();
            os.close();

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
