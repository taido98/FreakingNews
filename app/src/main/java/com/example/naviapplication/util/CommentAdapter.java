package com.example.naviapplication.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naviapplication.object.Comment;
import com.example.naviapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> listComment;
    private Context context;
    private LayoutInflater inflater;

    public CommentAdapter(List<Comment> listComment, Context context) {
        this.listComment = listComment;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_comment,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Comment comment = listComment.get(i);

        Picasso.get().load(comment.getUrl_avatar()).into(viewHolder.avatar);
        viewHolder.content.setText(comment.getContent());
        viewHolder.name.setText(comment.getName());
        viewHolder.date.setText(comment.getDate());
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView content, name, date;
        public ViewHolder(View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar_comment);
            content = (TextView) view.findViewById(R.id.content_comment);
            name = (TextView) view.findViewById(R.id.name_post);
            date = (TextView) view.findViewById(R.id.date_comment);
        }
    }

}
