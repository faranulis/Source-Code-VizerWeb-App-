package com.vizerweb.vizerweb.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vizerweb.vizerweb.R;


public class About extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        btn1 = (Button) findViewById(R.id.sosmed1);
        //funngis sebagai start
        klik1();
        btn2 = (Button) findViewById(R.id.sosmed2);
        klik2();
        btn3 = (Button) findViewById(R.id.sosmed3);
        klik3();
        btn4 = (Button) findViewById(R.id.sosmed4);
        klik4();


    }

    //ini sebagai fungsinya
    public void klik1() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fungsi sebagai button go to browser with link
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://plus.google.com/107215778787009993021")));
                //make toash after klik button
                Toast.makeText(About.this, "Facebook", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void klik2() {
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.facebook.com/vizerblog")));
                Toast.makeText(About.this, "Facebook", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void klik3() {
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.twitter.com/farhanul31")));
                Toast.makeText(About.this, "Twitter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void klik4() {

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.instagram.com/viz.farhan")));
                Toast.makeText(About.this, "Instagram", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
