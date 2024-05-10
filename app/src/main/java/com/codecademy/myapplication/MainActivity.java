package com.codecademy.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG=MainActivity.class.getName();
    private static final String PREF_KEY=MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY=99;
    EditText usernameEt;
    EditText passwordET;
    private FirebaseAuth mAuth;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEt=findViewById(R.id.EditTextUserName);
        passwordET=findViewById(R.id.editTextPassword);

        preferences=getSharedPreferences(PREF_KEY,MODE_PRIVATE);
        mAuth=FirebaseAuth.getInstance();
    }

    public void login(View view) {


        String username=usernameEt.getText().toString();
        String password=passwordET.getText().toString();
        //Log.i(LOG_TAG,username+" jelszo:"+password);
        if(!username.isEmpty() &&!password.isEmpty()){
            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG,"Sikeres bejelentkezés!");
                        startSoccer();
                    }else{
                        Log.d(LOG_TAG,"Nem sikerült bejelentkezni, hibás email vagy jelszó!");
                        Toast.makeText(MainActivity.this,"Nem sikerült bejelentkezni!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return;

    }
    private void startSoccer(){
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent=new Intent(this,Register.class);
        intent.putExtra("SECRET_KEY",SECRET_KEY);
        startActivity(intent);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("username",usernameEt.getText().toString());
        editor.putString("password",passwordET.getText().toString());
        editor.apply();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loginAsGuest(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG,"Vendégként jelentkeztél be!!");
                    startSoccer();
                }else{
                    Log.d(LOG_TAG,"Nem sikerült vendégként bejelentkezni!");
                    Toast.makeText(MainActivity.this,"Nem sikerült vendégként bejelentkezni! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}