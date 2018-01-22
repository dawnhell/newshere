package com.vklochkov.newshere.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vklochkov.newshere.R;

public class NewsSourcesListFragment extends Fragment {
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_sources_list_fragment, container, false);

        return view;
    }
}
