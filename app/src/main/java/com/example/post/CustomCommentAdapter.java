package com.example.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.PriorityQueue;

public class CustomCommentAdapter extends BaseAdapter {
    private List<Comment> listComment;
    private Context context;
    private LayoutInflater inflater;

    public CustomCommentAdapter(List<Comment> listComment, Context context) {
        this.listComment = listComment;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listComment.size();
    }

    @Override
    public Object getItem(int position) {
        return listComment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Comment comment = this.listComment.get(position);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_comment_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = convertView.findViewById(R.id.avatar_comment);
            viewHolder.content = convertView.findViewById(R.id.content_comment);
            viewHolder.name = convertView.findViewById(R.id.name_comment);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(comment.getUrl_avatar()).into(viewHolder.avatar);
        viewHolder.content.setText(comment.getContent());
        viewHolder.name.setText(comment.getName());

        return convertView;
    }

    static class ViewHolder{
        ImageView avatar;
        TextView content, name;
    }
}
