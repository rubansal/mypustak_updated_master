package com.mypushtak.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mypushtak.app.R;

public class Donation extends AppCompatActivity {

    ImageView home_back;

    FrameLayout next_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        home_back=findViewById(R.id.home_back);
        next_layout=findViewById(R.id.next_layout);

        home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Donation.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        next_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Donation.this,Donationform.class);
                startActivity(i);
            }
        });
    }
}
