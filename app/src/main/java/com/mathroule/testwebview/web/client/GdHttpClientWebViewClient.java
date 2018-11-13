package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.apache.http.client.methods.HttpUriRequest;
import com.good.gd.net.GDHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class GdHttpClientWebViewClient extends BaseWebViewClient {

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        final GDHttpClient client = new GDHttpClient();

        final String url = request.getUrl().toString();
        final String method = request.getMethod();
        Timber.d("Starting to load %s %s", method, url);

        final HttpUriRequest httpRequest = new HttpGet(url);

        try {
            return toWebResourceResponse(client.execute(httpRequest));
        } catch (IOException e) {
            Timber.e(e, "Error while loading %s %s", method, url);
        } finally {
            // TODO move in close method of input stream
            // client.getConnectionManager().shutdown();
        }

        Timber.d("Done loading %s %s", method, url);

        return super.shouldInterceptRequest(view, request);
    }

    @NonNull
    private static WebResourceResponse toWebResourceResponse(@NonNull final HttpResponse response) {
        final Header firstHeader = response.getFirstHeader("content-type");
        final String contentType = firstHeader != null ? firstHeader.getValue() : null;
        final String mimeType = getMimeType(contentType);
        final String encoding = getEncoding(contentType);
        final int statusCode = response.getStatusLine().getStatusCode();
        final String reasonPhrase = TextUtils.isEmpty(response.getStatusLine().getReasonPhrase()) ? "unknown" : response.getStatusLine().getReasonPhrase();
        final Map<String, String> responseHeaders = toHeaders(response.headerIterator());
        InputStream data = null;
        try {
            final  HttpEntity entity = response.getEntity();
            if(entity != null) {
                data = entity.getContent();
            }
        } catch (IOException e) {
            Timber.e(e, "Error while converting response");
        }

        Timber.d("New web resources contentType: %s, mimeType: %s, encoding: %s, statusCode: %d, reasonPhrase: %s, responseHeaders: %s ", contentType, mimeType, encoding, statusCode, reasonPhrase, responseHeaders);

        return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, responseHeaders, data);
    }

    @NonNull
    private static Map<String, String> toHeaders(@NonNull final HeaderIterator headerIterator) {
        final Map<String, String> result = new HashMap<>();

        while (headerIterator.hasNext()) {
            final Header header = headerIterator.nextHeader();
            result.put(header.getName(), header.getValue());
        }

        return result;
    }
}
