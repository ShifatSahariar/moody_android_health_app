package com.example.moody777.SelfTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moody777.Menu;
import com.example.moody777.R;


public class analysePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyse);
        Intent intent=getIntent();
        //ArrayList<String> getData =(ArrayList<String>) intent.getSerializableExtra("Key");
        String messege= intent.getStringExtra("messege");
        String headline= intent.getStringExtra("headline");
        int photoID= intent.getIntExtra("photo",R.drawable.offmood);
        //String percentage= intent.getStringExtra("Percentage");
        //TextView textView = findViewById(R.id.textviewShifat);
        TextView messegeText = findViewById(R.id.overallAnalysis);
        TextView headlineText = findViewById(R.id.headline);
        ImageView doctorPhoto = findViewById(R.id.setDoctorPhoto);
        Button backtoHomeButton = findViewById(R.id.btn_batkthomHome);
        //TextView textView3 = findViewById(R.id.analysisPersantage);
        //textView.setText(String.valueOf(getData));
        messegeText.setText(String.valueOf(messege));
        headlineText.setText(String.valueOf(headline));
        doctorPhoto.setImageResource(photoID);

        //textView3.setText(String.valueOf(percentage));


    }




    public void backToHomeButton(View view){

        Intent intent = new Intent(analysePage.this, Menu.class);
        startActivity(intent);
    }
}