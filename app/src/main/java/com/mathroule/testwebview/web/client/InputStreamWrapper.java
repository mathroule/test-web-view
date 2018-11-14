package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;

import com.good.gd.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends InputStream {

    private final InputStream inputStream;

    private final DefaultHttpClient defaultHttpClient;

    InputStreamWrapper(@NonNull final InputStream inputStream, @NonNull final DefaultHttpClient defaultHttpClient) {
        super();
        this.inputStream = inputStream;
        this.defaultHttpClient = defaultHttpClient;
    }

    @Override
    public int read(@NonNull byte[] bytes) throws IOException {
        return inputStream.read(bytes);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void close() throws IOException {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw e;
        } finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }
    }
}
