package com.giotec.mini_tumblr;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.giotec.mini_tumblr.Models.BlogItem;
import com.giotec.mini_tumblr.Utils.ForPreferences;
import com.giotec.mini_tumblr.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ForPreferencesTest {

    private final String examplePostItem= "[{\"type\":\"photo\",\"liked\":false,\"BlogItem\":{\"avatar\":\"https:\\/\\/66.media.tumblr.com\\/avatar_fc804123745b_64.png#_=_\",\"name\":\"programminghumour\"},\"title\":\"\",\"body\":\"Oh the fun of HTTP-based applications\\n\\n\",\"urlPhoto\":\"https:\\/\\/66.media.tumblr.com\\/4e665a95a987f2f68bbbb34ef3140542\\/tumblr_pvfelayqhq1x779u4o1_1280.png\",\"id\":186655754547,\"tags\":\"#funny    #joke    #programming    #programmer    #computer    #humor    \"}]";
    private final String exampleBlogItem = "{\"avatar\":\"https:\\/\\/66.media.tumblr.com\\/avatar_fc804123745b_64.png#_=_\",\"name\":\"programminghumour\"}";
    private final String exampleBlogItem2= "{\"avatar\":\"https:\\/\\/66.media.tumblr.com\\/avatar_fc804123745b_64.png#_=_\",\"name\":\"prog2ramminghumour2\"}";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.giotec.mini_tumblr", appContext.getPackageName());
    }

    @Test
    public void loadBlogsandPosts(){
        setBlogandPostforTest();
        String mjposts = examplePostItem;
        assertTrue(ForPreferences.loadBlogsandPosts(mjposts));
    }

    @Test
    public void addRepeatedBlog(){
        setBlogandPostforTest();
        String mjblog = exampleBlogItem;
        BlogItem blogItem = null;
        try {
            blogItem = new BlogItem(new JSONObject(mjblog));
        } catch (JSONException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        if(Utils.getBlogs()==null) assertTrue(false);
        assertFalse(ForPreferences.isNewBlog(Utils.getBlogs(),blogItem));
    }

    @Test
    public void addNewBlog(){
        setBlogandPostforTest();
        String mjblog = exampleBlogItem2;
        BlogItem blogItem = null;
        try {
            blogItem = new BlogItem(new JSONObject(mjblog));
        } catch (JSONException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        if(Utils.getBlogs()==null) assertTrue(false);
        assertTrue(ForPreferences.isNewBlog(Utils.getBlogs(),blogItem));
    }

    @Test
    public void validGoodSimpleLogin(){
        if(!ForPreferences.validLogin("admin","admin")) assertTrue(false);
        if(ForPreferences.validLogin("admin2","admin")) assertTrue(false);
        if(ForPreferences.validLogin("admin","admin2")) assertTrue(false);
        if(ForPreferences.validLogin(null,null)) assertTrue(false);
        assertTrue(true);
    }


    public void setBlogandPostNull(){
        Utils.setBlogs(null);
        Utils.setPosts(null);
    }

    public void setBlogandPostforTest(){
        ForPreferences.loadBlogsandPosts(examplePostItem);
    }

    public void resetBlogandPostforTest(){
        setBlogandPostNull();
        setBlogandPostforTest();
    }
}
