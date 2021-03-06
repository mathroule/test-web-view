package com.mathroule.testwebview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.good.gd.GDAndroid;
import com.mathroule.testwebview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GDAndroid.getInstance().activityInit(this);

        setContentView(R.layout.activity_main);
    }

    public void onStartDefaultWebView(View view) {
        startActivity(new Intent(MainActivity.this, DefaultWebViewActivity.class));
    }

    public void onStartHttpUrlConnectionWebView(View view) {
        startActivity(new Intent(MainActivity.this, HttpUrlConnectionWebViewActivity.class));
    }

    public void onStartDefaultHttpWebView(View view) {
        startActivity(new Intent(MainActivity.this, DefaultHttpClientWebViewActivity.class));
    }

    public void onStartGdHttpWebView(View view) {
        startActivity(new Intent(MainActivity.this, GdHttpClientWebViewActivity.class));
    }

    public void onStartGdHttpParallelWebView(View view) {
        startActivity(new Intent(MainActivity.this, GdHttpClientParallelWebViewActivity.class));
    }

    public void onStartOkHttpWebView(View view) {
        startActivity(new Intent(MainActivity.this, OkHttpClientWebViewActivity.class));
    }
}
