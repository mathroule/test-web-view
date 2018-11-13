package com.mathroule.testwebview.activity;

import android.support.annotation.NonNull;
import android.webkit.WebViewClient;

import com.mathroule.testwebview.activity.BaseWebViewActivity;

public class OkHttpClientWebViewActivity extends BaseWebViewActivity {

    @NonNull
    @Override
    WebViewClient getWebClient() {
        return new WebViewClient();
    }
}
