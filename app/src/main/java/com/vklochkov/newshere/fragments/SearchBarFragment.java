package com.vklochkov.newshere.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vklochkov.newshere.R;
import com.vklochkov.newshere.activities.ArticleActivity;
import com.vklochkov.newshere.adapters.ArticleListAdapter;
import com.vklochkov.newshere.models.Article;
import com.vklochkov.newshere.services.NewsAPIService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchBarFragment extends Fragment {
    NewsAPIService     newsAPIService = new NewsAPIService();
    ArticleListAdapter adapter;
    ProgressBar        spinner;
    ListView           articleListView;
    EditText           searchText;
    Button             searchBtn;
    Calendar           calendar;
    EditText           sortDateFrom;
    EditText           sortDateTo;
    EditText           activeEditText;
    RadioGroup         sortByRadioGroup;
    String             sortByKey = "publishedAt";
    String             sortFrom = "";
    String             sortTo = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_bar_fragment, container, false);

        spinner          = view.findViewById(R.id.progress_bar);
        articleListView  = view.findViewById(R.id.article_list);
        searchText       = view.findViewById(R.id.searchText);
        searchBtn        = view.findViewById(R.id.searchBtn);
        calendar         = Calendar.getInstance();
        sortDateFrom     = view.findViewById(R.id.sort_date_from);
        sortDateTo       = view.findViewById(R.id.sort_date_to);
        sortByRadioGroup = view.findViewById(R.id.radioGroup);

        spinner.setVisibility(View.GONE);
        bindSearchButton();
        bindFilterButtons();
        setRadioBtnOnClickListener();

        return view;
    }

    private void bindSearchButton () {
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
                        emitter.onNext(newsAPIService.getArticlesByRequest(request, 1, sortByKey, sortFrom, sortTo));
                        emitter.onComplete();
                    }
                })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<Article>>() {
                        @Override
                        public void onSubscribe (Disposable d) {
                            spinner.setVisibility(View.VISIBLE);
                            articleListView.setAdapter(null);
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
        adapter = new ArticleListAdapter(getContext(), articleArrayList);
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

    private void bindFilterButtons () {
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                String date = sdf.format(calendar.getTime());

                activeEditText.setText(date);

                if (activeEditText.getId() == R.id.sort_date_from) {
                    sortFrom = date;
                    searchBtn.callOnClick();
                } else if (activeEditText.getId() == R.id.sort_date_to) {
                    sortTo = date;
                    searchBtn.callOnClick();
                }
            }
        };

        setDatePickerDialog(sortDateFrom, listener);
        setDatePickerDialog(sortDateTo, listener);
    }

    private void setDatePickerDialog (final EditText editText, final DatePickerDialog.OnDateSetListener listener) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeEditText = editText;

                new DatePickerDialog(
                    getContext(),
                    listener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }

    private void setRadioBtnOnClickListener () {
        sortByRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId) {
                RadioButton button = group.findViewById(checkedId);

                if (null != button && checkedId > -1){
                    switch (group.indexOfChild(button)) {
                        case 1:
                            sortByKey = "popularity";
                            break;

                        case 2:
                            sortByKey = "relevancy";
                            break;

                        case 3:
                            sortByKey = "publishedAt";
                            break;

                        default:
                            sortByKey = "publishedAt";
                    }

                    searchBtn.callOnClick();
                }
            }
        });
    }
}
