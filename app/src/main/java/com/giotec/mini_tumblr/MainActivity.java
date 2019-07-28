package com.giotec.mini_tumblr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.giotec.mini_tumblr.Fragments.Chat;
import com.giotec.mini_tumblr.Fragments.Home.Home;
import com.giotec.mini_tumblr.Fragments.Profile;
import com.giotec.mini_tumblr.Fragments.Search;
import com.giotec.mini_tumblr.Utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Home.OnFragmentInteractionListener, Search.OnFragmentInteractionListener, Chat.OnFragmentInteractionListener, Profile.OnFragmentInteractionListener{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;
    private String TAG = "GIODEBUG_TBMLR";

    private static final int PERMISSION_ALL = 3;
    private final int code_request=1234;
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WAKE_LOCK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!hasPermissions(this, PERMISSIONS))
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);adapter = new TabAdapter(getSupportFragmentManager());
        AddFragmentsAndSetAdapter();
        tabLayout.setupWithViewPager(viewPager);
        setViewPagerandIcons();
        //new ConectarTumblr().execute();
        //setRecyclerAndPost();
    }



    private class ConectarTumblr extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG,"Comenzando");
            // Create a new client

            JumblrClient client = new JumblrClient(
                    "g7IlEjYtczUXLlul4sVtpNVsZEqQBDAVSzCltVjlRc4Gjz1D1S",
                    "u8xM2f6XpClNCIQJxuulP30mYCj9st3DPQJ7vJQYTlPEhFrVYO"
            );
            client.setToken(
                    "VAymU1RyN3pMquPLI98S71h5TzYSisJNQl4YnTUewS1qNR8dtn",
                    "r8DGrGwSZgoUsOZTyo7rQqxli21IOXU1OtlWo3xpugocFGM7JG"
            );


            // Write the user's name
            User user = client.user();
            Log.d(TAG,user.getName());

            //List<Post> posts = client.userDashboard();
            //Post mpost = posts.get(0);
            //Log.d(TAG,mpost.toString() );
            List<Blog> blogs = client.userFollowing();
            //Utils utils = Utils.getSingletonInstance(blogs);
            /*for (Blog blog : blogs) {
                Log.d(TAG,blog.getName());//Nombre
                Log.d(TAG,blog.avatar());//Link para la imagen
                Log.d(TAG,blog.getTitle());//tittle de mrd
            }*/
            // Make the request
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("limit", 1);

            params.put("notes_info", true);
            List<Post> posts = client.userDashboard(params);

/*
            PhotoPost mpost= (PhotoPost) posts.get(0);

            if(mpost.getNotes()!=null){
                List<Note>  notes = mpost.getNotes();
                Log.d(TAG,"getReplyText\t"+notes.get(0).getReplyText() );
                Log.d(TAG,"getAddedText\t"+notes.get(0).getAddedText());
            }

            //Log.d(TAG,"getAuthorId\t"+mpost.getAuthorId() );
            Log.d(TAG,"getPostUrl\t"+mpost.getPostUrl() );
            Log.d(TAG,"getShortUrl\t"+mpost.getShortUrl() );
            //Log.d(TAG,"getState\t"+mpost.getState());
            Log.d(TAG,"getBlogName\t"+mpost.getBlogName());
            //Log.d(TAG,"getFormat\t"+mpost.getFormat());
            Log.d(TAG,"getTimestamp\t"+mpost.getTimestamp());
            Log.d(TAG,"getTags\t"+mpost.getTags());

            Log.d(TAG,"getId\t"+mpost.getId());
            //Log.d(TAG,"getSourceTitle\t"+mpost.getSourceTitle());
            Log.d(TAG,"getType\t"+mpost.getType());

            Log.d(TAG,"getSlug\t"+mpost.getSlug() );
            Log.d(TAG,"getSourceTitle\t"+mpost.getSourceTitle() );


            Log.d(TAG,"getSlug\t"+mpost.getSlug() );
            Log.d(TAG,"getSourceTitle\t"+mpost.getSourceTitle() );
            Log.d(TAG,"getCaption\t"+mpost.getCaption() );

*/
            return null;
        }
    }

    private void AddFragmentsAndSetAdapter(){
        adapter.addFragment(new Home(), "Home");
        adapter.addFragment(new Search(), "Search");
        adapter.addFragment(new Chat(), "Chat");
        adapter.addFragment(new Profile(), "Profile");
        viewPager.setAdapter(adapter);
    }

    private void setViewPagerandIcons(){
        int[] tabIcons = {
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_search_black_24dp,
                R.drawable.ic_chat_bubble_black_24dp,
                R.drawable.ic_person_black_24dp
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case code_request:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "COARSE LOCATION permitido", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "COARSE LOCATION no permitido", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
