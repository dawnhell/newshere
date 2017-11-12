package com.vklochkov.newshere.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.adapters.ArticleListAdapter;
import com.vklochkov.newshere.models.Article;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vladislav on 11/11/17.
 */

public class ArticleListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.article_list_fragment, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.article_list);

        ArrayList<Article> articleArrayList = Article.getArticles();
        ArticleListAdapter articleListAdapter = new ArticleListAdapter(getContext(), articleArrayList);
        listView.setAdapter(articleListAdapter);

        return view;
    }
}
