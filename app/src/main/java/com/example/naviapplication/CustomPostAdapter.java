package com.example.naviapplication;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class CustomPostAdapter extends BaseAdapter {
    private List<Post> listPost;
    private LayoutInflater layoutInflater;
    private Context context;
    private int width;
    ip ip = new ip();

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
//                    Toast.makeText(context, ""+post.getVote(), Toast.LENGTH_LONG).show();
//                    post.setVote(post.getVote()+1);
//                    holder.vote.setText(""+post.getVote());
//                    holder.like.setImageResource(R.mipmap.voted_up);
//                    setStt("http://"+ip.getIp()+"/FreakingNews/getStatus.php",1,post.getId(),1);
                    Toast.makeText(context,""+ip.getIp(),Toast.LENGTH_LONG).show();
                }
            });
            holder.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,""+holder.imagePost.getWidth(), Toast.LENGTH_LONG).show();
//                    post.setVote(post.getVote()-1);
//                    holder.vote.setText(""+post.getVote());
//                    holder.dislike.setImageResource(R.mipmap.voted_down);
//                    setStt("http://"+ip.getIp()+"/FreakingNews/getStatus.php",1,post.getId(),-1);
                    Toast.makeText(context,""+post.getId(),Toast.LENGTH_LONG).show();
                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("idPost", ""+post.getId());
                    intent.putExtra("idUser", "1");
                    context.startActivity(intent);
//                    Toast.makeText(context,"Da chuyen",Toast.LENGTH_LONG).show();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(post.getStatus() == 1){
            holder.like.setImageResource(R.mipmap.voted_up);
            holder.dislike.setImageResource(R.mipmap.vote_down);
        }
        if(post.getStatus() == -1){
            holder.like.setImageResource(R.mipmap.vote_up);
            holder.dislike.setImageResource(R.mipmap.voted_down);
        }
        if(post.getStatus() == 0){
            holder.like.setImageResource(R.mipmap.vote_up);
            holder.dislike.setImageResource(R.mipmap.vote_down);
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

    private void setStt(String url, int idUser, int idPost, int setStatus){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url+"?type=setStt&idUser="+idUser+"&idPost="+idPost+"&status="+setStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        status = Integer.valueOf(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
