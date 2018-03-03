package com.vklochkov.newshere.services;

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

    public ArrayList<Article> getArticlesBySource (String source, int page) throws IOException {
        String url = "https://newsapi.org/v2/top-headlines?sources=" + source  + "&page=" + page + "&apiKey=" + API_KEY;
        return parseArticlesFromString(getArticlesByUrl(url));
    }

    public ArrayList<Article> getArticlesByRequest (String request, int page, String sortBy, String from, String to) throws IOException {
        String append = (sortBy.length() > 0 ? "&sortBy=" + sortBy : "")
            + (from.length() > 0 ? "&from=" + from : "")
            + (to.length() > 0 ? "&to=" + to : "");

        String url = "https://newsapi.org/v2/everything?q=" + request + "&page=" + page + "&apiKey=" + API_KEY + "&pageSize=30" + append;
        return parseArticlesFromString(getArticlesByUrl(url));
    }

    private String getArticlesByUrl(final String url) throws IOException {
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
