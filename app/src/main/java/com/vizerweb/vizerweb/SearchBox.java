package com.vizerweb.vizerweb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Community Programer on 21/08/2018.
 */

public class SearchBox extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchweb);

        WebView webView = (WebView) findViewById(R.id.SearchWeb);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUserAgentString(null);

        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //------------------------------//
        //next aktifity
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/search.html");
        webView.addJavascriptInterface(new SearchBox.WebAppInterface(this), "Android");
        //-----------------------------//

        //button back
        Button btnback = (Button) findViewById(R.id.back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //fungsi untuk Dari JS Mejadi Toast (Native JS)
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() == true) {
                boolean connectStatus = true;
                //Aktifitas Yang Akan Di lakukan Ada KOneksi
                Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
            } else {
                boolean connectStatus = false;
                //Aktifitas Yang Akan Di lakukan Tidak Ada Koneksi
                Toast.makeText(mContext, "Agan Tidak Punya Koneksi", Toast.LENGTH_LONG).show();

            }

        }
    }
}
