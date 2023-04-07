package com.example.feedjungle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivitySignInBinding;
import com.example.feedjungle.databinding.ActivityUserLoginBinding;
import com.example.feedjungle.utils.CommonFunction;

public class UserLoginActivity extends AppCompatActivity {
    ActivityUserLoginBinding signInBinding;
    CommonFunction commonFunction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = DataBindingUtil.setContentView(UserLoginActivity.this,R.layout.activity_user_login);
        commonFunction = new CommonFunction(this);

        signInBinding.loginbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonFunction.navigation(UserLoginActivity.this, LoginActivity.class);
                finish();
            }
        });

        signInBinding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonFunction.navigation(UserLoginActivity.this, SignInActivity.class);
                finish();
            }
        });

    }
}