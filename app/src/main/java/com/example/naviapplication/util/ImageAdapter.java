package com.example.naviapplication.util;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.naviapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    private ArrayList<String> listUrl;
    private LayoutInflater layoutInflater;
    private int width;

    DisplayMetrics displayMetrics = new DisplayMetrics();


    public ImageAdapter(ArrayList<String> listUrl) {
        this.listUrl = listUrl;
    }

    @Override
    public int getCount() {
        return listUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return ((ConstraintLayout) o) == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(container.getContext());
        View view = layoutInflater.inflate(R.layout.item_image, container,false);
        ((Activity)container.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = Math.round(displayMetrics.widthPixels - 10 * displayMetrics.density);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
        Picasso.get().load(listUrl.get(position)).resize(width,0).into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
