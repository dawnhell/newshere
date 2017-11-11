package com.vklochkov.newshere.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vklochkov.newshere.R;

public class SearchBarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.search_bar_fragment, container, false);
    }
}
