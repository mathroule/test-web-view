package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

public class OkHttpClientWebViewClient extends BaseWebViewClient {

    private final OkHttpClient client;

    public OkHttpClientWebViewClient() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        final String url = request.getUrl().toString();
        final String method = request.getMethod();
        Timber.d("Starting to load %s %s", method, url);

        final Request httpRequest = new Request.Builder()
                .url(url)
                .method(method, null)
                .build();

        try {
            return toWebResourceResponse(client.newCall(httpRequest).execute());
        } catch (IOException e) {
            Timber.e(e, "Error while loading %s %s", method, url);
        }

        Timber.d("Done loading %s %s", method, url);

        return super.shouldInterceptRequest(view, request);
    }

    private static WebResourceResponse toWebResourceResponse(@NonNull final Response response) {
        final String mimeType = response.header("content-type");
        final int statusCode = response.code();
        final String reasonPhrase = TextUtils.isEmpty(response.message()) ? "unknown" : response.message();
        final Map<String, String> responseHeaders = toHeaders(response.headers().toMultimap());
        final ResponseBody body = response.body();
        final InputStream data = body != null ? body.byteStream() : null;

        return new WebResourceResponse(mimeType, Charset.defaultCharset().displayName(), statusCode, reasonPhrase, responseHeaders, data);
    }

    private static Map<String, String> toHeaders(@NonNull final Map<String, List<String>> headers) {
        final Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            final List<String> headerValues = entry.getValue();
            String headerValue = TextUtils.join("; ", headerValues);
            result.put(entry.getKey(), headerValue);
        }

        return result;
    }
}
