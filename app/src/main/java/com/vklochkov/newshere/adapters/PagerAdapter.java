package com.vklochkov.newshere.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vklochkov.newshere.fragments.NewsSourcesListFragment;
import com.vklochkov.newshere.fragments.SearchBarFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private NewsSourcesListFragment newsSourcesListFragment;
    private SearchBarFragment searchBarFragment;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                this.newsSourcesListFragment = new NewsSourcesListFragment();
                return this.newsSourcesListFragment;
            case 1:
                this.searchBarFragment = new SearchBarFragment();
                return this.searchBarFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTabs;
    }
}
