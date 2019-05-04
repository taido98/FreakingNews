package com.example.naviapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    ArrayList<Comment> list = new ArrayList<>();
    ListView listCmt;
    CustomCommentAdapter customCommentAdapter;
    ip ip = new ip();
    int idPost, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = this.getIntent();
        idPost = Integer.valueOf(intent.getStringExtra("idPost"));
        idUser = Integer.valueOf(intent.getStringExtra("idUser"));

        listCmt = (ListView) findViewById(R.id.listComment);

        loadCmt("http://"+ip.getIp()+"/FreakingNews/getCmt.php");

        Button submit = findViewById(R.id.submit_cmt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.inputCmt) ;
                Toast.makeText(CommentActivity.this,idPost+";"+idUser+";"+input.getText().toString().trim(),Toast.LENGTH_LONG).show();
                addCmt("http://"+ip.getIp()+"/FreakingNews/getCmt.php",input.getText().toString().trim());
                input.setText("");
                loadCmt("http://"+ip.getIp()+"/FreakingNews/getCmt.php");
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadCmt(String url){
        list.removeAll(list);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"?idPost="+idPost+"&type=loadCmt",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(CommentActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                list.add(new Comment(
                                        jsonObject.getString("name"),
                                        jsonObject.getString("content"),
                                        jsonObject.getString("url_avatar"),
                                        jsonObject.getString("created_at")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        customCommentAdapter = new CustomCommentAdapter(list,CommentActivity.this);
                        listCmt.setAdapter(customCommentAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CommentActivity.this,"Lỗi\n"+error,Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void addCmt(String url, String content){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url+"?idPost="+idPost+"&idUser="+idUser+"&content="+content+"&type=addCmt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Success"))
                            Toast.makeText(CommentActivity.this,"Comment thành công",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(CommentActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CommentActivity.this,"Lỗi\n"+error,Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}

