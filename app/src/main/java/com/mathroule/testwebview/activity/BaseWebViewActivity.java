package com.mathroule.testwebview.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.good.gd.GDAndroid;
import com.mathroule.testwebview.R;

public abstract class BaseWebViewActivity extends AppCompatActivity {

    private static final String URL = "https://www.google.com";
    // private static final String URL = "https://github.com";
    // private static final String URL = "https://httpbin.org/redirect-to?url=https://www.google.com";

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GDAndroid.getInstance().activityInit(this);

        setContentView(getLayout());

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

    @LayoutRes
    int getLayout() {
        return R.layout.activity_web_view;
    }

    @NonNull
    abstract WebViewClient getWebClient();
}
