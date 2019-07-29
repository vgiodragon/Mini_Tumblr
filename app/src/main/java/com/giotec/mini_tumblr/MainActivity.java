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
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.giotec.mini_tumblr.Fragments.Chat;
import com.giotec.mini_tumblr.Fragments.Home.Home;
import com.giotec.mini_tumblr.Fragments.Profile;
import com.giotec.mini_tumblr.Fragments.Search;
import com.google.android.material.tabs.TabLayout;

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
