package com.example.feedjungle.ui.navigations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.feedjungle.R;
import com.example.feedjungle.databinding.ActivityDashboardBinding;
import com.example.feedjungle.ui.fragment.MenuFragment;
import com.example.feedjungle.ui.fragment.MoreFragment;
import com.example.feedjungle.ui.fragment.OfferFragment;
import com.example.feedjungle.ui.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    ActivityDashboardBinding binding;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.bottomNavi.setSelectedItemId(R.id.menu);

    }

    MenuFragment menuFragment = new MenuFragment();
    OfferFragment offerFragment = new OfferFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    MoreFragment moreFragment = new MoreFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, menuFragment).commit();
                return true;
            case R.id.offer:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, offerFragment).commit();
                return true;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, profileFragment).commit();
                return true;
            case R.id.more:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, moreFragment).commit();
                return true;
        }
        return false;
    }
}