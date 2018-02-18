package com.vklochkov.newshere.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.activities.ArticleActivity;
import com.vklochkov.newshere.adapters.ArticleListAdapter;
import com.vklochkov.newshere.models.Article;
import com.vklochkov.newshere.services.NewsAPIService;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchBarFragment extends Fragment {
    NewsAPIService newsAPIService = new NewsAPIService();
    ProgressBar spinner;
    ListView articleListView;
    EditText searchText;
    Button searchBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_bar_fragment, container, false);

        spinner = view.findViewById(R.id.progress_bar);
        spinner.setVisibility(View.GONE);
        articleListView = view.findViewById(R.id.article_list);
        searchText = view.findViewById(R.id.searchText);
        searchBtn = view.findViewById(R.id.searchBtn);

        bindSearchBtn();

        return view;
    }

    private void bindSearchBtn () {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                final String request = searchText.getText().toString();

                Observable.create(new ObservableOnSubscribe<ArrayList<Article>>() {
                    @Override
                    public void subscribe (@NonNull ObservableEmitter<ArrayList<Article>> emitter) throws Exception {
                        emitter.onNext(newsAPIService.getArticlesByRequestAndPage(request, 1));
                        emitter.onComplete();
                    }
                })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<Article>>() {
                        @Override
                        public void onSubscribe (Disposable d) {
                            spinner.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext (ArrayList<Article> articleArrayList) {
                            addArticlesToList(articleArrayList, articleListView);
                        }

                        @Override
                        public void onError (Throwable e) {
                            e.printStackTrace();
                            Log.e("ERROR", e.getMessage());
                        }

                        @Override
                        public void onComplete () {
                            spinner.setVisibility(View.GONE);
                        }
                    });
            }
        });
    }


    private void addArticlesToList (final ArrayList<Article> articleArrayList, ListView articleListView) {
        final Activity self = getActivity();
        ArticleListAdapter adapter = new ArticleListAdapter(getContext(), articleArrayList);
        articleListView.setAdapter(adapter);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(self, ArticleActivity.class);
                intent.putExtra("url", articleArrayList.get(position).getUrl());
                intent.putExtra("title", "Search");
                startActivity(intent);
            }
        });
    }
}
