package com.codecademy.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private static final String LOG_TAG=Register.class.getName();
    private static final String PREF_KEY=MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY=99;

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    EditText userPasswordAgainEditText;
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        int secret_key=getIntent().getIntExtra("SECRET_KEY",0);

        if(secret_key!=99){
            finish();
        }
        userNameEditText=findViewById(R.id.usernameEditText);
        userEmailEditText=findViewById(R.id.userEmailEditText);
        userPasswordEditText=findViewById(R.id.passwordEditText);
        userPasswordAgainEditText=findViewById(R.id.passwordAgainEditText);
        preferences=getSharedPreferences(PREF_KEY,MODE_PRIVATE);

        String username=preferences.getString("username","");
        String password=preferences.getString("password","");

        userNameEditText.setText(username);
        userPasswordEditText.setText(password);

        mAuth=FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String username=userNameEditText.getText().toString();
        String password=userPasswordEditText.getText().toString();
        String passwordConfim=userPasswordAgainEditText.getText().toString();
        String email=userEmailEditText.getText().toString();
        if(!password.equals(passwordConfim)){
            Log.e(LOG_TAG,"Nem egyenlo a jelszo es a megerositese!");
            return;
        }
        if(username.isEmpty()|| password.isEmpty() || email.isEmpty()){
            Log.e(LOG_TAG,"Üres valamelyik mező!");
            return;
        }
        if(!email.contains("@")){
            Log.e(LOG_TAG,"Rossz az email formátuma!");
            return;
        }
        Log.i(LOG_TAG,"Regisztrált: "+username+" jelszo:"+email);
        //startSoccer();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG,"Felhasználó sikeresen létrehozva!");
                    startSoccer();
                }else{
                    Log.d(LOG_TAG,"Felhasználót nem sikerült létrehozni!");
                    Toast.makeText(Register.this,"Felhasználót nem sikerült létrehozni: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }
    private void startSoccer(/* registered user data */){
        Intent intent=new Intent(this,WelcomeActivity.class);
        //intent.putExtra("SECRET_KEY",SECRET_KEY);
        startActivity(intent);
    }
}