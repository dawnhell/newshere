package com.vklochkov.newshere.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vklochkov.newshere.MainActivity;
import com.vklochkov.newshere.R;
import com.vklochkov.newshere.activities.SourceNewsActivity;

public class NewsSourcesListFragment extends Fragment {
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_sources_list_fragment, container, false);

        Button abcNewsBtn = (Button) view.findViewById(R.id.abc_news_btn);
        Button bbcNewsBtn = (Button) view.findViewById(R.id.bbc_news_btn);
        Button foxNewsBtn = (Button) view.findViewById(R.id.fox_news_btn);

        this.bindBtnOnClickListener(abcNewsBtn, "ABC News");
        this.bindBtnOnClickListener(bbcNewsBtn, "BBC News");
        this.bindBtnOnClickListener(foxNewsBtn, "Fox News");

        return view;
    }

    private void bindBtnOnClickListener (Button button, final String intentAction) {
        final Activity self = this.getActivity();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(self, SourceNewsActivity.class);
                intent.putExtra("newsSource", intentAction);
                startActivity(intent);
            }
        });
    }
}
