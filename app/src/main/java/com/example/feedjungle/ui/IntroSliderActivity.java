package com.example.feedjungle.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivityIntroSliderBinding;
import com.example.feedjungle.ui.navigations.DashboardActivity;
import com.example.feedjungle.utils.CommonFunction;
import com.example.feedjungle.utils.PermissionUtils;

public class IntroSliderActivity extends AppCompatActivity {
    ActivityIntroSliderBinding binding;
    private TextView[] dots;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LocationManager lManager;
    private String latitude = "", longitude = "";
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1000;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_LOCATION = 1000;
    private CommonFunction mCommonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(IntroSliderActivity.this, R.layout.activity_intro_slider);
        mCommonUtils = new CommonFunction(IntroSliderActivity.this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        layouts = new int[]{
                R.layout.page_01,
                R.layout.page_02,
                R.layout.page_03
        };

        addBottomDots(0);
        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        binding.viewPager.setAdapter(myViewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(viewOnPageListener);


        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);



        binding.nextDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+2);
                if (current < layouts.length) {
                    binding.viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorInActive = getResources().getIntArray(R.array.array_dot_inactive);
        binding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("•"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.hint_cl));
            binding.layoutDots.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[currentPage].setTextColor(getResources().getColor(R.color.main_cl));
        }

    }

    private int getItem(int i) {
        return binding.viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        if(!checkPermission()){
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Allow Permission")
                    .setMessage("Please allow all permission to use uninterrupted Services.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            requestPermission();
                        }
                    })
                    .show();
        }else{
            onloadNextPage();
            Toast.makeText(IntroSliderActivity.this, "Successfull", Toast.LENGTH_LONG).show();
        }

    }

    ViewPager.OnPageChangeListener viewOnPageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                binding.nextDots.setText("Go.!");
            } else if (position == layouts.length + 1) {
                binding.nextDots.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    //location provider

    private void locationSignal() {
        lManager = (LocationManager) getSystemService(Context.LOCALE_SERVICE);
        if (!lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getGPS();
        } else {
            getLocation();
        }
    }

    private void getGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean checkPermission()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED  )
        {
            //  return true;
            return false;
        }
        else
        {
            // return false;
            return true;
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locationGPS = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationGPS != null) {
            double lat = locationGPS.getLatitude();
            double longi = locationGPS.getLongitude();
            latitude = String.valueOf(lat);
            longitude = String.valueOf(longi);
            // showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);

            // Toast.makeText(this, "Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(IntroSliderActivity.this, "Unable to find location.", Toast.LENGTH_SHORT).show();
        }

    }


    private void requestPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED )
        {
            //You can show permission rationale if shouldShowRequestPermissionRationale() returns true.
            //I will skip it for this demo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.neverAskAgainSelected(this, Manifest.permission.ACCESS_FINE_LOCATION) )
                {
                    displayNeverAskAgainDialog();
                } else
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.VIBRATE,Manifest.permission.ACCESS_NETWORK_STATE},
                            SEND_SMS_PERMISSION_REQUEST_CODE);

                }
            }

        }
        else
        {
            // onLoadNextPage();
            onloadNextPage();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // onLoadNextPage();
                    onloadNextPage();
                } else {
                    displayNeverAskAgainDialog();
                }
                break;
        }
    }

    private void onloadNextPage() {
        mCommonUtils.navigation(IntroSliderActivity.this, DashboardActivity.class);
        finish();
    }

    private void displayNeverAskAgainDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Please allow all Permissions to use the app.Please permit the permission through Settings screen."
                + "\n\nSelect Permissions -> Enable permission");
        builder.setCancelable(false);
        builder.setPositiveButton("Permit Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.show();
    }

}
