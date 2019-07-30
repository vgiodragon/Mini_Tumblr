package com.giotec.mini_tumblr.UI.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.giotec.mini_tumblr.UI.Fragments.Chat;
import com.giotec.mini_tumblr.UI.Fragments.Home.Home;
import com.giotec.mini_tumblr.UI.Fragments.Profile;
import com.giotec.mini_tumblr.UI.Fragments.Search;
import com.giotec.mini_tumblr.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements Home.OnFragmentInteractionListener, Search.OnFragmentInteractionListener, Chat.OnFragmentInteractionListener, Profile.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter adapter;
    private String TAG = "GIODEBUG_TBMLR";
    private Home home;
    private int positionToRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);adapter = new TabAdapter(getSupportFragmentManager());
        AddFragmentsAndSetAdapter();
        tabLayout.setupWithViewPager(viewPager);
        setViewPagerandIcons();
    }

    private void AddFragmentsAndSetAdapter(){
        home = new Home().newInstance(this);
        adapter.addFragment(home, "Home");
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

    @Override
    public void setLike(ImageView iv_like, boolean like){
        if(like){
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
            iv_like.setImageResource(R.drawable.ic_favorite_red_24dp);
            iv_like.startAnimation(animation);
        }
        else iv_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    @Override
    public void Eliminate(View v, int position) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();
        positionToRemove=position;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_eliminar:
                        home.RemovePost(positionToRemove);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

}
