package com.vklochkov.newshere.models;

public class Article {
    private String title       = null;
    private String description = null;
    private String url         = null;
    private String imageUrl    = null;
    private String publishedAt = null;
    private String author      = null;
    private String source      = null;

    public Article(
        String title,
        String description,
        String url,
        String imageUrl,
        String publishedAt,
        String author,
        String source
    ) {
        this.title       = title;
        this.description = description;
        this.url         = url;
        this.imageUrl    = imageUrl;
        this.publishedAt = publishedAt;
        this.author      = author;
        this.source      = source;
    }

    public String getTitle () {
        return title;
    }

    public String getDescription () {
        return description;
    }

    public String getUrl () {
        return url;
    }

    public String getImageUrl () {
        return imageUrl;
    }

    public String getPublishedAt () {
        return publishedAt;
    }

    public String getAuthor () {
        return author;
    }

    public String getSource() {
        return source;
    }
}
