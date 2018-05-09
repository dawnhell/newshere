package com.vklochkov.newshere.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.models.NewsSourceItem;

import java.util.ArrayList;

public class NewsSourcesAdapter extends ArrayAdapter<NewsSourceItem> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<NewsSourceItem> data;

    public NewsSourcesAdapter (Context context, int layoutResourceId, ArrayList<NewsSourceItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = row.findViewById(R.id.source_title);
            holder.image = row.findViewById(R.id.source_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        NewsSourceItem item = data.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageResource(item.getImageId());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
