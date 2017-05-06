package com.tanya.task_11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wv = (WebView) findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
//        wv.getSettings().setDomStorageEnabled(true);
        wv.loadUrl("http://oauth.vk.com/authorize?client_id=6019074&v=5.7&scope=wall,offline&" +
                "response_type=token");
    }
}
