package com.giotec.mini_tumblr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.giotec.mini_tumblr.Models.Blog_item;
import com.giotec.mini_tumblr.Utils.Utils;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    private String TAG = "GIODEBUG_TBMLR";
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin = findViewById(R.id.blogin);
    }

    public void Login(View view){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.mixed_anim);
        new ConectarTumblr().execute();
        bLogin.startAnimation(animation);
        bLogin.setEnabled(false);
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

            Utils.getSingletonInstance(client);
            Utils.setBlogs(getAllBlogs(client));

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("limit", 10);
            List<Post> posts = client.userDashboard(params);
            Utils.setPosts(posts);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            bLogin.setEnabled(true);
            finish();
        }
    }

    private List<Blog_item> getAllBlogs(JumblrClient client){
        List<Blog_item> mblogs = new ArrayList<>();
        User user = client.user();
        for (Blog blog : user.getBlogs())
            mblogs.add(new Blog_item(blog.getName(),blog.avatar()));

        for (Blog blog : client.userFollowing())
            mblogs.add(new Blog_item(blog.getName(),blog.avatar()));

        return mblogs;
    }
}
