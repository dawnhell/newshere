package com.vklochkov.newshere.models;

import java.util.ArrayList;

/**
 * Created by vladislav on 11/12/17.
 */

public class Article {
    private String title            = null;
    private String shortDescription = null;
    private String imageUrl         = null;

    public static ArrayList<Article> getArticles() {
        ArrayList<Article> articleArrayList = new ArrayList<Article>();
        articleArrayList.add(new Article("Article 1", "Desc 1", "img 1"));
        articleArrayList.add(new Article("Article 2", "Desc 2", "img 2"));
        articleArrayList.add(new Article("Article 3", "Desc 3", "img 3"));
        articleArrayList.add(new Article("Article 11", "Desc 11", "img 11"));
        articleArrayList.add(new Article("Article 21", "Desc 21", "img 21"));
        articleArrayList.add(new Article("Article 31", "Desc 31", "img 31"));
        articleArrayList.add(new Article("Article 12", "Desc 12", "img 12"));
        articleArrayList.add(new Article("Article 22", "Desc 22", "img 22"));
        articleArrayList.add(new Article("Article 32", "Desc 32", "img 32"));
        articleArrayList.add(new Article("Article 13", "Desc 13", "img 13"));
        articleArrayList.add(new Article("Article 23", "Desc 23", "img 23"));
        articleArrayList.add(new Article("Article 33", "Desc 33", "img 33"));

        return articleArrayList;
    }

    public Article(String title, String shortDescription, String imageUlr) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUlr;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getShortDescription () {
        return shortDescription;
    }

    public void setShortDescription (String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageUrl () {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
