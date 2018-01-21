package com.vklochkov.newshere.fragments;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.services.ArticleService;

public class SearchBarFragment extends Fragment {
    private ArticleService articleService;
    private boolean isServiceBound;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        bindSearchBtn(container);
        return layoutInflater.inflate(R.layout.search_bar_fragment, container, false);
    }

    private void bindSearchBtn (final ViewGroup viewGroup) {
        Button searchBtn = (Button) viewGroup.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                EditText searchText = (EditText) viewGroup.findViewById(R.id.searchText);
                String request = searchText.getText().toString();
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            articleService = ((ArticleService.LocalBinder) service).getService();

            Toast.makeText(getActivity(), "Article Service connected", Toast.LENGTH_LONG).show();
            isServiceBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            articleService = null;
            isServiceBound = false;
            Toast.makeText(getActivity(), "Article Service disconnected", Toast.LENGTH_LONG).show();
        }
    };


    void doBindService() {
        getActivity().bindService(new Intent(getActivity(), ArticleService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    void doUnbindService() {
        if (isServiceBound) {
            getActivity().unbindService(serviceConnection);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}