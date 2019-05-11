package com.example.naviapplication;

import java.util.ArrayList;

public class Post {
    private String name, date, url_avatar, url_post, content;
    private int status, id;
    private int vote;
    private ArrayList<String> listImage;
    public Post(){}

    public Post(String name, String date, String url_avatar, String url_post, String content, int status, int id, int vote) {
        this.name = name;
        this.date = date;
        this.url_avatar = url_avatar;
        this.url_post = url_post;
        this.content = content;
        this.status = status;
        this.id = id;
        this.vote = vote;
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }

    public String getUrl_post() {
        return url_post;
    }

    public void setUrl_post(String url_post) {
        this.url_post = url_post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
