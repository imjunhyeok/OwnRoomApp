package com.example.jun.httpcmm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 3330MT on 2017-05-31.
 */

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.editTxtUsrId) EditText editTxtUsrId;
    @Bind(R.id.editTxtUsrPwd) EditText editTxtUsrPwd;
    @Bind(R.id.btnSgnIn) Button btnSgnIn;
    @Bind(R.id.btnSgnUp) Button btnSgnUp;
    static UserInfo userInfo[] = new UserInfo[1];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnSgnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usrid = editTxtUsrId.getText().toString();
                final String usrpwd = editTxtUsrPwd.getText().toString();
                new RestfulGetAPI(new OnCompletionListener() {
                    @Override
                    public void onComplete(String result) {
                        userInfo = new Gson().fromJson(result, UserInfo[].class);
                        Toast.makeText(getApplicationContext(),userInfo[0].getUsrid(), Toast.LENGTH_SHORT).show();
                        if(usrpwd.equals(userInfo[0].getUsrpwd()))
                            Log.v("login","success");
                        else
                            Log.v("login","failed");
                    }
                }).execute("http://192.168.0.6:3000/user/signin?usrid=" + usrid);
            }
        }); //btnSgnIn
    }
}
