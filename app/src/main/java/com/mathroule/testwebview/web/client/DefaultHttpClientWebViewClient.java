package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;

import com.good.gd.apache.http.impl.client.DefaultHttpClient;

public class DefaultHttpClientWebViewClient extends BaseApacheHttpClientWebViewClient {

    @NonNull
    @Override
    DefaultHttpClient createHttpClient() {
        return new DefaultHttpClient();
    }
}
