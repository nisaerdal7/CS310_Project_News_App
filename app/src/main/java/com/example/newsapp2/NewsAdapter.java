package com.example.newsapp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> news;

    public NewsAdapter() {

    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView dateTextView;
        ImageView imageView;
        News currentNews;

        public NewsViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.title_text_view);
            dateTextView = v.findViewById(R.id.date_text_view);
            imageView = v.findViewById(R.id.image_view);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ActivityDetails.class);
            intent.putExtra("id", currentNews.getId());
            context.startActivity(intent);
        }



    }

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row_layout, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News currentNews = news.get(position);
        holder.titleTextView.setText(currentNews.getTitle());
        holder.dateTextView.setText(currentNews.getDate());
        // TODO: Download and set the image using the "imagelink" field
        NewsRepository repo = new NewsRepository();
        repo.downloadImage(Executors.newSingleThreadExecutor(), new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bitmap image = (Bitmap) msg.obj;
                holder.imageView.setImageBitmap(image);
            }
        }, currentNews.getImg());
        holder.currentNews = currentNews;

    }


    @Override
    public int getItemCount() {
        return  news != null ? news.size() : 0;
    }

    public void setNews(List<News> news) {
        this.news = news;
        notifyDataSetChanged();
    }
}
