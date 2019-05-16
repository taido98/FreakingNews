package com.example.naviapplication;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.naviapplication.object.Post;
import com.example.naviapplication.object.User;
import com.example.naviapplication.service.ip;
import com.example.naviapplication.util.PostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity{

    ArrayList<Post> list = new ArrayList<>();
    RecyclerView listPost;
    SwipeRefreshLayout containerPost;
    PostAdapter postAdapter;
    int width;
    Spinner category;
    ip ip = new ip();
    int idUser ;
    String urlPost = "http://"+ip.getIp()+"/FreakingNews/getPost.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_post_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        User user = new User(this);
        idUser = user.getId();
//        Toast.makeText(this,""+idUser,Toast.LENGTH_LONG).show();

        listPost = (RecyclerView) findViewById(R.id.listPost);
        postAdapter = new PostAdapter(PostActivity.this,list);
        listPost.setAdapter(postAdapter);
        listPost.setLayoutManager(new LinearLayoutManager(PostActivity.this, LinearLayoutManager.VERTICAL, false));

        containerPost = (SwipeRefreshLayout) findViewById(R.id.containerPost);

        category = (Spinner) findViewById(R.id.category);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadPost(urlPost, category.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        containerPost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPost(urlPost, category.getSelectedItem().toString());
                containerPost.setRefreshing(false);
            }
        });

        Button add_post = findViewById(R.id.add_post);
        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this,AddPostActivity.class);
                PostActivity.this.startActivity(intent);
            }
        });
    }

    private void loadPost(String url, final String type){
        list.removeAll(list);
//        Toast.makeText(PostActivity.this,type,Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"?category="+type+"&idUser="+idUser,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ArrayList<String> listUrl = new ArrayList<>();
                                JSONArray arrUrl = new JSONArray(jsonObject.getString("url_image"));
                                for(int j = 0; j<arrUrl.length(); j++){
                                    listUrl.add(arrUrl.getJSONObject(j).getString("url").replaceAll("localhost",ip.getIp()));
                                }
                                int stt, v;
                                if(jsonObject.getString("status").equals("null"))
                                    stt = 0;
                                else stt = jsonObject.getInt("status");
                                if(jsonObject.getString("vote").equals("null"))
                                    v = 0;
                                else v = jsonObject.getInt("vote");
                                Log.d("name",jsonObject.getString("name"));
                                list.add(new Post(
                                        jsonObject.getString("name"),
                                        jsonObject.getString("created_at"),
                                        jsonObject.getString("url_avatar"),
                                        jsonObject.getString("content"),
                                        stt,
                                        jsonObject.getInt("id"),
                                        v,
                                        listUrl
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        postAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        postAdapter.notifyDataSetChanged();
                        Log.d("Lỗi",error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
        finish();
    }
}
