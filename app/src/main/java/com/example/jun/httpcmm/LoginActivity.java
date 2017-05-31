package com.example.jun.httpcmm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 3330MT on 2017-05-31.
 */

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.usrId) EditText usrId;
    @Bind(R.id.usrPwd) EditText usrPwd;
    @Bind(R.id.btnSgnIn) Button btnSgnIn;
    @Bind(R.id.btnSgnUp) Button btnSgnUp;
    static UserInfo userInfo = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnSgnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strUsrId = usrId.getText().toString();
                final String strUsrPwd = usrPwd.getText().toString();
            }
        });
    }
}
