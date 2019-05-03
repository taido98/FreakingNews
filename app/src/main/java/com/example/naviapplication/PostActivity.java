package com.example.naviapplication;

import android.content.Intent;
import android.net.sip.SipSession;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class PostActivity extends AppCompatActivity{

    ArrayList<Post> list = new ArrayList<>();
    ListView listPost;
    CustomPostAdapter customPostAdapter;

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
        int width = Math.round(displayMetrics.widthPixels - 10 * displayMetrics.density);

//        List<Post> listData = getListPost();
        listPost = (ListView) findViewById(R.id.listPost);
//        List<Post> listData = loadPost("http://"+ip+"/FreakingNews/getPost.php");
        customPostAdapter = new CustomPostAdapter(list,this, width);
        listPost.setAdapter(customPostAdapter);
        ip ip = new ip();
        loadPost("http://"+ip.getIp()+"/FreakingNews/getPost.php");


//        Toast.makeText(this,ip+"/FreakingNews/getPost.php",Toast.LENGTH_LONG).show();

        Button add_post = findViewById(R.id.add_post);
        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this,AddPostActivity.class);
                PostActivity.this.startActivity(intent);
            }
        });
    }

    private void loadPost(String url){
        list.removeAll(list);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(PostActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                list.add(new Post(
                                        jsonObject.getString("name"),
                                        jsonObject.getString("created_at"),
                                        jsonObject.getString("url_avatar"),
                                        jsonObject.getString("url_image"),
                                        jsonObject.getString("content"),
                                        jsonObject.getInt("vote")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            customPostAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostActivity.this,"Lỗi\n"+error,Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
//        return list;
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
