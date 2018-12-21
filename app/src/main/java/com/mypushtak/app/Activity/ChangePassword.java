package com.mypushtak.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mypushtak.app.R;

public class ChangePassword extends AppCompatActivity {


    private ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        back_button=findViewById(R.id.navigationnn);



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToBooksActivity();
            }
        });
    }

    private void sendToBooksActivity() {

        Intent i=new Intent(ChangePassword.this,Fictionbooks.class);
        startActivity(i);
    }
}
