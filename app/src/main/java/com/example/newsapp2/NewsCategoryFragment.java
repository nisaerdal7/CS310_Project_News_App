package com.example.newsapp2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

public class NewsCategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private int categoryId;
    ProgressBar prg;

    public static NewsCategoryFragment newInstance(int categoryId) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt("categoryId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_category, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        // Make a network request to get the list of news for this category
        prg = view.findViewById(R.id.progressBarList);
        prg.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        getNewsByCategoryId(categoryId);

        return view;
    }

    private void getNewsByCategoryId(int categoryId) {
        // Make a network request to get the list of news for this category
        NewsRepository repo = new NewsRepository();
        repo.getDataByCategoryId(Executors.newSingleThreadExecutor(), new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                List<News> news = (List<News>) msg.obj;
                recyclerView.setVisibility(View.VISIBLE);
                prg.setVisibility(View.INVISIBLE);
                adapter.setNews(news);
            }
        }, categoryId);
    }
}
