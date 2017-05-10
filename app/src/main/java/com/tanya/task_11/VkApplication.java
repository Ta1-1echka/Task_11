package com.tanya.task_11;

import android.app.Activity;
import android.app.Application;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;

/**
 * Created by Ta1-1 on 10.05.2017.
 */

public class VkApplication extends Application {
    VKAccessToken access_token;

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        access_token = VKAccessToken.tokenFromSharedPreferences(this, "VK_ACCESS_TOKEN");
    }

    public void login(Activity activity) {
        VKSdk.login(activity, "wall", "offline");
    }
}
