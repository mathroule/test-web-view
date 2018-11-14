package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
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

    private final OkHttpClient okHttpClient;

    public OkHttpClientWebViewClient() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .followRedirects(false)
                .build();
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        final String method = request.getMethod();
        final String url = request.getUrl().toString();
        return interceptRequest(view, method, url);
    }

    @Nullable
    private WebResourceResponse interceptRequest(@NonNull final WebView view, @NonNull final String method, @NonNull final String url) {
        Timber.d("Starting to load %s %s", method, url);

        final Request httpRequest = new Request.Builder()
                .url(url)
                .method(method, null)
                .build();

        try {
            final Response httpResponse = okHttpClient.newCall(httpRequest).execute();

            final int statusCode = httpResponse.code();
            if (isRedirection(statusCode)) {
                final String redirectUrl = httpResponse.header("Location") != null
                        ? httpResponse.header("Location")
                        : httpResponse.header("location");

                if (redirectUrl != null) {
                    Timber.d("Redirecting from %s to %s", url, redirectUrl);
                    // TODO replace with WebResourceRequest.isRedirect()
                    if (url.equals(getLastUrl())) {
                        redirectTo(view, redirectUrl);
                    } else {
                        Timber.d("Proxying request from %s %s to %s %s", method, url, method, redirectUrl);
                        return interceptRequest(view, method, redirectUrl);
                    }
                } else {
                    Timber.e("Error while doing redirection. Location is unknown");
                }

                return null;
            }

            return toWebResourceResponse(httpResponse);
        } catch (IOException e) {
            Timber.e(e, "Error while loading %s %s", method, url);
        }

        Timber.d("Done loading %s %s", method, url);

        return null;
    }

    @NonNull
    private static WebResourceResponse toWebResourceResponse(@NonNull final Response response) {
        final String contentType = response.header("content-type");
        final String mimeType = getMimeType(contentType);
        final String encoding = getEncoding(contentType);
        final int statusCode = response.code();
        final String message = response.message();
        final String reasonPhrase = TextUtils.isEmpty(message) ? "unknown" : message;
        final Map<String, String> responseHeaders = toHeaders(response.headers().toMultimap());
        final ResponseBody body = response.body();
        final InputStream data = body != null ? body.byteStream() : null;

        Timber.d("New web resources contentType: %s, mimeType: %s, encoding: %s, statusCode: %d, reasonPhrase: %s, responseHeaders: %s ", contentType, mimeType, encoding, statusCode, reasonPhrase, responseHeaders);

        return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, responseHeaders, data);
    }

    @NonNull
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
