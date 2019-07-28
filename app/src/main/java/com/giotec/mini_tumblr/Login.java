package com.giotec.mini_tumblr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.giotec.mini_tumblr.Models.Blog_item;
import com.giotec.mini_tumblr.Utils.Utils;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    private String TAG = "GIODEBUG_TBMLR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View view){
        new ConectarTumblr().execute();

    }

    private class ConectarTumblr extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG,"Comenzando");
            // Create a new client

            JumblrClient client = new JumblrClient(
                    getString(R.string.consumerKey),
                    getString(R.string.consumerSecret));

            client.setToken(getString(R.string.token),
                    getString(R.string.tokenSecret));

            List<Blog_item> mblogs = new ArrayList<>();

            for (Blog blog : client.userFollowing())
                mblogs.add(new Blog_item(blog.getName(),blog.avatar()));

            Utils.getSingletonInstance(mblogs);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("limit", 3);

            List<Post> posts = client.userDashboard(params);
            Utils.setPosts(posts);
            PhotoPost mpost= (PhotoPost) posts.get(0);
            //List<Photo> mphoto = mpost.getPhotos();
            //Log.d(TAG,mphoto.get(0).);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
