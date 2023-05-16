package com.example.feedjungle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;
import android.widget.Toast;

public class CommonFunction {
    private Activity mActivity;

    public CommonFunction(Activity cont) {
        mActivity = cont;
    }

    public void navigation(Context currentactivityname, Class<?> nextactivityname) {
        Intent i = new Intent(currentactivityname, nextactivityname);
        currentactivityname.startActivity(i);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        boolean isConnected = netInfo != null && netInfo.isConnected();
        if (isConnected)
            return true;
        else {
            return false;
        }
    }

    public boolean isValidEmail(String email, EditText emailText) {
        String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPatterns)) {
            return true;
        } else {
            emailText.setError("Invalid Email");
            return false;
        }

    }

    public boolean isValidMobileNumber(String mobile, EditText mobileNumberText) {

        String mobileNumberPatterns = "^[56789]\\d{9,9}$";
        if (mobile.matches(mobileNumberPatterns)) {
            return true;
        } else {
            mobileNumberText.setError("Incorrect Mobilenumber");
            return false;
        }
    }

    public boolean isValidConfirmPassword(String confirmPassword, String password, Context mContext) {
        if (password.equals(confirmPassword)) {
            return true;
        } else {
            Toast.makeText(mContext, "Password invalid", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean isEmptyText(String value, Context mContext) {
        if (!value.isEmpty()) {
            return true;
        } else {
            Toast.makeText(mContext, "Error, Empty fields occurs", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
