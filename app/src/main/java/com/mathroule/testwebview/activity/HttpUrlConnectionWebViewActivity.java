package com.mathroule.testwebview.activity;

import android.support.annotation.NonNull;
import android.webkit.WebViewClient;

import com.mathroule.testwebview.web.client.HttpURLConnectionWebViewClient;

public class HttpUrlConnectionWebViewActivity extends BaseWebViewActivity {

    @NonNull
    @Override
    WebViewClient getWebClient() {
        return new HttpURLConnectionWebViewClient();
    }
}
