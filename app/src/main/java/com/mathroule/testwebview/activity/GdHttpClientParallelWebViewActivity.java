package com.mathroule.testwebview.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.webkit.WebViewClient;

import com.mathroule.testwebview.R;
import com.mathroule.testwebview.web.client.GdHttpClientParallelWebViewClient;

public class GdHttpClientParallelWebViewActivity extends BaseWebViewActivity {

    @LayoutRes
    @Override
    int getLayout() {
        return R.layout.activity_gd_web_view;
    }

    @NonNull
    @Override
    WebViewClient getWebClient() {
        return new GdHttpClientParallelWebViewClient();
    }
}
