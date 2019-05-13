package com.example.naviapplication.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naviapplication.object.Article;
import com.example.naviapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedNewsAdapter extends ArrayAdapter<Article> {

    public SavedNewsAdapter(Context context, int resource, List<Article> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.saved_item, null);
        }
        Article p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtTitle = (TextView) view.findViewById(R.id.savedTitle);
            txtTitle.setText(p.title);
            TextView txtPubDate = (TextView) view.findViewById(R.id.savedPubDate);
            txtPubDate.setText(p.pubDate);
            ImageView imageView = (ImageView) view.findViewById(R.id.savedIamge);
            Picasso.get().load(p.image).into(imageView);
        }
        return view;
    }
}
