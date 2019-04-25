package com.example.naviapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends ArrayAdapter<Article> {

    public GridAdapter(Context context, int resource, List<Article> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.grid_element, null);
        }
        Article p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtTitle = (TextView) view.findViewById(R.id.gridtext);
            txtTitle.setText(p.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridimage);
            Picasso.with(getContext()).load(p.image).into(imageView);
        }
        return view;
    }
}
