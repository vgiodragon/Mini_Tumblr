package com.giotec.mini_tumblr.Models;

public class Blog_item {
    private String avatar;
    private String name;

    public Blog_item(String name,String avatar) {
        this.avatar = avatar;
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }
}
