package com.example.eventifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{

    ActionBar toolbar;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        bottomNav = findViewById(R.id.navigationView);
        loadFragment(new fragment_home());
        bottomNav.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.home:
                fragment = new fragment_home();
                break;
            case R.id.events:
                fragment = new fragment_events();
                break;
            case R.id.profile:
                fragment = new fragment_profile();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){

        if(fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            Bundle bundle=new Bundle();
            bundle.putString("sap_id", getIntent().getStringExtra("sap_id"));
            Log.d("mainactivityid",getIntent().getStringExtra("sap_id"));
            fragment.setArguments(bundle);
        }

        return false;
    }
}