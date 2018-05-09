package com.vklochkov.newshere.db;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.vklochkov.newshere.models.Article;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME                = "Articles.db";
    private static final String ARTICLES_TABLE_NAME          = "articles";
    private static final String ARTICLES_COLUMN_ID           = "id";
    private static final String ARTICLES_COLUMN_TITLE        = "title";
    private static final String ARTICLES_COLUMN_DESCRIPTION  = "description";
    private static final String ARTICLES_COLUMN_URL          = "url";
    private static final String ARTICLES_COLUMN_URL_TO_IMAGE = "urlToImage";
    private static final String ARTICLES_COLUMN_PUBLISHED_AT = "publishedAt";
    private static final String ARTICLES_COLUMN_AUTHOR       = "author";
    private static final String ARTICLES_COLUMN_SOURCE       = "source";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "create table if not exists " + ARTICLES_TABLE_NAME + " (" +
                "id integer primary key, " +
                "title text, " +
                "description text, " +
                "url text, " +
                "urlToImage text, " +
                "publishedAt text, " +
                "author text, " +
                "source text " +
            ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ARTICLES_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertArticles (ArrayList<Article> articleArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues;

        for (int i = 0; i < articleArrayList.size(); ++i) {
            contentValues = new ContentValues();
            contentValues.put("title", articleArrayList.get(i).getTitle());
            contentValues.put("description", articleArrayList.get(i).getDescription());
            contentValues.put("url", articleArrayList.get(i).getUrl());
            contentValues.put("urlToImage", articleArrayList.get(i).getImageUrl());
            contentValues.put("publishedAt", articleArrayList.get(i).getPublishedAt());
            contentValues.put("author", articleArrayList.get(i).getAuthor());
            contentValues.put("source", articleArrayList.get(i).getSource());
            db.insert(ARTICLES_TABLE_NAME, null, contentValues);
        }

        return true;
    }

    public Integer deleteArticleBySource (String source) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ARTICLES_TABLE_NAME,
            "source='" + source + "';",
            null);
    }

    public ArrayList<Article> getArticlesBySource (String source) {
        ArrayList<Article> articleArrayList = new ArrayList<Article>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ARTICLES_TABLE_NAME + " where source='" + source + "';", null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            articleArrayList.add(new Article(
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_TITLE)),
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_DESCRIPTION)),
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_URL)),
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_URL_TO_IMAGE)),
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_PUBLISHED_AT)),
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_AUTHOR)),
                res.getString(res.getColumnIndex(ARTICLES_COLUMN_SOURCE))
            ));

            res.moveToNext();
        }

        return articleArrayList;
    }
}
