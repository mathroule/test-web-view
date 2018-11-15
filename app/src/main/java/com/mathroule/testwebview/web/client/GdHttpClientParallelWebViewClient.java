package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;

import com.good.gd.apache.http.impl.client.DefaultHttpClient;
import com.good.gd.net.GDHttpClient;

public class GdHttpClientParallelWebViewClient extends BaseApacheHttpClientWebViewClient {

    @NonNull
    @Override
    DefaultHttpClient createHttpClient() {
        return new GDHttpClient();
    }
}
