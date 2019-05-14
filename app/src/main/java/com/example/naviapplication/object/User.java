package com.example.naviapplication.object;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.naviapplication.R;

import static android.content.Context.MODE_PRIVATE;

public class User {
    private String name, email, url_avatar;
    private int id;

    public User(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("DoUSer", MODE_PRIVATE);
        this.name = sharedPreferences.getString("name","null");
        this.email = sharedPreferences.getString("email" , "null");
        this.url_avatar = sharedPreferences.getString("url","null");
        this.id = Integer.valueOf(sharedPreferences.getString("idUser","0"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
