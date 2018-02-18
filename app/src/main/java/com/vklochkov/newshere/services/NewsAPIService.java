package com.vklochkov.newshere.services;

import android.util.Log;

import com.vklochkov.newshere.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsAPIService {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private final String API_KEY = "9019330b938f4c5891909f999d472473";

    public ArrayList<Article> getArticlesBySourceAndPage (String source, int page) throws IOException {
        String url = "https://newsapi.org/v2/top-headlines?sources=" + source  + "&page=" + page + "&apiKey=" + API_KEY;
        return parseArticlesFromString(getArticlesByUrl(url));
    }

    public ArrayList<Article> getArticlesByRequestAndPage (String request, int page) throws IOException {
        String url = "https://newsapi.org/v2/everything?q=" + request + "&page=" + page + "&apiKey=" + API_KEY + "&language=en";
        Log.d("NEASAI", getArticlesByUrl(url));
        return parseArticlesFromString(getArticlesByUrl(url));
    }

    public String getArticlesByUrl(final String url) throws IOException {
        Request request = new Request.Builder()
            .url(url)
            .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public ArrayList<Article> parseArticlesFromString (String result) {
        ArrayList<Article> articleArrayList = new ArrayList<Article>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray articlesJSONArray = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articlesJSONArray.length(); ++i) {
                JSONObject article = articlesJSONArray.getJSONObject(i);
                articleArrayList.add(new Article(
                    article.getString("title"),
                    article.getString("description"),
                    article.getString("url"),
                    article.getString("urlToImage"),
                    article.getString("publishedAt"),
                    article.getString("author")
                ));
            }
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }

        return articleArrayList;
    }
}
