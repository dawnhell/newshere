package com.vklochkov.newshere.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vklochkov.newshere.fragments.ArticleListFragment;
import com.vklochkov.newshere.fragments.NewsSourcesListFragment;
import com.vklochkov.newshere.fragments.SearchBarFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewsSourcesListFragment newsSourcesListFragment = new NewsSourcesListFragment();
                return newsSourcesListFragment;
            case 1:
//                ArticleListFragment articleListFragment = new ArticleListFragment();
                SearchBarFragment searchBarFragment = new SearchBarFragment();
                return searchBarFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
