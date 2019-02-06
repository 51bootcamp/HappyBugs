package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.happybugs.happybugs.R;

public class SplashScreen extends AppCompatActivity {
    Context currContext = this;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent introIntent = new Intent(currContext, IntroActivity.class);
                currContext.startActivity(introIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
