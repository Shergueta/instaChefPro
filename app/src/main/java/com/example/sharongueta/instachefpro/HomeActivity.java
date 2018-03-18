package com.example.sharongueta.instachefpro;

import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {


    private static final String TAG = "HomeActivity";

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupAndInitTabs();
    
    }

    private void setupAndInitTabs() {


        viewPager=(ViewPager)findViewById(R.id.container);
        setupViewAdapter(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        drawIconsOnTabs(tabLayout);

    }

    private void setupViewAdapter(ViewPager viewPager) {

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        sectionsPageAdapter.addFragment(new SearchFragment());
        sectionsPageAdapter.addFragment(new ProfileFragment());
        sectionsPageAdapter.addFragment(new AddFragment());
        sectionsPageAdapter.addFragment(new MapFragment());

        viewPager.setAdapter(sectionsPageAdapter);

    }

    private void drawIconsOnTabs(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_search);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_menu_myplaces);
        tabLayout.getTabAt(2).setIcon(android.R.drawable.ic_input_add);
        tabLayout.getTabAt(3).setIcon(android.R.drawable.ic_menu_mapmode);
    }
}