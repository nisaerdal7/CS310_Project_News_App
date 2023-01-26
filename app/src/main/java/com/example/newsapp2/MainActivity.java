package com.example.newsapp2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity{


    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("CS310 News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_newspaper_24);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        NewsCategoryPagerAdapter pagerAdapter = new NewsCategoryPagerAdapter(this);

        // Set up the ViewPager2 with a pager adapter

        viewPager.setAdapter(pagerAdapter);

        // Connect the TabLayout and ViewPager2
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            Categories category = pagerAdapter.getCategory(position);
            tab.setText(category.getName());
        });
        tabLayoutMediator.attach();

        // Get the list of categories
        NewsRepository repo = new NewsRepository();
        repo.getAllCategories(Executors.newSingleThreadExecutor(), new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                List<Categories> categories = (List<Categories>) msg.obj;
                Log.d("main", categories.get(0).getName());
                pagerAdapter.setCategories(categories);
            }
        });
    }


}