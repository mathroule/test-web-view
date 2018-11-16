# WebView with different HTTP Clients

## Integrated HTTP clients
* Default web view without request interception
* Web view with request intercepted by [HTTP URL connection](https://developer.android.com/reference/java/net/HttpURLConnection)
* Web view with request intercepted by [Apache default HTTP client](https://developer.blackberry.com/devzone/files/blackberry-dynamics/android/classcom_1_1good_1_1gd_1_1apache_1_1http_1_1impl_1_1client_1_1_default_http_client.html)
* Web view with request intercepted by [GD HTTP client](https://developer.blackberry.com/devzone/files/blackberry-dynamics/android/classcom_1_1good_1_1gd_1_1net_1_1_g_d_http_client.html)
* Web view with request intercepted by [OK HTTP client](https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.html)

## Setup
Setup BlackBerry Dynamics following [this setup](https://developers.blackberry.com/us/en/resources/get-started/blackberry-dynamics-getting-started.html?platform=android).

The default web view URL could be changed in [BaseWebViewActivity.java](./app/src/main/java/com/mathroule/testwebview/activity/BaseWebViewActivity.java).

## Result
Loading times in milliseconds for an URL using an HTTP client.

_Test runs on Nexus 6P emulator with Android API 26._

| URL \ HTTP client | Default web view client | HTTP URL connection | Apache default HTTP client | GD HTTP client | OK HTTP client |
|-------------------|-------------------------|---------------------|----------------------------|----------------|----------------|
| https://www.google.com | 895 ms | 942 ms | 925 ms | 1932 ms | 623 ms |
| https://github.com | 1931 ms | 2730 ms | 9787 ms | 21600 ms | 2737 ms |
| https://httpbin.org/redirect-to?url=https://www.google.com | 707 ms | 1400 ms | 1480 ms | 2560 ms | 1015 ms |
