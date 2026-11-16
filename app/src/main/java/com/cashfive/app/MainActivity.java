package com.cashfive.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;

public class MainActivity extends Activity {
    private WebView webView;

    public class StorageBridge {
        private final SharedPreferences preferences;
        StorageBridge(Context context) {
            preferences = context.getSharedPreferences("cash5_settings", Context.MODE_PRIVATE);
        }
        @JavascriptInterface
        public String load() {
            return preferences.getString("form", "{}");
        }
        @JavascriptInterface
        public void save(String json) {
            preferences.edit().putString("form", json).apply();
        }
        @JavascriptInterface
        public void clear() {
            preferences.edit().remove("form").apply();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(10, 45, 37));
        getWindow().setNavigationBarColor(Color.rgb(10, 45, 37));
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(248, 250, 249));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        webView.addJavascriptInterface(new StorageBridge(this), "CashFiveStorage");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !"file".equalsIgnoreCase(request.getUrl().getScheme());
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !url.startsWith("file:///android_asset/");
            }
        });
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
