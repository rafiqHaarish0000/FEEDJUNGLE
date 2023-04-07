package com.example.feedjungle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivitySplashScreenBinding;
import com.example.feedjungle.databinding.ActivityUserLoginBinding;
import com.example.feedjungle.utils.CommonFunction;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding signInBinding;
    CommonFunction commonFunction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = DataBindingUtil.setContentView(SplashScreenActivity.this,R.layout.activity_splash_screen);
        commonFunction = new CommonFunction(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                commonFunction.navigation(SplashScreenActivity.this,UserLoginActivity.class);
                finish();
            }
        },3000);

    }
}