package com.app.jmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.app.jmd.activity.LoginActivity;
import com.app.jmd.activity.MainActivity2;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreeen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screeen);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task1 -> {

                    if(!task1.isSuccessful())
                    {
                        return;
                    }
                    String newToken = task1.getResult();
                    AppPrefrences.getInstance(getApplicationContext()).saveDataToPrefs("Token_Key",newToken);
                });


        int DELAY = 4000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPrefrences.getInstance(getApplicationContext()).getDataFromPrefs("is_login").equalsIgnoreCase("Yes"))
                {
//                    Toast.makeText(SplashScreeen.this, ""+AppPrefrences.getInstance(SplashScreeen.this).getDataFromPrefs("is_login"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreeen.this, MainActivity2.class));
//                    startActivity(new Intent(SplashScreeen.this, LoginActivity.class));
                    finish();

                }else {
                    startActivity(new Intent(SplashScreeen.this, LoginActivity.class));
//                    startActivity(new Intent(SplashScreeen.this, MainActivity2.class));
                    finish();
                }

            }
        }, DELAY);


    }
}