package com.example.sleep;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tab_title_list;
    private ArrayList<Fragment> fragment_list;

    public MyFragmentPagerAdapter(FragmentManager fm , ArrayList<String> tab_title_list, ArrayList<Fragment> fragment_list) {
        super(fm);
        this.tab_title_list = tab_title_list;
        this.fragment_list = fragment_list;

    }
    @Override
    public int getCount() {
        return fragment_list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return  fragment_list.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title_list.get(position);
    }
}
