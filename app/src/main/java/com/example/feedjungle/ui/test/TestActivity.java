package com.example.feedjungle.ui.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.feedjungle.R;

public class TestActivity extends AppCompatActivity {
    EditText mPhoneNumber;
    Button mGetPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mPhoneNumber = findViewById(R.id.getPhoneNumber);
        mGetPhoneNumber = findViewById(R.id.getNumber);

        String[] permission = {
                Manifest.permission.READ_PHONE_NUMBERS
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission,102);
        }

        mGetPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSmsPhoneNumber();
            }
        });
    }

    private void setSmsPhoneNumber() {
        TelephonyManager mTeleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mPhoneNumber.setText(mTeleManager.getLine1Number());
    }
}