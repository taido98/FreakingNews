package com.example.naviapplication.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naviapplication.R;
import com.example.naviapplication.object.Article;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends ArrayAdapter<Article> {

    public CustomAdapter(Context context, int resource, List<Article> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.fragment_home, parent, false);
        }
        Article p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtTitle = (TextView) view.findViewById(R.id.textViewTitle);
            txtTitle.setText(Html.fromHtml(Html.fromHtml(p.title).toString()));
            TextView txtPubDate = (TextView) view.findViewById(R.id.textViewPubDate);
            txtPubDate.setText(parseDate(p.pubDate));
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.get().load(p.image).into(imageView);
        }
        return view;
    }

    private String parseDate(String time) {
        String inputPattern = "EEE, d MMM yyyy HH:mm:ss Z";
        String outputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.US);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SetDate setDate = new SetDate(str);
        return setDate.getDate();
    }
}