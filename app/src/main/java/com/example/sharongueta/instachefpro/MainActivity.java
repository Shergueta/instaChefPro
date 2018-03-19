package com.example.sharongueta.instachefpro;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

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
        sectionsPageAdapter.addFragment(new HomeFragment() );

        viewPager.setAdapter(sectionsPageAdapter);

    }

    private void drawIconsOnTabs(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_search);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_menu_myplaces);
        tabLayout.getTabAt(2).setIcon(android.R.drawable.ic_input_add);
        tabLayout.getTabAt(3).setIcon(android.R.drawable.ic_menu_mapmode);
        tabLayout.getTabAt(4).setIcon(android.R.drawable.ic_menu_recent_history);
    }
}