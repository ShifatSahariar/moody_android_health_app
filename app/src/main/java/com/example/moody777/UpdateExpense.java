package com.example.moody777;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateExpense extends AppCompatActivity {
    EditText name_input, value_input;
    Button update_button , delete_button;
    String id, name, value, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_income);



        name_input =findViewById(R.id.up_type_edt);
        value_input = findViewById(R.id.up_ammount_edt);

        update_button =findViewById(R.id.btnUPDATE);
        delete_button =findViewById(R.id.btnDELETE);

        //First we call this
        getAndSetIntentData();

        //set actionbar title after getandsetıntent data
        ActionBar ab =getSupportActionBar();
        if(ab != null){
            ab.setTitle(name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //and only then we call this
                name =  name_input.getText().toString();
                value =  value_input.getText().toString();
                sqlite_layer myDB = new sqlite_layer(UpdateExpense.this);
                myDB.updateDataE(id,name,value);
                Intent intent = new Intent(UpdateExpense.this, Budget.class);
                startActivity(intent);
            }
        });
        System.out.println("HEEEY");
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("value") ) {
            //getting data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            value = getIntent().getStringExtra("value");
            System.out.println("u_name "+name);
            System.out.println("u_id "+id);


            //setting intent data

            name_input.setText(name);
            value_input.setText(value);


        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete"+name+"?");
        builder.setMessage("Are you sure you want to delete"+name+"?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sqlite_layer myDB = new sqlite_layer(UpdateExpense.this);
                myDB.DeleteOneRowE(id);
                //finish();
                Intent intent = new Intent(UpdateExpense.this, Budget.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
