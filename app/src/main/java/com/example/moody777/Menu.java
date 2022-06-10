package com.example.moody777;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Menu extends AppCompatActivity {

    ImageView resources, budget, mood,sleepTrack,share,calenderView;
    CardView cardView,cardViewselftest,viewbudget,viewresources,viewshare,viewtracksleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        resources=(ImageView)findViewById(R.id.imageView4);
        budget=(ImageView)findViewById(R.id.budgetid);


        cardView= (CardView)findViewById(R.id.testcard);
        cardViewselftest= (CardView)findViewById(R.id.selfttest);
        viewbudget= (CardView)findViewById(R.id.budgetcard);
        viewresources= (CardView)findViewById(R.id.resourcecard);
        viewshare= (CardView)findViewById(R.id.sharedata);
        viewtracksleep= (CardView)findViewById(R.id.sleeptracker);


        mood = (ImageView)findViewById(R.id.moodFeature);
        sleepTrack = (ImageView)findViewById(R.id.sleeptrack);
        share = (ImageView)findViewById(R.id.shareid);
        calenderView = (ImageView)findViewById(R.id.calenderid);


        viewresources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Resources.class);
                startActivity(intent);
            }
        });

        viewbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Budget.class);
                startActivity(intent);
            }
        });
        cardViewselftest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Mood.class);
                startActivity(intent);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,SleepTracker.class);
                startActivity(intent);
            }
        });
        viewshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,Share.class);
                startActivity(intent);

            }
        });
        viewtracksleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this,CalenderView.class);
                startActivity(intent);

            }
        });
    }
}