package com.example.feedjungle.ui.test;

import static com.facebook.internal.FacebookDialogFragment.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivityOtpGenerateBinding;
import com.example.feedjungle.ui.LoginActivity;
import com.example.feedjungle.ui.test.subClass.SmsbroadCastReciever;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

public class OtpGenerate extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String verification_ID;
    ActivityOtpGenerateBinding binding;
    private static final int MY_OTP_REQUEST = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    //    SmsbroadCastReciever smsbroadCastReciever;
//    private static final int REQ_USER_CONSENT = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(OtpGenerate.this, R.layout.activity_otp_generate);
        mAuth = FirebaseAuth.getInstance();
//        checkPermission();
        GetNumber();
        binding.getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.enterNumber.getText().toString())) {
                    Toast.makeText(OtpGenerate.this, "Please enter the valid number", Toast.LENGTH_LONG).show();
                } else {
                    String pin = "+91" + binding.enterNumber.getText().toString();
                    sentVerification(pin);
                }
            }
        });

        binding.verifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.enterOtp.getText().toString())) {
                    Toast.makeText(OtpGenerate.this, "OTP number is incorrect.!", Toast.LENGTH_LONG).show();
                } else {
                    verifyCode(binding.enterOtp.getText().toString());
                }
            }
        });

    }

    // this is used by broadcast receiver and its on progress still need to verify the correct format
    /*private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        String number = "+91"+binding.enterNumber.getText().toString();
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(aVoid->{

        });
        task.addOnFailureListener(e->{

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                binding.textMessage.setText(
                        String.format("%s - %s", getString(R.string.received_message), message));
                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            binding.enterOtp.setText(matcher.group(0));
        }
    }

    private void registerBroadcastReceiver() {
        smsbroadCastReciever = new SmsbroadCastReciever();
        smsbroadCastReciever.lisitener = new SmsbroadCastReciever.SmsBroadCastLisitener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent,REQ_USER_CONSENT);
            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsbroadCastReciever, intentFilter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsbroadCastReciever);
    }

     */

    private void signWithCredentials(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(OtpGenerate.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(OtpGenerate.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verification_ID = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                binding.enterOtp.setText(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpGenerate.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void sentVerification(String phone_number) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phone_number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_ID, code);
        signWithCredentials(credential);
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_OTP_REQUEST);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_OTP_REQUEST: {
//                if (permissions[0].equalsIgnoreCase
//                        (Manifest.permission.SEND_SMS)
//                        && grantResults[0] ==
//                        PackageManager.PERMISSION_GRANTED) {
//                    // Permission was granted. Enable sms button.
//                } else {
//                    // Permission denied.
////                    Log.d(TAG, getString(R.string.failure_permission));
//                    Toast.makeText(this,
//                            "Permission failed.!",
//                            Toast.LENGTH_LONG).show();
//                    // Disable the sms button.
////                    disableSmsButton();
//                }
//            }
//        }
//    }

    public void GetNumber() {

        String[] permission = {
                Manifest.permission.READ_PHONE_NUMBERS
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, 102);
        }

        TelephonyManager mTeleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        TelephonyManager mTeleManager1 = (TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        binding.textMessage.setText(mTeleManager1.getLine1Number());
        Log.i(TAG, "GetNumber: "+mTeleManager1.getLine1Number());
    }

}