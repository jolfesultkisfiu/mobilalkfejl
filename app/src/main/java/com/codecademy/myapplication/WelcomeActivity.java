package com.codecademy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private static final String LOG_TAG=WelcomeActivity.class.getName();
    private TextView welcomeString;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Log.d(LOG_TAG,"Authentikált felhasználó!");
            welcomeString=findViewById(R.id.welcomeTextView);
            String udv="Üdv, "+user.getEmail();
            if(user.getEmail()==null){
                udv="Üdv, Vendég!";
            }
            welcomeString.setText(udv);
            Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide_in_from_left);
            Animation anim=AnimationUtils.loadAnimation(this,R.anim.slide_in_from_right);

            findViewById(R.id.createButton).startAnimation(animation);
            findViewById(R.id.deleteButton).startAnimation(anim);
            findViewById(R.id.updateButton).startAnimation(animation);
            findViewById(R.id.readButton).startAnimation(anim);

        }else{
            Log.d(LOG_TAG,"Nem authentikált felhasználó!");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user= FirebaseAuth.getInstance().getCurrentUser();
        Log.d(LOG_TAG,"Resumeban vagyunk");
        if(user==null){
            Log.d(LOG_TAG,"Nem authentikált felhasználó!");
            finish();
        }else{
            Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide_in_from_left);
            Animation anim=AnimationUtils.loadAnimation(this,R.anim.slide_in_from_right);

            findViewById(R.id.createButton).startAnimation(animation);
            findViewById(R.id.deleteButton).startAnimation(anim);
            findViewById(R.id.updateButton).startAnimation(animation);
            findViewById(R.id.readButton).startAnimation(anim);
        }
    }

    public void read(View view) {
        Intent intent=new Intent(this,ReadActivity.class);
        startActivity(intent);
    }
}