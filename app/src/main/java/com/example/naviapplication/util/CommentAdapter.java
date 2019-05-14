package com.example.naviapplication.util;

import android.content.Context;
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

public class CommentAdapter extends BaseAdapter {
    private List<Comment> listComment;
    private Context context;
    private LayoutInflater inflater;


    public CommentAdapter(List<Comment> listComment, Context context) {
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
            convertView = inflater.inflate(R.layout.item_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = convertView.findViewById(R.id.avatar_comment);
            viewHolder.content = convertView.findViewById(R.id.content_comment);
            viewHolder.name = convertView.findViewById(R.id.name_post);
            viewHolder.date = convertView.findViewById(R.id.date_comment);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.get().load(comment.getUrl_avatar()).into(viewHolder.avatar);
        viewHolder.content.setText(comment.getContent());
        viewHolder.name.setText(comment.getName());
        viewHolder.date.setText(comment.getDate());

        return convertView;
    }

    static class ViewHolder{
        ImageView avatar;
        TextView content, name, date;
    }
}
