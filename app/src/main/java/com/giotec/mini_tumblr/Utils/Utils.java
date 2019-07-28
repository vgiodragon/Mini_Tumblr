package com.giotec.mini_tumblr.Utils;

import android.util.Log;

import com.giotec.mini_tumblr.Models.Blog_item;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;

import java.util.List;

public class Utils {
    private static List<Blog_item> blogs;
    private static List<Post> posts;
    private static Utils utils;
    private static String TAG="GIODEBUG_UTILS";

    private Utils(List<Blog_item> blogs){
        this.blogs=blogs;
    }

    public static Utils getSingletonInstance(List<Blog_item> blogs2){
        if(blogs == null){
            utils = new Utils(blogs2);
            Log.d(TAG,"blogs seteado!");
        }
        else {
            Log.d(TAG,"Ya estaba iniciada el Utils");
        }
        return utils;
    }

    public static Blog_item getBlogbyName(String name){
        Log.d(TAG,"name "+ name + " "+blogs.size());
        for (Blog_item blog : blogs) {
            Log.d(TAG,blog.getName());//Nombre
            Log.d(TAG, blog.getAvatar());
            if (blog.getName().equals(name)) return blog;
        }
        return null;
    }

    public static List<Post> getPosts() {
        return posts;
    }

    public static void setPosts(List<Post> posts) {
        Utils.posts = posts;
    }

    public static void addPost(List<Post> posts) {
        if(Utils.posts!=null){
            for (Post post : posts)
                Utils.posts.add(post);
        }else{
            setPosts(posts);
        }
    }

    public static String cleanCaption(String caption){
        return caption.replace("<p>","").replace("</p>","");
    }

    public static String cleanTags(List<String> tags){
        String tag = "";
        for(String mtag : tags)
            tag += "#"+mtag+"\t";
        return tag;
    }
}
