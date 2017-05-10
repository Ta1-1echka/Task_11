package com.tanya.task_11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

import br.com.dina.oauth.instagram.InstagramApp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button vk_button;
    LoginButton loginButton;
    CallbackManager mFacebookCallbackManager;
    TextView textView;
    ImageView imageView;

    Button vk;
    Button insta;

    VkApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        vk_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
//                startActivity(intent);
///*
//
//vk  6019074  BOUnuVi4yS5UPmQ7zfEX
//facebook 1942039502696027  c72bd171cedf4687325102ac6c012611
//insta 1577d809ec6744efbc98876f4ff650c1 74daf857564743ef8de86c5f9223b477
//https://pnixx.ru/blog/Android_i_vkontakte_chast_2_%28avtorizatsiya_i_poluchenie_prav%29-6.html
//https://auth0.com/blog/how-to-authenticate-on-android-using-social-logins/
// */
//            }
//        });
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_main);

        application = new VkApplication();

        vk = (Button) findViewById(R.id.vk);
        insta = (Button) findViewById(R.id.insta);
        vk.setOnClickListener(this);
        insta.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // Callback registration
        loginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }


        });
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    textView.setText("");
                    imageView.setImageBitmap(null);
                }
            }


        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("*******" + requestCode + " " + resultCode);
        if (requestCode == 64206) {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
            textView.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
            Picasso.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(1200, 1200)).into(imageView);
        } else if (requestCode == 10485) {
            VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max_orig"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    VKApiUserFull user = ((VKList<VKApiUserFull>) response.parsedModel).get(0);
                    textView.setText(user.first_name + " " + user.last_name);
                    Picasso.with(getApplicationContext()).load(user.photo_max_orig).into(imageView);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vk:
                application.login(this);
                break;
            case R.id.insta:
                signInWithInstagram();

                break;
            default:
                break;
        }
    }

    private void signInWithInstagram() {
        InstagramApp mApp = new InstagramApp(this,
                ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET,
                ApplicationData.CALLBACK_URL);
        mApp.authorize();
        System.out.println("****" +mApp.getUserName() );
        textView.setText(mApp.getUserName());
//        final Intent browser = new Intent(getApplicationContext(), WebActivity.class);
//        startActivity(browser);
    }
}
