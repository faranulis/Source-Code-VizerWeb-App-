package com.vizerweb.vizerweb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vizerweb.vizerweb.menu.About;
import com.vizerweb.vizerweb.menu.Contact;
import com.vizerweb.vizerweb.menu.Settings;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private ProgressDialog progressBar;
    //Navigasi Menu 3 Columb
    final Fragment fragment1 = new Home();
    final Fragment fragment2 = new Label();
//    final Fragment fragment3 = new NotificationsFragment();
final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ads Admob
        MobileAds.initialize(this, "ca-app-pub-4625381772326882~6996494638");

        //FUlL Ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4625381772326882/5001720837");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //banner ads
        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        checkInternet();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Dekralasi Navigasi 3 Columb
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();
    }


    //Cek Koneksi Dan Diarahkan Sesuai Dengan Adanya KOneksi
    public boolean checkInternet() {
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            connectStatus = true;
            //Aktifitas Yang Akan Di lakukan Ada KOneksi
            Toast.makeText(this, "Agan Terhubung ke Internet", Toast.LENGTH_SHORT).show();
        } else {
            connectStatus = false;
            Toast.makeText(this, "Agan Tidak Terhubung ke Internet", Toast.LENGTH_SHORT).show();
            //Aktifitas Yang Akan Di lakukan Tidak Ada Koneksi
            LayoutInflater objek2 = getLayoutInflater();
            View v2 = objek2.inflate(R.layout.nokoneksi, null);
            AlertDialog.Builder a = new AlertDialog.Builder(this);
            a.setView(v2);
            a.show();
            a.setCancelable(true);
        }
        return connectStatus;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //ini tempat buat nampilin icon
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //ini tempat buat nanganin klik pada oncreateoptionmenu
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        } else if (id == R.id.action_contact) {
            progressBar = ProgressDialog.show(this, "Hi", "me");
            WebView webView = (WebView) findViewById(R.id.webview);

            webView.setWebViewClient(new MyWebViewClient());
            webView.loadUrl("https://rawgit.com/vizerweb/VizerStyle/master/formamp1.html?email=farhanul31@gmail.com&url=https://rawgit.com/vizerweb/VizerStyle/master/LinkThxForm.html&blog=http://www.vizerweb.com/");
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), About.class));
            return true;
        } else if (id == R.id.search) {
            //jika ada FC onitemselect coba lihat manifest dulu apakah class nya tertulis ?
            startActivity(new Intent(getApplicationContext(), SearchBox.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressBar != null && progressBar.isShowing()) {
                progressBar.dismiss();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Bundle bundle = new Bundle();
            //Add your data from getFactualResults method to bundle
            bundle.putString("NextUrl", url);

            Intent i = new Intent(MainActivity.this, NextActivity.class);
            i.putExtras(bundle);
            startActivity(i);

            Log.i("Hi", "WEb nextttt");

            return true;
        }
    }


    //Fungsi Navigasi Menu 3Columb
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_dashboard:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.navigation_notifications:
//                    fm.beginTransaction().hide(active).show(fragment3).commit();
//                    active = fragment3;
                    return true;
            }
            return false;
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_tipspc) {

        } else if (id == R.id.nav_duniainet) {

        } else if (id == R.id.nav_app) {

        } else if (id == R.id.nav_tipspc) {

        } else if (id == R.id.nav_games) {


            //Tips Artikel Android
        } else if (id == R.id.nav_trickandroid) {

        } else if (id == R.id.nav_artikelandroid) {


            // Gadget
        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        } else if (id == R.id.nav_games) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //fungsi ibawah berfungsi sebagai tombol next aktifity (dari webview) dari activity content ke main2 (ke webview selanjutnya)

}