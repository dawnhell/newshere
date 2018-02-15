package com.vklochkov.newshere.adapters;

import android.content.Context;
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
    private final Context context;
    private final ArrayList<Article> articles;

    public ArticleListAdapter (Context context, ArrayList<Article> articles) {
        super (context, -1, articles);
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.article_fragment, parent, false);

        TextView title = rowView.findViewById(R.id.article_title);
        title.setText(articles.get(position).getTitle());

        TextView description = rowView.findViewById(R.id.article_description);
        description.setText(articles.get(position).getDescription().length() > 0 ? articles.get(position).getDescription() : "No description");

        TextView createdAt = rowView.findViewById(R.id.article_created_at);
        String publishedAt = articles.get(position).getPublishedAt();
        createdAt.setText(publishedAt.substring(0, publishedAt.indexOf("T")));

        return rowView;
    }
}
