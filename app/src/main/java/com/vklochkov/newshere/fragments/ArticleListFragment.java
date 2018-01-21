package com.vklochkov.newshere.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.services.ArticleService;

public class ArticleListFragment extends ListFragment implements AdapterView.OnItemClickListener{
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.article_list_fragment, container, false);

        ListView articleListView = view.findViewById(R.id.article_list);


        /*ArrayList<Article> articleArrayList = articleService.getArticles();
        for (int i = 0; i < articleArrayList.size(); ++i) {
            ArticleFragment articleFragment = new ArticleFragment();

            TextView articleTitle = (TextView) articleFragment.getActivity().findViewById(R.id.article_title);
            articleTitle.setText(articleArrayList.get(i).getTitle());

            TextView articlePreview = (TextView) articleFragment.getActivity().findViewById(R.id.article_preview);
            articlePreview.setText(articleArrayList.get(i).getDescription());

            articleListView.addView(articleFragment.getView());

            Log.i("@@@@@@", articleArrayList.get(i).getTitle() + "____________+++++++++++++++++");
        }*/

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.Planets, android.R.layout.simple_list_item_1);
//        setListAdapter(adapter);
//        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
