package com.example.newsapp2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoryPagerAdapter extends FragmentStateAdapter {

    private List<Categories> categories;

    public NewsCategoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.categories = new ArrayList<>();
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public Categories getCategory(int position) {
        return categories.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Categories category = categories.get(position);
        return NewsCategoryFragment.newInstance(category.getId());
    }

    @Override
    public int getItemCount() {
        if (categories!= null){
            return categories.size();
        }
        return 0;
    }
}
