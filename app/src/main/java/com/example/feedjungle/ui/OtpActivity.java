package com.example.feedjungle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivityOtpBinding;
import com.example.feedjungle.utils.SharedPref;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp);
        String getMobileNumber = SharedPref.getString(SharedPref.PREF_KEY.MOBILE_NUMBER);
//        binding.hiddenNumberValidate.setText(getMobileNumber.replace());

    }
}