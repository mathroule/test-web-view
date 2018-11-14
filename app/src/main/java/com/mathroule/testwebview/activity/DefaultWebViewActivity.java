package com.mathroule.testwebview.activity;

import android.support.annotation.NonNull;
import android.webkit.WebViewClient;

import com.mathroule.testwebview.web.client.DefaultWebViewClient;

public class DefaultWebViewActivity extends BaseWebViewActivity {

    @NonNull
    @Override
    WebViewClient getWebClient() {
        return new DefaultWebViewClient();
    }
}
