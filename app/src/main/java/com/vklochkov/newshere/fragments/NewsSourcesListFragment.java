package com.vklochkov.newshere.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.activities.ArticleListActivity;

public class NewsSourcesListFragment extends Fragment {
    private final String ABC_NEWS_API = "abc-news";
    private final String BBC_NEWS_API = "bbc-news";
    private final String FOX_NEWS_API = "fox-news";

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_sources_list_fragment, container, false);

        Button abcNewsBtn = view.findViewById(R.id.abc_news_btn);
        Button bbcNewsBtn = view.findViewById(R.id.bbc_news_btn);
        Button foxNewsBtn = view.findViewById(R.id.fox_news_btn);

        this.bindBtnOnClickListener(abcNewsBtn, "ABC News", ABC_NEWS_API);
        this.bindBtnOnClickListener(bbcNewsBtn, "BBC News", BBC_NEWS_API);
        this.bindBtnOnClickListener(foxNewsBtn, "Fox News", FOX_NEWS_API);

        return view;
    }

    private void bindBtnOnClickListener (Button button, final String title, final String apiKey) {
        final Activity self = this.getActivity();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(self, ArticleListActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("apiKey", apiKey);
                startActivity(intent);
            }
        });
    }
}
