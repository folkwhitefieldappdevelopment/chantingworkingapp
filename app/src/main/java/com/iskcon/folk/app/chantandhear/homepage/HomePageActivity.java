package com.iskcon.folk.app.chantandhear.homepage;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.model.UserDetails;

import java.text.MessageFormat;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {

    private UserDetails userDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);
        Object userDetailsObj = getIntent().getExtras().get("userDetails");
        if (userDetailsObj != null) {
            userDetails = (UserDetails) userDetailsObj;
            ((TextView) findViewById(R.id.homePageUserNameTextView)).setText(MessageFormat.format("Hare Krishna {0}", userDetails.getDisplayName().toUpperCase(Locale.ENGLISH)));
        }

        TabLayout tabLayout = findViewById(R.id.homePageTabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.homePageViewPager);
        HomePageFragmentAdapter homePageFragmentAdapter = new HomePageFragmentAdapter(this);
        viewPager2.setAdapter(homePageFragmentAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}