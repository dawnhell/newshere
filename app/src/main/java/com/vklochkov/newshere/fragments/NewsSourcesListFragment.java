package com.vklochkov.newshere.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.activities.ArticleListActivity;
import com.vklochkov.newshere.adapters.NewsSourcesAdapter;
import com.vklochkov.newshere.models.NewsSourceItem;

import java.util.ArrayList;

public class NewsSourcesListFragment extends Fragment {
    private String[] NEWS_API_KEYS;
    private String[] NEWS_TITLES;
    private int[] NEWS_ICONS;

    private GridView gridView;
    private NewsSourcesAdapter newsSourcesAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity self = this.getActivity();
        View view = inflater.inflate(R.layout.news_sources_list_fragment, container, false);

        NEWS_API_KEYS = getResources().getStringArray(R.array.news_api_keys);
        NEWS_TITLES = getResources().getStringArray(R.array.news_titles);

        TypedArray typedArray = getResources().obtainTypedArray(R.array.news_icons);
        NEWS_ICONS = new int[typedArray.length() + 1];
        for (int i = 0; i < typedArray.length(); ++i) {
            NEWS_ICONS[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();

        gridView = view.findViewById(R.id.grid_view);
        newsSourcesAdapter = new NewsSourcesAdapter(getActivity(), R.layout.news_source_layout, getNewsSources());
        gridView.setAdapter(newsSourcesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(self, ArticleListActivity.class);
                intent.putExtra("title", NEWS_TITLES[position]);
                intent.putExtra("apiKey", NEWS_API_KEYS[position]);
                startActivity(intent);
            }
        });

        return view;
    }

    private ArrayList<NewsSourceItem> getNewsSources () {
        final ArrayList<NewsSourceItem> newsSourceItems = new ArrayList<>();
        for (int i = 0; i < NEWS_TITLES.length; i++) {
            newsSourceItems.add(new NewsSourceItem(NEWS_ICONS[i], NEWS_TITLES[i]));
        }
        return newsSourceItems;
    }
}
