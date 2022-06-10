package com.example.moody777;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moody777.R;

public class Resources extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        this.setTitle("Resource");
    }
    public void browser1(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.verywellmind.com/symptoms-of-mania-380311"));
        startActivity(browserIntent);
    }
    public void browser2(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.medicalnewstoday.com/articles/324578#symptoms"));
        startActivity(browserIntent);
    }

}