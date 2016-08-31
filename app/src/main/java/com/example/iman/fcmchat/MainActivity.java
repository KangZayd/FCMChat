package com.example.iman.fcmchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    public static final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    private String url = "http://192.168.2.160:8000/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("MainActivity", FirebaseInstanceId.getInstance().getToken());
        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendRegistrationToServer(final String refreshedToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String requestBody = "{\"hash\":" + "\"" + refreshedToken +
                        "\"}e";
                RequestBody body = RequestBody.create(mediaType, requestBody);
                Log.d("MainActivity", requestBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
