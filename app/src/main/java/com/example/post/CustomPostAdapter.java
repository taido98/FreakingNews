package com.example.post;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class CustomPostAdapter extends BaseAdapter {
    private List<Post> listPost;
    private LayoutInflater layoutInflater;
    private Context context;
    private int width;

    public CustomPostAdapter(List<Post> listPost, Context context, int width) {
        this.width = width;
        this.listPost = listPost;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listPost.size();
    }

    @Override
    public Object getItem(int position) {
        return listPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Post post = this.listPost.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_post_layout, null);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.imagePost = (ImageView) convertView.findViewById(R.id.imagePost);
            holder.name = (TextView) convertView.findViewById(R.id.name_comment);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.vote = (TextView) convertView.findViewById(R.id.vote);
            holder.like = (ImageView) convertView.findViewById(R.id.like);
            holder.dislike = (ImageView) convertView.findViewById(R.id.dislike);
            holder.button = (Button) convertView.findViewById(R.id.commentButton);
            holder.like = (ImageView) convertView.findViewById(R.id.like);
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+post.getVote(), Toast.LENGTH_LONG).show();
                    post.setVote(post.getVote()+1);
                    holder.vote.setText(""+post.getVote());
                    holder.like.setImageResource(R.mipmap.voted_up);
                }
            });
            holder.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,""+holder.imagePost.getWidth(), Toast.LENGTH_LONG).show();
                    post.setVote(post.getVote()-1);
                    holder.vote.setText(""+post.getVote());
                    holder.dislike.setImageResource(R.mipmap.voted_down);
                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
//                    intent.putExtra("idComment", post.getId());
                    context.startActivity(intent);
//                    Toast.makeText(context,"Da chuyen",Toast.LENGTH_LONG).show();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(post.getName());
        holder.date.setText(post.getDate());
        holder.content.setText(post.getContent());
        holder.vote.setText(""+post.getVote());
        Picasso.with(context).load(post.getUrl_avatar()).into(holder.avatar);
        Picasso.with(context).load(post.getUrl_post()).resize(width,0).into(holder.imagePost);

        return convertView;
    }
    static class ViewHolder {
        ImageView avatar, imagePost, like, dislike;
        TextView name, date, content, vote;
        Button button;
    }
}
