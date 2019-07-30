package com.giotec.mini_tumblr.Models;

import android.text.Html;

import com.giotec.mini_tumblr.Utils.Utils;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import org.json.JSONException;
import org.json.JSONObject;


public class PostItem {
    private String type;
    private String tags;
    private boolean liked;
    private BlogItem blogItem;
    private String title;
    private String body;
    private String urlPhoto;
    private long id;

    public PostItem(Post mpost, String type){
        this.blogItem = Utils.getBlogbyName(mpost.getBlogName());
        this.tags = Utils.cleanTags(mpost.getTags());
        this.type = type;
        this.liked = mpost.isLiked();
        this.id = mpost.getId();
        if (type.equals("text"))
            setForTextPost((TextPost) mpost);
        else if (type.equals("photo"))
            setForPhotoPost((PhotoPost) mpost);
    }

    public void setForTextPost(TextPost mpost){
        this.title = mpost.getTitle();
        this.body = Html.fromHtml(mpost.getBody())+"";
        this.urlPhoto="";
    }

    public void setForPhotoPost(PhotoPost mpost){
        this.title = "";
        urlPhoto="";
        for (Photo photo: mpost.getPhotos()){
            PhotoSize photoSize= photo.getSizes().get(0);
            urlPhoto = photoSize.getUrl();
            break;
        }
        this.body = Html.fromHtml(mpost.getCaption())+"";
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public BlogItem getBlog_item() {
        return blogItem;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public long getId() {
        return id;
    }

    public JSONObject getJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("type",type);
            obj.put("liked",liked);
            obj.put("BlogItem",blogItem.getJSON());
            obj.put("title",title);
            obj.put("body",body);
            obj.put("urlPhoto",urlPhoto);
            obj.put("id",id);
            obj.put("tags",tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public PostItem(JSONObject obj) throws JSONException {
        type = obj.getString("type");
        liked = obj.getBoolean("liked");
        title = obj.getString("title");
        body = obj.getString("body");
        urlPhoto = obj.getString("urlPhoto");
        id = obj.getLong("id");
        tags = obj.getString("tags");
        blogItem = new BlogItem(obj.getJSONObject("BlogItem"));
    }
}
