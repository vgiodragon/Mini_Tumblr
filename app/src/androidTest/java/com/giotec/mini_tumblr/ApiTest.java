package com.giotec.mini_tumblr;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.giotec.mini_tumblr.Utils.Connections;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.User;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApiTest {

    @Test
    public void apiTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        JumblrClient client = Connections.getClientwithToken(appContext);
        // Make the request
        try {
            User user = client.user();
        }catch (Exception ex) {
            assertTrue(false);
        }
        assertTrue(true);
    }



}
