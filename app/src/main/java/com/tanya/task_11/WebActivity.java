package com.tanya.task_11;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static br.com.dina.oauth.instagram.InstagramApp.mCallbackUrl;

public class WebActivity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wv = (WebView) findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new VkWebViewClient());
        final Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.instagram.com")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", "1577d809ec6744efbc98876f4ff650c1")
                .appendQueryParameter("redirect_uri", "https://www.instagram.com")
                .appendQueryParameter("response_type", "code");
        wv.loadUrl(uriBuilder.toString());

        System.out.println(wv.getUrl());
    }

    class VkWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            progress.setVisibility(View.VISIBLE);
            parseUrl(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            System.out.println("*/*/*/" + view.getUrl());
        }
    }

    private void parseUrl(String url) {

        if (url == null) {
            return;
        }

        if (!url.contains("error")) {
//            String[] auth = VKUtil.parseRedirectUrl(url);
//                    webview.setVisibility(View.GONE);
//                    progress.setVisibility(View.VISIBLE);

            //Строим данные
//                    Intent intent = new Intent();
//                    intent.putExtra("token", auth[0]);
//                    intent.putExtra("uid", auth[1]);
//                    System.out.println("token"+ auth[0] + " uid"+auth[1]);
            //Возвращаем данные
//                    setResult(Activity.RESULT_OK, intent);
            System.out.println("**********" + url);
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void getAccessToken(final String code) throws IOException {
        String mClientId = "1577d809ec6744efbc98876f4ff650c1",
                mClientSecret = "74daf857564743ef8de86c5f9223b477";

        URL url = new URL("https://api.instagram.com/oauth/access_token");
        //URL url = new URL(mTokenUrl + "&code=" + code);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        //urlConnection.connect();
        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
        writer.write("client_id=" + mClientId +
                "&client_secret=" + mClientSecret +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + mCallbackUrl +
                "&code=" + code);
        writer.flush();

        String response = streamToString(urlConnection.getInputStream());
        System.out.println(response);
//                    Log.i(TAG, "response " + response);
//                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
//
//                    mAccessToken = jsonObj.getString("access_token");
//                    Log.i(TAG, "Got access token: " + mAccessToken);
//
//                    String id = jsonObj.getJSONObject("user").getString("id");
//                    String user = jsonObj.getJSONObject("user").getString("username");
//                    String name = jsonObj.getJSONObject("user").getString("full_name");
//
//                    mSession.storeAccessToken(mAccessToken, id, user, name);


    }

    private String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }
}

