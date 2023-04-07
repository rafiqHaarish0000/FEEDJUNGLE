package com.example.feedjungle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
    }
}