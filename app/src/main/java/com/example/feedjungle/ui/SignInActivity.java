package com.example.feedjungle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivitySignInBinding;
import com.example.feedjungle.utils.CommonFunction;
import com.example.feedjungle.utils.SharedPref;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding signInBinding;
    CommonFunction commonFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = DataBindingUtil.setContentView(SignInActivity.this, R.layout.activity_sign_in);
        commonFunction = new CommonFunction(this);

        signInBinding.backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonFunction.navigation(SignInActivity.this, LoginActivity.class);
                finish();
            }
        });

        signInBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (commonFunction.isValidEmail(signInBinding.isEmailValid.getText().toString(), signInBinding.isEmailValid) == true
                        && commonFunction.isValidMobileNumber(signInBinding.mIsValidMN.getText().toString(), signInBinding.mIsValidMN) == true
                        && commonFunction.isValidMobileNumber(signInBinding.mIsValidMN.getText().toString(), signInBinding.mIsValidMN) == true
                        && commonFunction.isValidConfirmPassword(signInBinding.mPassword.getText().toString(), signInBinding.mCPassword.getText().toString(), SignInActivity.this) == true
                        && commonFunction.isEmptyText(signInBinding.mfullName.getText().toString(), SignInActivity.this) == true
                        && commonFunction.isEmptyText(signInBinding.mAddress.getText().toString(), SignInActivity.this) == true
                        && commonFunction.isEmptyText(signInBinding.mPassword.getText().toString(), SignInActivity.this) == true
                        && commonFunction.isEmptyText(signInBinding.mCPassword.getText().toString(), SignInActivity.this) == true) {


                    SharedPref.putString(SharedPref.PREF_KEY.EMAIL_ADDDRESS,signInBinding.isEmailValid.getText().toString());
                    SharedPref.putString(SharedPref.PREF_KEY.MOBILE_NUMBER,signInBinding.mIsValidMN.getText().toString());
                    SharedPref.putString(SharedPref.PREF_KEY.CONFIRM_PASSWORD,signInBinding.mCPassword.getText().toString());
                    SharedPref.putString(SharedPref.PREF_KEY.FULLNAME,signInBinding.mfullName.getText().toString());



                    Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    commonFunction.navigation(SignInActivity.this,IntroSliderActivity.class);
                    finish();


                }
            }
        });

    }
}