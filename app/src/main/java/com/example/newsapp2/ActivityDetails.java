package com.example.newsapp2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class ActivityDetails extends AppCompatActivity {

    ImageView imgDetails;
    TextView txtTitleDetail;
    TextView textDetail;
    TextView dateDetail;
    int newsId;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            News news = (News) msg.obj;

            txtTitleDetail.setText(news.getTitle());
            textDetail.setText(news.getText());
            dateDetail.setText(news.getDate());
            newsId = news.getId();

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler,news.getImg());
            getSupportActionBar().setTitle(news.getCategoryName());
            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Bitmap img = (Bitmap) msg.obj;
            imgDetails.setImageBitmap(img);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);


        int id = getIntent().getIntExtra("id",1);

        imgDetails =findViewById(R.id.imgDetails);
        txtTitleDetail = findViewById(R.id.txtTitleDetail);
        textDetail = findViewById(R.id.textDetail);
        dateDetail = findViewById(R.id.dateDetail);


        NewsRepository repo = new NewsRepository();
        repo.getDataById(((NewsApp)getApplication()).srv,dataHandler,id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_comments_bar, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.show_comments) {
            // Handle the show comments action
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra("newsId", newsId );
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
