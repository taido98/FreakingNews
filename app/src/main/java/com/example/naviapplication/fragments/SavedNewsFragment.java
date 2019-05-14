package com.example.naviapplication.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.naviapplication.object.Article;
import com.example.naviapplication.MainActivity;
import com.example.naviapplication.util.SavedNewsAdapter;
import com.example.naviapplication.R;

import java.util.ArrayList;

public class SavedNewsFragment extends Fragment {

    SavedNewsAdapter listAdapter;
    SwipeMenuListView listView;
    ArrayList<Article> savedArticle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_saved, container, false);
        listView = (SwipeMenuListView) rootview.findViewById(R.id.swipeView);

        savedArticle = new ArrayList<Article>();
        //TODO 2: đây là mẫu, comment phần này lại
        savedArticle.add(new Article("this is a very long title1 that it break line", "link1", "image1", "pub1"));
        savedArticle.add(new Article("title2", "link1", "image2", "pub1"));
        savedArticle.add(new Article("title3", "link1", "image3", "pub1"));
        savedArticle.add(new Article("title4", "link1", "image4", "pub1"));
        savedArticle.add(new Article("title5", "link1", "image5", "pub1"));
        savedArticle.add(new Article("title6", "link1", "image6", "pub1"));

        //TODO 3: lấy các tin đã lưu trên server vào biến savedArticle
        //ArrayList<Article> savedArticle = ...

        listAdapter = new SavedNewsAdapter(getActivity(), android.R.layout.simple_list_item_1, savedArticle);
        listView.setAdapter(listAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // tạo nút share
                SwipeMenuItem shareItem = new SwipeMenuItem(
                        getContext());
                // nút share background
                shareItem.setBackground(new ColorDrawable(Color.rgb(66, 134, 244)));
                // width của nút share
                shareItem.setWidth(170);
                // tạo icon
                shareItem.setIcon(R.drawable.ic_share_white);
                // thêm vào menu
                menu.addMenuItem(shareItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_white);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ((MainActivity)getActivity()).shareIt(savedArticle.get(position));
                        break;
                    case 1:
                        // TODO 4: tạo chức năng xóa tin đã lưu tại đây
                        Toast.makeText(getActivity(), "delete post", Toast.LENGTH_LONG).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        return rootview;
    }
}
