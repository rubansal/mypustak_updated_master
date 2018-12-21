package com.mypushtak.app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mypushtak.app.R;

public class EditAddress extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        mToolbar=findViewById(R.id.edit_address_toolbar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextview=findViewById(R.id.change_password);
        mTextview.setText("EDIT ADDRESS");



    }
}
