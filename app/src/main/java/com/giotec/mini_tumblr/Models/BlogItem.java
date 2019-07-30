package com.giotec.mini_tumblr.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BlogItem {
    private String avatar;
    private String name;

    public BlogItem(String name, String avatar) {
        this.avatar = avatar;
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public JSONObject getJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("avatar",avatar);
        obj.put("name",name);

        return obj;
    }

    public BlogItem(JSONObject obj) throws JSONException {
        this.avatar = obj.getString("avatar");
        this.name = obj.getString("name");
    }
}
