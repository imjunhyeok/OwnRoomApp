package com.example.jun.httpcmm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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
    static public UserInfo userInfo = null;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        intent = new Intent(this, RealChatActivity.class);
        // 로그인 버튼
        btnSgnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usrid = editTxtUsrId.getText().toString();
                final String usrpwd = editTxtUsrPwd.getText().toString();
                new RestfulGetAPI(new OnCompletionListener() {
                    @Override
                    public void onComplete(String result) {
                        userInfo = new Gson().fromJson(result, UserInfo.class);
                        if(userInfo != null){
                            // 로그인 성공
                            if(usrpwd.equals(userInfo.getUsrpwd())){
                                Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                                Log.v("login","success");
                                startActivity(intent);
                            }
                            // 로그인 실패
                            else
                                Log.v("login","failed");
                        }
                        else
                            Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).execute("http://192.168.1.5:3000/user/info?usrid=" + usrid);
            }
        }); //btnSgnIn

        // 회원가입 버튼
        btnSgnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_reg, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();

                final EditText editTxtRegUsrId = (EditText) dialogView.findViewById(R.id.editTxtRegUsrId);
                final EditText editTxtRegUsrPwd = (EditText) dialogView.findViewById(R.id.editTxtRegUsrPwd);
                final EditText editTxtRegUsrName = (EditText) dialogView.findViewById(R.id.editTxtRegUsrName);
                Button btnRegCom = (Button) dialogView.findViewById(R.id.btnRegCom);
                btnRegCom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String regUsrId = "\""+ editTxtRegUsrId.getText().toString() + "\"";
                        String regUsrPwd = "\"" + editTxtRegUsrPwd.getText().toString() + "\"";
                        String regUsrName = "\"" + editTxtRegUsrName.getText().toString() + "\"";
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        String regUsrDate = "\"" + sdf.format(calendar.getTime()) + "\"";
                        String param = "{\"usrid\" : "+ regUsrId +", \"usrpwd\" : "+ regUsrPwd+ ", \"usrname\" : "+ regUsrName + ", \"usrregdate\" : "+ regUsrDate + "}";
                        new RestfulPostAPI(new OnCompletionListener() {
                            @Override
                            public void onComplete(String result) {
                            }
                        }).execute("http://192.168.1.5:3000/user/updateUserInfo", param);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
}
