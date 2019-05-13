package com.example.naviapplication.object;

public class Comment {
    private String name, content, url_avatar, date, id;

    public Comment(String name, String content, String url_avatar) {
        this.name = name;
        this.content = content;
        this.url_avatar = url_avatar;
    }

    public Comment(String name, String content, String url_avatar, String date) {
        this.name = name;
        this.content = content;
        this.url_avatar = url_avatar;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
