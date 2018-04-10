package com.example.sharongueta.instachefpro.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.sharongueta.instachefpro.Fragments.AddFragment;
import com.example.sharongueta.instachefpro.Fragments.HomeFragment;
import com.example.sharongueta.instachefpro.Fragments.MapsFragment;
import com.example.sharongueta.instachefpro.Fragments.ProfileFragment;
import com.example.sharongueta.instachefpro.Fragments.SearchFragment;
import com.example.sharongueta.instachefpro.R;
import com.example.sharongueta.instachefpro.SectionsPageAdapter;

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

        sectionsPageAdapter.addFragment(new ProfileFragment());
        sectionsPageAdapter.addFragment(new SearchFragment());
        sectionsPageAdapter.addFragment(new AddFragment());
        sectionsPageAdapter.addFragment(new MapsFragment());
        sectionsPageAdapter.addFragment(new HomeFragment() );

        viewPager.setAdapter(sectionsPageAdapter);

    }

    private void drawIconsOnTabs(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.search_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.add_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.locaition_icon);
        tabLayout.getTabAt(4).setIcon(R.drawable.home_icon);
    }
}