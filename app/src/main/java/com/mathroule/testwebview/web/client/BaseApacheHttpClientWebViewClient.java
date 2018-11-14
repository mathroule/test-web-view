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
import com.good.gd.apache.http.client.RedirectHandler;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.apache.http.client.methods.HttpPost;
import com.good.gd.apache.http.client.methods.HttpRequestBase;
import com.good.gd.apache.http.impl.client.DefaultHttpClient;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.net.GDHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public abstract class BaseApacheHttpClientWebViewClient extends BaseWebViewClient {

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        final String method = request.getMethod();
        final String url = request.getUrl().toString();
        return interceptRequest(view, method, url);
    }


    @Nullable
    private WebResourceResponse interceptRequest(@NonNull final WebView view, @NonNull final String method, @NonNull final String url) {
        final GDHttpClient gdHttpClient = new GDHttpClient();
        gdHttpClient.setRedirectHandler(new RedirectHandler() {
            @Override
            public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
                return false;
            }

            @Override
            public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) {
                return null;
            }
        });

        Timber.d("Starting to load %s %s", method, url);

        final HttpRequestBase httpRequest = createHttpRequest(url, method);
        try {
            final HttpResponse httpResponse = gdHttpClient.execute(httpRequest);

            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (isRedirection(statusCode)) {
                final Header location = httpResponse.getFirstHeader("Location") != null
                        ? httpResponse.getFirstHeader("Location")
                        : httpResponse.getFirstHeader("location");

                if (location != null) {
                    final String redirectUrl = location.getValue();
                    Timber.d("Redirecting from %s to %s", url, redirectUrl);
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

            return toWebResourceResponse(httpResponse, gdHttpClient);
        } catch (IOException e) {
            Timber.e(e, "Error while loading %s %s", method, url);
        }

        Timber.d("Done loading %s %s", method, url);

        return null;
    }

    @NonNull
    abstract DefaultHttpClient createHttpClient();

    @Nullable
    private WebResourceResponse toWebResourceResponse(@NonNull final HttpResponse response, @NonNull final DefaultHttpClient defaultHttpClient) {
        final Header firstHeader = response.getFirstHeader("content-type");
        final String contentType = firstHeader != null ? firstHeader.getValue() : null;
        final String mimeType = getMimeType(contentType);
        final String encoding = getEncoding(contentType);
        final int statusCode = response.getStatusLine().getStatusCode();
        final String message = response.getStatusLine().getReasonPhrase();
        final String reasonPhrase = TextUtils.isEmpty(message) ? "unknown" : message;
        final Map<String, String> responseHeaders = toHeaders(response.headerIterator());

        Timber.d("New web resources contentType: %s, mimeType: %s, encoding: %s, statusCode: %d, reasonPhrase: %s, responseHeaders: %s ", contentType, mimeType, encoding, statusCode, reasonPhrase, responseHeaders);

        InputStream data = null;
        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                data = new InputStreamWrapper(entity.getContent(), defaultHttpClient);
            }
        } catch (IOException e) {
            Timber.e(e, "Error while converting response");

            defaultHttpClient.getConnectionManager().shutdown();

            return null;
        }

        return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, responseHeaders, data);
    }

    @NonNull
    private static HttpRequestBase createHttpRequest(@NonNull final String url, @NonNull final String method) {
        switch (method.toUpperCase()) {
            case "POST":
                // TODO set request body
                return new HttpPost(url);
            case "GET":
            default:
                return new HttpGet(url);
        }
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
