package com.example.newsapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentsAdapter adapter;
    ProgressBar prg;
    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_comments);
        getSupportActionBar().setTitle("Comments");
        recyclerView = findViewById(R.id.recyclerViewList);
        prg = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommentsAdapter();
        recyclerView.setAdapter(adapter);
        prg.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        // Get the newsId from the intent
        newsId = getIntent().getIntExtra("newsId", 0);

        // Make a network request to get the list of comments for this newsId
        CommentsRepo repo = new CommentsRepo();
        repo.getCommentsbyNewsId(Executors.newSingleThreadExecutor(), new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                List<Comments> comments = (List<Comments>) msg.obj;
                adapter.setComments(comments);
                prg.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, newsId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_comments_bar, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.write_comments) {
            // Handle the show comments action
            Intent intent = new Intent(this, PostCommentsActivity.class);
            intent.putExtra("newsId", newsId );
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(this, ActivityDetails.class);
            intent.putExtra("id", newsId );
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

