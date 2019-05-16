package com.example.naviapplication.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.naviapplication.MainActivity;
import com.example.naviapplication.WebViewActivity;
import com.example.naviapplication.object.ArticleSave;

import com.example.naviapplication.object.User;
import com.example.naviapplication.service.ip;
import com.example.naviapplication.util.SavedNewsAdapter;
import com.example.naviapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedNewsFragment extends Fragment {

    SavedNewsAdapter listAdapter;
    SwipeMenuListView listView;
    ArrayList<ArticleSave> savedArticle;
    private int idUser,check;
    private String link;
    ip ip =new ip();
    String urlLoadSave = "http://"+ip.getIp()+"/FreakingNews/Loadsave.php";
    String urlDelSave = "http://"+ip.getIp()+"/FreakingNews/DelSave.php";
    SavedNewsAdapter savedNewsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_saved, container, false);
        User user = new User(getActivity());
        idUser = user.getId();
        listView = (SwipeMenuListView) rootview.findViewById(R.id.swipeView);

        savedArticle = new ArrayList<ArticleSave>();
        //TODO 2: đây là mẫu, comment phần này lại
//        savedArticle.add(new Article("this is a very long title1 that it break line", "link1", "image1", "pub1"));
//        savedArticle.add(new Article("title2", "link1", "image2", "pub1"));
//        savedArticle.add(new Article("title3", "link1", "image3", "pub1"));
//        savedArticle.add(new Article("title4", "link1", "image4", "pub1"));
//        savedArticle.add(new Article("title5", "link1", "image5", "pub1"));
//        savedArticle.add(new Article("title6", "link1", "image6", "pub1"));
        savedNewsAdapter = new SavedNewsAdapter(getActivity(),android.R.layout.simple_list_item_1,savedArticle);
        listView.setAdapter(savedNewsAdapter);

        //TODO 3: lấy các tin đã lưu trên server vào biến savedArticle
        //ArrayList<Article> savedArticle = ...
        loadSave(urlLoadSave);
//        listAdapter = new SavedNewsAdapter(getActivity(), android.R.layout.simple_list_item_1, savedArticle);
//        listView.setAdapter(listAdapter);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", savedArticle.get(position).link);
                startActivity(intent);
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        shareItSave(savedArticle.get(position));
                        break;
                    case 1:
                        // TODO 4: tạo chức năng xóa tin đã lưu tại đây
                        link = savedArticle.get(position).link;
                        DelSave(urlDelSave);
//                        Toast.makeText(getActivity(), "delete post", Toast.LENGTH_LONG).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        return rootview;
    }
    public void shareItSave(ArticleSave p) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String sharebody = p.link;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    private void loadSave(String url){
        savedArticle.removeAll(savedArticle);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"?idUser="+idUser,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(SavedNewsFragment.this.getContext(),check+"",Toast.LENGTH_LONG).show();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                savedArticle.add(new ArticleSave(
//                                       jsonObject.getString("idUser"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("link"),
                                        jsonObject.getString("url_news")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        savedNewsAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"Load thanh cong",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        savedNewsAdapter.clear();
                        Toast.makeText(getActivity(),"Chưa có tin đươc lưu",Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //    xoa tin luu
    private void DelSave(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url+"?link="+link+"&idUser="+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Success")){
                            loadSave(urlLoadSave);
                        }
                        else
                            Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Lỗi\n"+error,Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }
}
