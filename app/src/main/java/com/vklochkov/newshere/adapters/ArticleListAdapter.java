package com.vklochkov.newshere.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.models.Article;

import java.util.ArrayList;

public class ArticleListAdapter extends ArrayAdapter<Article> {
    public ArticleListAdapter(Context context, ArrayList<Article> articleArrayList) {
        super(context, 0, articleArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_fragment, container, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.article_title);
        TextView description = (TextView) convertView.findViewById(R.id.article_preview);

        title.setText(article.getTitle());
        description.setText(article.getShortDescription());

        return convertView;
    }
}
