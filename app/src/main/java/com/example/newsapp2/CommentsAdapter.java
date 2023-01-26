package com.example.newsapp2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comments> comments;
    public CommentsAdapter() {
        comments = new ArrayList<>();
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_comments_row, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comments comment = comments.get(position);
        //holder.imageView.setImageResource(comment.getImageResource());
        holder.nameView.setText(comment.getName());
        holder.commentView.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        Log.d("Comments num", String.valueOf(comments.size()));
        return comments.size();

    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView commentView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.nameView);
            commentView = itemView.findViewById(R.id.commentView);
        }





    }
}
