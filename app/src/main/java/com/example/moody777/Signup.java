package com.example.moody777;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private TextView logintext;
    private Button sign;
    private EditText editusername,editpass, editconfpass,editmail;
    String userID;
    //FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    public static final String TAG= "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        editusername=(EditText)findViewById(R.id.editText3);
        editpass=(EditText)findViewById(R.id.editText4);
        editconfpass=(EditText)findViewById(R.id.editText5);
        editmail=(EditText)findViewById(R.id.editText6);
        logintext=(TextView)findViewById(R.id.textView5);
        sign = (Button) findViewById(R.id.button4);

        fAuth=FirebaseAuth.getInstance();
      //  fStore=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent(Signup.this, Menu.class);
            startActivity(intent);
            finish();
        }


        sign.setOnClickListener(this);
        logintext.setOnClickListener(this);


    }
    private void registerUser() {
        final String username=editusername.getText().toString();
        final String email=editmail.getText().toString().trim();
        String password=editpass.getText().toString().trim();
        String confpass=editconfpass.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(Signup.this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;



        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Signup.this, "Please enter mail address", Toast.LENGTH_SHORT).show();
            return;



        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Signup.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;



        }
        if(password.length()<6 ){
            editpass.setError("Password must be >= 6 characters");
            return;



        }
        if(!password.equals(confpass)){
            editpass.setError("password confirmation doesn't match with password");
            return;



        }
        if(TextUtils.isEmpty(confpass)){
            Toast.makeText(Signup.this, "Please enter confpass", Toast.LENGTH_SHORT).show();
            return;




        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup.this, "Account Created", Toast.LENGTH_SHORT).show();
                   // userID=fAuth.getCurrentUser().getUid();
                   // DocumentReference documentReference =fStore.collection("users").document(userID);
                   // Map<String,Object>user=new HashMap<>();
                   // user.put("kName",username);
                    //user.put("kemail",email);
                    //documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                       // @Override
                       // public void onSuccess(Void aVoid) {
                           // Log.d(TAG, "onSuccess:user Profile is created for  "+userID);
                      //  }
                    //});
                    Intent intent = new Intent(Signup.this, Menu.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Signup.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onClick(View view){
        if (view == sign) {
            registerUser();

        }
        if (view == logintext) {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        }


    }



}
