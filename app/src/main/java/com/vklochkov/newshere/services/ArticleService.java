package com.vklochkov.newshere.services;

import android.app.Service;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.vklochkov.newshere.models.Article;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ArticleService extends Service {
    private final IBinder iBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public ArticleService getService () {
            return ArticleService.this;
        }
    }

    @Override
    public void onCreate () {

    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.i("ArticleService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind (Intent intent) {
        return iBinder;
    }

    @Override
    public void onDestroy () {
        Toast.makeText(this, "Article service destroyed!", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<Article> getArticles() {
        final ArrayList<Article> articleArrayList = new ArrayList<Article>();
        try {
            new Thread(new Runnable() {
                @Override
                public void run () {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet("https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=9019330b938f4c5891909f999d472473");
                    HttpResponse response;
                    try {
                        response = client.execute(request);
                        StatusLine statusLine = response.getStatusLine();
                        int code = statusLine.getStatusCode();
                        String ans = "" + code;

                        Log.d("Response of GET request", ans.toString());
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        response.getEntity().writeTo(out);
                        String res = out.toString();
                        JSONObject jsonObject = new JSONObject(res);
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
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (JSONException jsone) {
                        jsone.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return articleArrayList;
    }
}
