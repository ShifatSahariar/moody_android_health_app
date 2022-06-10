package com.example.moody777;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button signin;
    LinearLayout l1,l2;
    Animation updown ,downup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        signin = (Button) findViewById(R.id.button);
        l1=(LinearLayout)findViewById(R.id.l1);
        l2=(LinearLayout)findViewById(R.id.l1);
        updown= AnimationUtils.loadAnimation(this,R.anim.updown);
        l1.setAnimation(updown);

        signin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                openLoginActivity();

                //works when button is clicked
            }
        });

    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);

    }



}
