package com.giotec.mini_tumblr.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.giotec.mini_tumblr.Models.BlogItem;
import com.giotec.mini_tumblr.Models.PostItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForPreferences {

    private final static String valuePreference ="com.giotec.mini_tumblr";
    private final static String emptyJSONpost= "[]";
    private static final String idmjpost = "post_items";
    private static String TAG="GIODEBUG_ForPreferences";

    public static String getEmptyJSONpost() {
        return emptyJSONpost;
    }


    public static void savePostinPreferences(Context ctx, List<PostItem> mnoticas){
        if(ctx==null) return;
        SharedPreferences sharedPref = ctx.getSharedPreferences(valuePreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(idmjpost, getJSONArrayofPost(mnoticas).toString());
        editor.commit();
        Log.d(TAG,"Grabado en POST");
    }

    private static JSONArray getJSONArrayofPost(List<PostItem> post_items){
        JSONArray jsonArray = new JSONArray();
        for(PostItem post_item: post_items){
            jsonArray.put(post_item.getJSON());
        }
        Log.d(TAG,jsonArray.toString());
        return jsonArray;
    }


    public static boolean isTherePreferences(Context ctx){
        String defaultpref = ForPreferences.getEmptyJSONpost();
        String mjposts = getStringfromPreferences(ctx,defaultpref);

        if(mjposts.equals(defaultpref)) return false;

        return loadBlogsandPosts(mjposts);
    }

    public static String getStringfromPreferences(Context ctx,String defaultpref){
        SharedPreferences sharedPref = ctx.getSharedPreferences(valuePreference, Context.MODE_PRIVATE);
        return sharedPref.getString(idmjpost,defaultpref);
    }

    public static boolean loadBlogsandPosts(String mjposts){
        List<BlogItem> mblogs = new ArrayList<>();
        List<PostItem> mposts = new ArrayList<>();
        try {
            JSONArray mjsonarray = new JSONArray(mjposts);
            for (int i = 0 ; i < mjsonarray.length(); i++) {
                JSONObject obj = mjsonarray.getJSONObject(i);
                PostItem mpost_item = new PostItem(obj);
                mposts.add(mpost_item);
                if(isNewBlog(mblogs,mpost_item.getBlog_item()))
                    mblogs.add(mpost_item.getBlog_item());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        Utils.setBlogs(mblogs);
        Utils.setPosts(mposts);

        return true;
    }


    public static boolean isNewBlog(List<BlogItem> mblogs, BlogItem blog){
        if(blog==null || mblogs == null) return false;
        for(BlogItem blog_item : mblogs){
            if(blog_item.getName().equals(blog.getName())) return false;
        }
        return true;
    }

    public static boolean validLogin(String user,String pass){
        if(user==null || pass ==null) return false;
        if(user.equals("admin") && pass.equals("admin")) return true;
        return false;
    }
}
