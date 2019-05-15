package com.example.naviapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naviapplication.object.Comment;
import com.example.naviapplication.object.User;
import com.example.naviapplication.service.ip;
import com.example.naviapplication.util.CommentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    ArrayList<Comment> list = new ArrayList<>();
    RecyclerView listCmt;
    CommentAdapter commentAdapter;
    ip ip = new ip();
    int idPost, idUser;
    EditText input;
    Button submit;
    String urlComment = "http://"+ip.getIp()+"/FreakingNews/getCmt.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = this.getIntent();
        idPost = Integer.valueOf(intent.getStringExtra("idPost"));

        final User user = new User(this);
        idUser = user.getId();

        input = (EditText) findViewById(R.id.inputCmt);
        listCmt = (RecyclerView) findViewById(R.id.listComment);
        commentAdapter = new CommentAdapter(list,CommentActivity.this);
        listCmt.setAdapter(commentAdapter);
        listCmt.setLayoutManager(new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false));

        loadCmt(urlComment);

        submit = findViewById(R.id.submit_cmt);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEmpty();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()){
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    Comment newCmt = new Comment(user.getName(),input.getText().toString(),user.getUrl_avatar(),sdf.format(date));
                    list.add(newCmt);
                    commentAdapter.notifyItemInserted(list.size()-1 );
                    listCmt.scrollToPosition(list.size()-1);
                    Toast.makeText(CommentActivity.this,idPost+";"+idUser+";"+input.getText().toString().trim(),Toast.LENGTH_LONG).show();
                    addCmt(urlComment,input.getText().toString());
                    input.setText("");
                }
                else
                    Toast.makeText(CommentActivity.this,"Vui lòng nhập bình luận",Toast.LENGTH_LONG).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    protected boolean checkEmpty(){
        if(input.getText().toString().equals("")){
            submit.setTextColor(0x4F4285F4);
            return false;
        }
        submit.setTextColor(0xFF4285F4);
        return true;
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
                        commentAdapter.notifyDataSetChanged();
                        Toast.makeText(CommentActivity.this,"Load thanh cong",Toast.LENGTH_LONG).show();
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
                        if(response.trim().equals("Success")){
                            Toast.makeText(CommentActivity.this,"Comment thành công"+idUser,Toast.LENGTH_LONG).show();
//                            loadCmt(urlComment);
                        }
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

