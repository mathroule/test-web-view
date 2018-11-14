package com.mathroule.testwebview.web.client;

import android.support.annotation.NonNull;

import com.good.gd.net.GDHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends InputStream {

    private final InputStream inputStream;

    private final GDHttpClient gdHttpClient;

    public InputStreamWrapper(@NonNull final InputStream inputStream, @NonNull final GDHttpClient gdHttpClient) {
        super();
        this.inputStream = inputStream;
        this.gdHttpClient = gdHttpClient;
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
            gdHttpClient.getConnectionManager().shutdown();
        }
    }
}
