package com.mypushtak.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mypushtak.app.R;

public class donationlastpart extends AppCompatActivity {

    ImageView home_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationlastpart);

        home_back=findViewById(R.id.home_back);
        home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(donationlastpart.this,Donationform.class);
                startActivity(i);
                finish();
            }
        });
    }
}
