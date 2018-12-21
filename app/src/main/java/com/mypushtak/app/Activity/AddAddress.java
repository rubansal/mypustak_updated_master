package com.mypushtak.app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mypushtak.app.R;

public class AddAddress extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        mToolbar=findViewById(R.id.maintop);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView=findViewById(R.id.change_password);
        mTextView.setText("ADD NEW ADDRESS");

    }
}
