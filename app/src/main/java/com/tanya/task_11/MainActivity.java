package com.tanya.task_11;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button vk_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vk_button = (Button) findViewById(R.id.vk);
        vk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                startActivity(intent);
/*

vk  6019074  BOUnuVi4yS5UPmQ7zfEX
facebook 1942039502696027  c72bd171cedf4687325102ac6c012611
insta 1577d809ec6744efbc98876f4ff650c1 74daf857564743ef8de86c5f9223b477
https://pnixx.ru/blog/Android_i_vkontakte_chast_2_%28avtorizatsiya_i_poluchenie_prav%29-6.html
https://auth0.com/blog/how-to-authenticate-on-android-using-social-logins/
 */
            }
        });

    }


}
