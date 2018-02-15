package com.vklochkov.newshere.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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
}
