package com.example.moody777;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

public class BudgetMain extends AppCompatActivity {
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                openMenuActivity();

                //works when button is clicked
            }
        });

    }
    public void openMenuActivity() {
        Intent intent = new Intent(this, Budget.class);
        startActivity(intent);

    }
}