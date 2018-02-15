package com.vklochkov.newshere.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vklochkov.newshere.R;

public class SearchBarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_bar_fragment, container, false);

        bindSearchBtn(view);

        return view;
    }

    private void bindSearchBtn (final View view) {
        Button searchBtn = (Button) view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                EditText searchText = (EditText) view.findViewById(R.id.searchText);
                String request = searchText.getText().toString();
            }
        });
    }
}
