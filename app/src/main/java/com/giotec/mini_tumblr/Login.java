package com.giotec.mini_tumblr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.giotec.mini_tumblr.MainActivity.MainActivity;
import com.giotec.mini_tumblr.Models.BlogItem;
import com.giotec.mini_tumblr.Utils.Connections;
import com.giotec.mini_tumblr.Utils.G_Utils;
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
    private EditText etUser;
    private EditText etPassword;

    private static final int PERMISSION_ALL = 3;
    private final int code_request=1234;
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WAKE_LOCK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin = findViewById(R.id.blogin);
        etUser = findViewById(R.id.etusuario);
        etPassword = findViewById(R.id.etpassword);
        if(!hasPermissions(this, PERMISSIONS))
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }

    public void Login(View view){
        String user = etUser.getText().toString();
        String pass = etPassword.getText().toString();
        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
        if(G_Utils.validLogin(user,pass)) GoMainActivity();
        else Toast.makeText(this,getString(R.string.login_failed),Toast.LENGTH_LONG).show();

    }

    public void GoMainActivity(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.mixed_anim);
        new ConectarTumblr().execute();
        bLogin.startAnimation(animation);
        bLogin.setEnabled(false);
        etUser.setVisibility(View.INVISIBLE);
        etPassword.setVisibility(View.INVISIBLE);
    }


    private class ConectarTumblr extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d(TAG,"Comenzando");
            // Create a new client
            JumblrClient client = Connections.getClientwithToken(getBaseContext());

            try {
                Utils.setBlogs(getAllBlogs(client));
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("limit", Utils.getLimit());
                List<Post> posts = client.userDashboard(params);
                Utils.FilterSetAndSavePosts(posts,getApplicationContext());
                Connections.setClient(client);
            }catch (Exception ex) {
                Log.d(TAG,ex.getMessage());
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean HayConexion) {
            super.onPostExecute(HayConexion);

            if(!HayConexion)
                if(!G_Utils.isTherePreferences(getApplicationContext())){
                    Toast.makeText(getBaseContext(), getString(R.string.noInternet),
                            Toast.LENGTH_LONG).show();
                    bLogin.clearAnimation();
                    bLogin.setEnabled(true);
                    etUser.setVisibility(View.VISIBLE);
                    etPassword.setVisibility(View.VISIBLE);
                    return;
                }

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            bLogin.setEnabled(true);
        }
    }

    private List<BlogItem> getAllBlogs(JumblrClient client){
        List<BlogItem> mblogs = new ArrayList<>();
        User user = client.user();
        for (Blog blog : user.getBlogs())
            mblogs.add(new BlogItem(blog.getName(),blog.avatar()));

        for (Blog blog : client.userFollowing())
            mblogs.add(new BlogItem(blog.getName(),blog.avatar()));

        return mblogs;
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
