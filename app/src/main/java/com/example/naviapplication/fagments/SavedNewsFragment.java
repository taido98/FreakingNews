package com.example.naviapplication.fagments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.naviapplication.Article;
import com.example.naviapplication.GridAdapter;
import com.example.naviapplication.Main2Activity;
import com.example.naviapplication.MainActivity;
import com.example.naviapplication.R;

import java.util.ArrayList;

public class SavedNewsFragment extends Fragment {

    GridAdapter gridAdapter;
    GridView gridView;
    ArrayList<Article> savedArticle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_saved, container, false);

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

        gridAdapter = new GridAdapter(getActivity(), android.R.layout.simple_list_item_1, savedArticle);
        gridView = (GridView) rootview.findViewById(R.id.Gridview);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra("link", savedArticle.get(position).link);
                startActivity(intent);
            }
        });

        return rootview;
    }
}
