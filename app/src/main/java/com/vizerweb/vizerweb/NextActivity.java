package com.vizerweb.vizerweb;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NextActivity extends Activity {

    WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Kolom Komentar Belum Rilis...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String url = bundle.getString("NextUrl");


        Log.i("Hi", "Get next");
        wv1 = (WebView) findViewById(R.id.nextWeb);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        wv1.getSettings().setSupportZoom(true);
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.getSettings().setDisplayZoomControls(false);
        wv1.getSettings().setUserAgentString(null);

        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewn, String urln) {
                viewn.loadUrl(urln);
                return false;
            }
        });
        try {
            wv1.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
