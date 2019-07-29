package com.giotec.mini_tumblr.Utils;

import android.util.Log;

import com.giotec.mini_tumblr.Models.Blog_item;
import com.giotec.mini_tumblr.Models.Post_Item;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static List<Blog_item> blogs;
    private static List<Post_Item> posts;
    private static Utils utils;
    private static String TAG="GIODEBUG_UTILS";
    private static JumblrClient client;

    private Utils(JumblrClient client) {
        this.client = client;
    }

    public static Utils getSingletonInstance(JumblrClient client2){
        if(blogs == null){
            utils = new Utils(client2);
            Log.d(TAG,"JumblrClient seteado!");
        }
        else {
            Log.d(TAG,"Ya estaba iniciada el Utils");
        }
        return utils;
    }

    public static void setBlogs(List<Blog_item> blogs) {
        Utils.blogs = blogs;
    }

    public static Blog_item getBlogbyName(String name){
        for (Blog_item blog : blogs) {
            if (blog.getName().equals(name)) return blog;
        }
        return null;
    }

    public static List<Post_Item> getPosts() {
        return posts;
    }

    public static void setPosts(List<Post> posts) {
        Utils.posts = filterPosts(posts);
    }

    public static List<Post_Item> filterPosts(List<Post> posts) {
        List<Post_Item> mposts = new ArrayList<>();
        String type = "";
        for (Post post : posts){
            type = post.getType().getValue();
            if (type.equals("text")||type.equals("photo"))
                mposts.add(new Post_Item(post,type));
        }
        return mposts;
    }


    public static String cleanHTML(String caption){
        return caption.replace("<p>","")
                .replace("</p>","")
                .replace("<br/>","");
    }

    public static String cleanTags(List<String> tags){
        String tag = "";
        for(String mtag : tags)
            tag += "#"+mtag+"\t";
        return tag;
    }

    public static JumblrClient getClient() {
        return client;
    }
}
