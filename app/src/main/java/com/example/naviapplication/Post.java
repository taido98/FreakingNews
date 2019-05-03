package com.example.naviapplication;

public class Post {
    private String name, date, url_avatar, url_post, content;
    private int idUser, id;
    private int vote;
    public Post(){}

    public Post(String name, String date, String url_avatar, String url_post, String content, int vote, int id, int idUser) {
        this.name = name;
        this.date = date;
        this.url_avatar = url_avatar;
        this.url_post = url_post;
        this.content = content;
        this.vote = vote;
        this.id = id;
        this.idUser = idUser;
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

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
