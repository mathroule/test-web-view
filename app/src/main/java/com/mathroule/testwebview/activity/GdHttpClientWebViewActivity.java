package com.mathroule.testwebview.activity;

import android.support.annotation.NonNull;
import android.webkit.WebViewClient;

import com.good.gd.GDAndroid;
import com.mathroule.testwebview.R;
import com.mathroule.testwebview.web.client.GdHttpClientWebViewClient;

public class GdHttpClientWebViewActivity extends BaseWebViewActivity {

    @Override
    void initContentView() {
        GDAndroid.getInstance().activityInit(this);

        setContentView(R.layout.activity_gd_web_view);
    }

    @NonNull
    @Override
    WebViewClient getWebClient() {
        return new GdHttpClientWebViewClient();
    }
}
