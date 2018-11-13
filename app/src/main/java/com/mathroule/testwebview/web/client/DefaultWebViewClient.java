package com.mathroule.testwebview.web.client;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import timber.log.Timber;

public class DefaultWebViewClient extends WebViewClient {

    long startTime;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Timber.d("shouldOverrideUrlLoading request: %s %s", request.getMethod(), request.getUrl());
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        startTime = System.currentTimeMillis();
        Timber.d("onPageStarted url: %s", url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        long elaspedTime = System.currentTimeMillis() - startTime;
        Timber.d("onPageFinished url: %s in %d ms", url, elaspedTime);
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        Timber.d("onLoadResource url: %s", url);
        super.onLoadResource(view, url);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Timber.d("onReceivedSslError handler: %s error: %s", handler, error);
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        Timber.d("onReceivedClientCertRequest request: %s:%s", request.getHost(), request.getPort());
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        Timber.d("onReceivedHttpAuthRequest handler: %s host: %s realm: %s", handler, host, realm);
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }
}
