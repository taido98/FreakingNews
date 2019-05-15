package com.example.naviapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {
    private String name, email, url_avatar, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e) {

                } finally {
                    Intent i = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }


}
