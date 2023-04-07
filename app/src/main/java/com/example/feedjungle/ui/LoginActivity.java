package com.example.feedjungle.ui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivityLoginBinding;
import com.example.feedjungle.utils.CommonFunction;
import com.example.feedjungle.utils.SharedPref;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    GoogleSignInOptions gso;
    GoogleSignInClient gci;
    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    ImageView fb_btn;
    private CommonFunction commonFunction;
    private String emailAddress, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gci = GoogleSignIn.getClient(this, gso);

        commonFunction = new CommonFunction(this);

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (commonFunction.isValidEmail(binding.emailValid.getText().toString(), binding.emailValid) == true
                        && commonFunction.isEmptyText(binding.passwordValid.getText().toString(), LoginActivity.this) == true) {

                    emailAddress = SharedPref.getString(SharedPref.PREF_KEY.EMAIL_ADDDRESS);
                    confirmPassword = SharedPref.getString(SharedPref.PREF_KEY.CONFIRM_PASSWORD);

                    if (binding.emailValid.equals(emailAddress) && binding.passwordValid.equals(confirmPassword)) {
                        Toast.makeText(LoginActivity.this, "Login successful.!", Toast.LENGTH_LONG).show();
                        commonFunction.navigation(LoginActivity.this,IntroSliderActivity.class);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Something went wrong please try again.!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Login error,Please try again.!", Toast.LENGTH_LONG).show();

                }
            }
        });

        binding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });

        binding.signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonFunction.navigation(LoginActivity.this, SignInActivity.class);
                finish();
            }
        });

    }


    private void signin() {
        Intent intent = gci.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //google signin
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                DashboardActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void DashboardActivity() {
        Toast.makeText(this, "login successfully..!", Toast.LENGTH_LONG).show();
    }

}