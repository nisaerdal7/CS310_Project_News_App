package com.example.newsapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostCommentsActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mCommentEditText;
    private Button postButton;
    private int mNewsId;
    private TextView errorMsg;

    private ExecutorService mExecutorService;
    private Handler mUiHandler;
    boolean sent = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_comments_activity);
        getSupportActionBar().setTitle("Post Comment");
        mNameEditText = findViewById(R.id.user_name);
        mCommentEditText = findViewById(R.id.comment_text);
        postButton = findViewById(R.id.button2);
        errorMsg = findViewById(R.id.errorView);
        mNewsId = getIntent().getIntExtra("newsId", 0);

        mExecutorService = Executors.newSingleThreadExecutor();
        mUiHandler = new Handler();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mNameEditText.setText("");
                }
            }
        });
        mCommentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mCommentEditText.setText("");
                }
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prgBar.setVisibility(View.VISIBLE);
                ProgressDialog progressDialog = new ProgressDialog(PostCommentsActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String name = mNameEditText.getText().toString();
                String text = mCommentEditText.getText().toString();
                if(name.equals("")|| text.equals("")){
                    progressDialog.dismiss();
                    errorMsg.setVisibility(View.VISIBLE);
                }
                else {
                    ExecutorService srv = ((NewsApp) getApplication()).srv;
                    CommentsRepo repo = new CommentsRepo();
                    repo.PostComments(srv, mUiHandler, name, text, mNewsId);
                    progressDialog.dismiss();
                    Intent intent = new Intent(PostCommentsActivity.this, CommentsActivity.class);
                    intent.putExtra("newsId", mNewsId );
                    startActivity(intent);
                }


            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra("newsId", mNewsId );
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}












