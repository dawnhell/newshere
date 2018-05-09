package com.vklochkov.newshere.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.adapters.ArticleListAdapter;
import com.vklochkov.newshere.db.DatabaseHelper;
import com.vklochkov.newshere.models.Article;
import com.vklochkov.newshere.services.NewsAPIService;

import java.io.IOException;
import java.util.*;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArticleListActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private ListView articleListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseHelper dbHelper;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        setTitle(getIntent().getStringExtra("title"));

        dbHelper = new DatabaseHelper(this);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        articleListView = findViewById(R.id.article_list);
        spinner = findViewById(R.id.progress_bar);
        spinner.setVisibility(View.GONE);

        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (isConnected) {
            try {
                getArticleList(articleListView, true);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            getArticleListFromDB();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                swipeRefreshLayout.setRefreshing(false);

                networkInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

                if (isConnected) {
                    try {
                        getArticleList(articleListView, false);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else {
                    getArticleListFromDB();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getArticleListFromDB() {
        ArrayList<Article> articleArrayList = dbHelper.getArticlesBySource(getIntent().getStringExtra("apiKey"));
        addArticlesToList(articleArrayList, articleListView);
    }

    private void setTitle (String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    private void getArticleList (final ListView articleListView, final boolean shouldShowSpinner) throws IOException {
        final NewsAPIService newsAPIService = new NewsAPIService();
        final String apiKey = getIntent().getStringExtra("apiKey");

        Observable.create(new ObservableOnSubscribe<ArrayList<Article>>() {
            @Override
            public void subscribe (@NonNull ObservableEmitter<ArrayList<Article>> emitter) throws Exception {
                emitter.onNext(newsAPIService.getArticlesBySource(apiKey, 1));
                emitter.onComplete();
            }
        })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<ArrayList<Article>>() {
                @Override
                public void onSubscribe (Disposable d) {
                    if (shouldShowSpinner) {
                        spinner.setVisibility(View.VISIBLE);
                    } else {
                        spinner.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onNext (ArrayList<Article> articleArrayList) {
                    addArticlesToList(articleArrayList, articleListView);
                    saveArticlesToDB(articleArrayList);
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

    private void saveArticlesToDB(ArrayList<Article> articleArrayList) {
        if (dbHelper.getArticlesBySource(getIntent().getStringExtra("apiKey")).size() > 0) {
            dbHelper.deleteArticleBySource(getIntent().getStringExtra("apiKey"));
        }
        dbHelper.insertArticles(articleArrayList);
    }

    private void addArticlesToList (final ArrayList<Article> articleArrayList, ListView articleListView) {
        final Activity self = this;
        ArticleListAdapter adapter = new ArticleListAdapter(this, articleArrayList);
        articleListView.setAdapter(adapter);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(self, ArticleActivity.class);
                intent.putExtra("url", articleArrayList.get(position).getUrl());
                intent.putExtra("title", getIntent().getStringExtra("title"));
                startActivity(intent);
            }
        });
    }
}
