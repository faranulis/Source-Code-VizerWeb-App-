package com.vizerweb.vizerweb;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final WebView webView = (WebView) view.findViewById(R.id.webview);
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
        //client custom untuk refresh (CUSTOMVIEWCLIENT)
        webView.setWebViewClient(new Home.CustomWebViewClient());
        //-----------------------------//

        progressBar = ProgressDialog.show(this.getActivity(), "", "me");
//        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("file:///android_asset/home.html");
        //Cara Membuat HTML Di Dalam JAVA
//        webView.loadUrl("data:text/html,".concat("HTML DISINI TINGGAL PASTE AJA"));

        //backkey press
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
        //untuk refresh dan mengarah client custom dan onrefresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected() == true) {
                            //Aktifitas Yang Akan Di lakukan Ada KOneksi
                            Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
                        } else {
                            checkInternet();
                        }
                    }
                }
        );

        return view;
    }

    private boolean checkInternet() {
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            connectStatus = true;
            //Aktifitas Yang Akan Di lakukan Ada KOneksi
            Toast.makeText(getActivity(),"Agan Terhubung Ke Internet!",Toast.LENGTH_SHORT).show();
        } else {
            connectStatus = false;
            Toast.makeText(getActivity(), "Agan Tidak Terhubung ke Internet", Toast.LENGTH_SHORT).show();
            //Aktifitas Yang Akan Di lakukan Tidak Ada Koneksi
            LayoutInflater objek2 = getActivity().getLayoutInflater();
            View v2 = objek2.inflate(R.layout.nokoneksi, null);
            AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
            a.setView(v2);
            a.show();
            a.setCancelable(true);
        }
        return connectStatus;
    }

    //Swipe Up Refresh Webpage Tebaik VizerWeb
    //bisa berhenti sendiri jika server load finish
    public class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Bundle bundle = new Bundle();
            //Add your data from getFactualResults method to bundle
            bundle.putString("NextUrl", url);

            Intent i = new Intent(getActivity(), NextActivity.class);
            i.putExtras(bundle);
            startActivity(i);

            //-----------------------

            Log.i("Hi", "WEb nextttt");
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            swipeRefreshLayout.setRefreshing(false);
            if (progressBar != null && progressBar.isShowing()) {
                progressBar.dismiss();
            }
        }
    }



    public Home() {
        // Required empty public constructor
    }

}
