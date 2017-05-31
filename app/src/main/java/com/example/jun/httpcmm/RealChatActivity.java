package com.example.jun.httpcmm;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.example.jun.httpcmm.LoginActivity.userInfo;

/**
 * Created by jun on 2017-05-31.
 */

public class RealChatActivity extends Activity {
    @Bind(R.id.sendMessageBtn) Button sendMessageBtn;
    @Bind(R.id.sendMessageEdit) EditText sendMessageEdit;
    @Bind(R.id.chatContentView) TextView chatContentView;
    String msg;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realchat);
        ButterKnife.bind(this);

        try{
            mSocket = IO.socket("http://192.168.1.5:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 리스너 등록(서버에서 보낸 이벤트를 받아오는 리스너를 string으로 등록
        mSocket.on("toclient", toclient);
        mSocket.connect();
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatContentView.setText( chatContentView.getText().toString() + "\n" + userInfo.getUsrid() + ":" + sendMessageEdit.getText().toString());
                sendMessage();
            }
        });

    }

    public void sendMessage() {
        JSONObject obj = new JSONObject();
        try{
            String msg = userInfo.getUsrname() + ":" + sendMessageEdit.getText().toString();
            obj.put("msg", msg);
            mSocket.emit("fromclient", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 웹 소켓 보냄
        sendMessageEdit.setText("");
    }

    @Override
    public void onBackPressed() {
        mSocket.disconnect();
        super.onBackPressed();
    }

    private Emitter.Listener toclient = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 서버에서 보낸 JSON객체를 사용
            final JSONObject obj = (JSONObject)args[0];
            try {
                msg = obj.get("msg").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatContentView.setText(chatContentView.getText().toString() + "\n" + msg );
                }
            });
        }
    };
}
