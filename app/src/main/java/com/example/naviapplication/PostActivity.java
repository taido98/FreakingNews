package com.example.naviapplication;

import android.content.Intent;
import android.net.sip.SipSession;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity{

    ArrayList<Post> list = new ArrayList<>();
    RecyclerView listPost;
    PostAdapter postAdapter;
    int width;
    Spinner category;
    ip ip = new ip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_post_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = Math.round(displayMetrics.widthPixels - 10 * displayMetrics.density);
        listPost = (RecyclerView) findViewById(R.id.listPost);
        Post post = new Post();
        post.setName("one");
        post.setVote(12);
        post.setStatus(1);
        post.setContent("hhaa");
        post.setId(1);
        post.setDate("12/12/12");
        post.setUrl_avatar("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        post.setUrl_post("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        ArrayList<String> listImage = new ArrayList<>();
        listImage.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        listImage.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        listImage.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        post.setListImage(listImage);

        Post post2 = new Post();
        post2.setName("two");
        post2.setVote(120);
        post2.setStatus(-1);
        post2.setContent("hhaa998");
        post2.setId(2);
        post2.setDate("12/1222/12");
        post2.setUrl_avatar("https://upload.wikimedia.org/wikipedia/commons/c/c7/Blue_rose-artificially_coloured.jpg");
        post2.setUrl_post("https://upload.wikimedia.org/wikipedia/commons/c/c7/Blue_rose-artificially_coloured.jpg");
        ArrayList<String> listImage2 = new ArrayList<>();
        listImage2.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        listImage2.add("https://upload.wikimedia.org/wikipedia/commons/c/c7/Blue_rose-artificially_coloured.jpg");
        listImage2.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        post2.setListImage(listImage2);

        Post post3 = new Post();
        post3.setName("three");
        post3.setVote(120);
        post3.setStatus(-1);
        post3.setContent("leuleu");
        post3.setId(2);
        post3.setDate("12/1222/12");
        post3.setUrl_avatar("https://upload.wikimedia.org/wikipedia/commons/c/c7/Blue_rose-artificially_coloured.jpg");
        post3.setUrl_post("https://upload.wikimedia.org/wikipedia/commons/c/c7/Blue_rose-artificially_coloured.jpg");
        ArrayList<String> listImage3 = new ArrayList<>();
        listImage3.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        listImage3.add("https://upload.wikimedia.org/wikipedia/commons/c/c7/Blue_rose-artificially_coloured.jpg");
        listImage3.add("https://icdn.dantri.com.vn/thumb_w/640/2018/5/23/net-cuoi-be-gai-9-1527053440039156820618.jpg");
        post3.setListImage(listImage3);

        list.add(post3);
        list.add(post);
        list.add(post2);

        postAdapter = new PostAdapter(this,list,width);
        listPost.setAdapter(postAdapter);
        listPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        category = (Spinner) findViewById(R.id.category);

//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                loadPost("http://"+ip.getIp()+"/FreakingNews/getPost.php", category.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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
        Toast.makeText(PostActivity.this,type,Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"?category="+type+"&idUser=1",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                list.add(new Post(
                                        jsonObject.getString("name"),
                                        jsonObject.getString("created_at"),
                                        jsonObject.getString("url_avatar"),
                                        jsonObject.getString("url_image"),
                                        jsonObject.getString("content"),
                                        jsonObject.getInt("status"),
                                        jsonObject.getInt("id"),
                                        jsonObject.getInt("vote")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listPost.setAdapter(new PostAdapter(PostActivity.this,list,width));
                        listPost.setLayoutManager(new LinearLayoutManager(PostActivity.this, LinearLayoutManager.VERTICAL, false));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostActivity.this,"Lỗi\n"+error,Toast.LENGTH_LONG).show();
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
    }
}
