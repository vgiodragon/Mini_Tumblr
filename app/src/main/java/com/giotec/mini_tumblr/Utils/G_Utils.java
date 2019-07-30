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

public class G_Utils {

    private final static String valuePreference ="com.giotec.mini_tumblr";
    private final static String emptyJSONpost= "[]";
    private static final String idmjpost = "post_items";
    private static String TAG="GIODEBUG_G_UTILS";

    public static String getValuePreference() {
        return valuePreference;
    }

    public static String getEmptyJSONpost() {
        return emptyJSONpost;
    }

    public static String getIdmjpost() {
        return idmjpost;
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
        return jsonArray;
    }


    public static boolean isTherePreferences(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(valuePreference, Context.MODE_PRIVATE);
        String defaultpref = G_Utils.getEmptyJSONpost();
        String mjposts = sharedPref.getString(idmjpost,defaultpref);

        if(mjposts.equals(defaultpref)) return false;

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
                //Check and save Blogs
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utils.setBlogs(mblogs);
        Utils.setPosts(mposts);
        return true;
    }

    public static boolean isNewBlog(List<BlogItem> mblogs, BlogItem blog){
        for(BlogItem blog_item : mblogs){
            if(blog_item.getName().equals(blog.getName())) return false;
        }
        return true;
    }

}
