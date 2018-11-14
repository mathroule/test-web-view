package com.mathroule.testwebview.web.client;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.nio.charset.Charset;

import timber.log.Timber;

public abstract class BaseWebViewClient extends WebViewClient {

    private String lastUrl;

    private long startTime;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Timber.d("shouldOverrideUrlLoading request: %s %s", request.getMethod(), request.getUrl());
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        lastUrl = url;
        startTime = System.currentTimeMillis();
        Timber.d("onPageStarted url: %s", url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        final long elapsedTime = System.currentTimeMillis() - startTime;
        Timber.d("onPageFinished url: %s in %d ms", url, elapsedTime);
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

    static void redirectTo(@NonNull final WebView view, @NonNull final String url) {
        Timber.d("Redirecting web view to %s", url);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.loadUrl(url);
            }
        });
    }

    @Nullable
    static String getMimeType(@Nullable final String contentType) {
        return contentType != null && contentType.startsWith("text/html")
                ? "text/html"
                : contentType;
    }

    @NonNull
    static String getEncoding(@Nullable final String contentType) {
        return contentType != null && contentType.contains("charset=")
                ? contentType.substring(contentType.lastIndexOf("charset=") + 8, contentType.length())
                : Charset.defaultCharset().displayName();
    }

    static boolean isRedirection(int statusCode) {
        return 300 <= statusCode && statusCode <= 399;
    }

    @Nullable
    String getLastUrl() {
        return lastUrl;
    }
}
