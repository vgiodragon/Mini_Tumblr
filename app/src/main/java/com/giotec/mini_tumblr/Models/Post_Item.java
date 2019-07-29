package com.giotec.mini_tumblr.Models;

import com.giotec.mini_tumblr.Utils.Utils;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;


public class Post_Item {
    private String type;
    private String tags;
    private boolean liked;
    private Blog_item blog_item;
    private String title;
    private String body;
    private String urlPhoto;
    private long id;

    public Post_Item(Post mpost, String type){
        this.blog_item = Utils.getBlogbyName(mpost.getBlogName());
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
        this.body = Utils.cleanHTML(mpost.getBody());
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
        this.body = Utils.cleanHTML(Utils.cleanHTML(mpost.getCaption()));
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

    public Blog_item getBlog_item() {
        return blog_item;
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
}
