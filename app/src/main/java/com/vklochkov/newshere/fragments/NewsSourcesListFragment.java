package com.vklochkov.newshere.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private final String[] NEWS_API_KEYS = {
        "reddit-r-all",
        "hacker-news",
        "the-next-web",
        "usa-today",
        "lenta",
        "mtv-news",
        "national-geographic",
        "news24",
        "the-verge",
        "wired",
        "abc-news",
        "fortune",
        "four-four-two",
        "fox-news",
        "fox-sports",
        "new-scientist",
        "medical-news-today"
    };

    private final String[] NEWS_TITLES = {
        "Reddit All News",
        "Hacker News",
        "The Next Web News",
        "USA Today News",
        "Lenta.ru News",
        "MTV News",
        "National Geographic News",
        "News24",
        "The Verge News",
        "Wired News",
        "ABC News",
        "Fortune News",
        "Four Four Two News",
        "Fox News",
        "Fox Sports News",
        "New Scientist News",
        "Medical News Today"
    };

    private final int[] NEWS_ICONS = {
        R.drawable.reddit_icon,
        R.drawable.hacker_news_icon,
        R.drawable.the_next_web_icon,
        R.drawable.usa_today_icon,
        R.drawable.lenta_icon,
        R.drawable.mtv_icon,
        R.drawable.nat_geo_icon,
        R.drawable.news24_icon,
        R.drawable.the_verge_icon,
        R.drawable.wired_icon,
        R.drawable.abc_news_icon,
        R.drawable.fortune_icon,
        R.drawable.four_four_two_icon,
        R.drawable.fox_news_icon,
        R.drawable.fox_sports_icon,
        R.drawable.new_scientist_icon,
        R.drawable.mnt_icon
    };

    private GridView gridView;
    private NewsSourcesAdapter newsSourcesAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity self = this.getActivity();
        View view = inflater.inflate(R.layout.news_sources_list_fragment, container, false);

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
