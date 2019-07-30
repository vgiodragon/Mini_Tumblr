package com.giotec.mini_tumblr.Utils;

import android.content.Context;

import com.giotec.mini_tumblr.R;
import com.tumblr.jumblr.JumblrClient;

public class Connections {
    private static JumblrClient client;


    public static JumblrClient getClient() {
        return client;
    }

    public static JumblrClient getClientwithToken(Context ctx){
        JumblrClient client = new JumblrClient(
                ctx.getString(R.string.consumerKey),
                ctx.getString(R.string.consumerSecret));

        client.setToken(ctx.getString(R.string.token),
                ctx.getString(R.string.tokenSecret));

        return client;
    }

}
