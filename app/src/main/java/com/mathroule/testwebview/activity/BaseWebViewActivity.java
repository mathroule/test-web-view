package com.mathroule.testwebview.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mathroule.testwebview.R;

public abstract class BaseWebViewActivity extends AppCompatActivity {

    private static final String URL = "https://www.google.com";

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webView);

        webView.clearCache(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearHistory();

        webView.setWebViewClient(getWebClient());
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(URL);
    }

    @NonNull
    abstract WebViewClient getWebClient();
}