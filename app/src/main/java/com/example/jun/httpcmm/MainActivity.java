package com.example.jun.httpcmm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    TextView txtView;
    JSONArray usrInfoArr;
    UserInfo [] usrInfo = new UserInfo[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView)findViewById(R.id.txtView);

        new RestfulGetAPI(new OnCompletionListener() {
            @Override
            public void onComplete(String result) {
                Log.v("result : ", result);

                try {
                    usrInfoArr = new JSONArray(result);
                    usrInfo = new Gson().fromJson(result, UserInfo[].class);
                    /*
                    for (int idx = 0; idx < usrInfoArr.length(); idx++){
                        JSONObject usrInfoObj = usrInfoArr.getJSONObject(idx);
                        String usrInfo = usrInfoObj.toString();
                        usrInfo[idx] = new Gson().fromJson(usrInfo,UserInfo.class);

                    }
                    */
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                txtView.setText(result);
            }
        }).execute("http://192.168.0.6:3000/user/info");
    }
}
