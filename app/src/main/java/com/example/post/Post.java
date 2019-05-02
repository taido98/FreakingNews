package com.example.post;

public class Post {
    private String name, date, url_avatar, url_post, content, id;
    private int vote;
    public Post(String name, String date, String url_avatar, String url_post, String content, int vote){
        this.name = name;
        this.date = date;
        this.url_avatar = url_avatar;
        this.url_post = url_post;
        this.content = content;
        this.vote = vote;
    }

    public Post(String name, String date, String url_avatar, String url_post, String content, int vote, String id) {
        this.name = name;
        this.date = date;
        this.url_avatar = url_avatar;
        this.url_post = url_post;
        this.content = content;
        this.vote = vote;
        this.id = id;
    }

    public Post(String name, String date, String url_avatar, String content, int vote) {
        this.name = name;
        this.date = date;
        this.url_avatar = url_avatar;
        this.content = content;
        this.vote = vote;
        url_post = "null";
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
