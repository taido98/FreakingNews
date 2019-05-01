package com.example.post;

public class Comment {
    private String name, content, url_avatar, id;

    public Comment(String name, String content, String url_avatar) {
        this.name = name;
        this.content = content;
        this.url_avatar = url_avatar;
    }

    public Comment(String name, String content, String url_avatar, String id) {
        this.name = name;
        this.content = content;
        this.url_avatar = url_avatar;
        this.id = id;
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
}
