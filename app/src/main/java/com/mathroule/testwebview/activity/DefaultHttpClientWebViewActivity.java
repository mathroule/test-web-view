package com.mathroule.testwebview.activity;

import android.support.annotation.NonNull;
import android.webkit.WebViewClient;

import com.mathroule.testwebview.web.client.DefaultHttpClientWebViewClient;

public class DefaultHttpClientWebViewActivity extends BaseWebViewActivity {

    @NonNull
    @Override
    WebViewClient getWebClient() {
        return new DefaultHttpClientWebViewClient();
    }
}
