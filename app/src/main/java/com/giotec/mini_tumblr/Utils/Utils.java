package com.giotec.mini_tumblr.Utils;

import android.content.Context;

import com.giotec.mini_tumblr.Models.BlogItem;
import com.giotec.mini_tumblr.Models.PostItem;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static List<BlogItem> blogs;
    private static List<PostItem> posts;
    private static String TAG="GIODEBUG_ForPreferences";
    private static final int limit = 8;


    public static List<BlogItem> getBlogs() {
        return blogs;
    }

    public static void setBlogs(List<BlogItem> blogs) {
        Utils.blogs = blogs;
    }

    public static BlogItem getBlogbyName(String name){
        for (BlogItem blog : blogs) {
            if (blog.getName().equals(name)) return blog;
        }
        return null;
    }

    public static List<PostItem> getPosts() {
        return posts;
    }

    public static void FilterSetAndSavePosts(List<Post> posts, Context context) {
        setPosts(filterPosts(posts));
        ForPreferences.savePostinPreferences(context,getPosts());
    }

    public static void setPosts(List<PostItem> posts) {
        Utils.posts = posts;
    }

    public static List<PostItem> filterPosts(List<Post> posts) {
        List<PostItem> mposts = new ArrayList<>();
        String type = "";
        for (Post post : posts){
            type = post.getType().getValue();
            if (type.equals("text")||type.equals("photo"))
                mposts.add(new PostItem(post,type));
        }
        return mposts;
    }

    public static List<PostItem> filterPosts(List<Post> orig_posts, List<Post> posts, int offset) {
        List<PostItem> mposts = new ArrayList<>();
        String type = "";
        for (Post post : posts){
            type = post.getType().getValue();
            if (type.equals("text")||type.equals("photo")){
                PostItem mpost_item = newPost_Item(orig_posts,post,type,offset);
                if(mpost_item!=null) mposts.add(new PostItem(post,type));
            }
        }
        return mposts;
    }

    public static PostItem newPost_Item(List<Post> orig_posts, Post post, String type, int offset){
        if(offset<orig_posts.size()){
            for(int i = orig_posts.size()-offset; i<orig_posts.size();i++)
                if(orig_posts.get(i).getId()==post.getId()) return null;
            return new PostItem(post,type);
        }
        return null;
    }

    public static String cleanTags(List<String> tags){
        String tag = "";
        for(String mtag : tags)
            tag += "#"+mtag+"    ";
        return tag;
    }

    public static int getLimit() {
        return limit;
    }

}
