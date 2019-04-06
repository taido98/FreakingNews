package com.example.naviapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String linkPath = intent.getStringExtra("link");
        webView.loadUrl(linkPath);
        webView.setWebViewClient(new WebViewClient());
    }
}
