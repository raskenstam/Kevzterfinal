package com.example.stn.firebasetest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    //private DrawerLayout mDrawerLayout;
    private Fragment fragment = null;
    Class fragmentClass;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = "defchan";
            String channelName = "com.google.firebase.messaging.default_notification_channel_id";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("msg", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("msg", token);
                        // Log and toast


                    }
                });

        Globals g = Globals.getInstance();
        int data = g.getData();

        SharedPreferences mPrefs = getSharedPreferences("label", 0);


        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        SharedPreferences.Editor mEditor = mPrefs.edit();
        if (user.length() <= 1) {
            mEditor.putString("login", "Guest").commit();
            mEditor.putString("login", "Guest").apply();
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
            this.setTitle("Guest");

        } else {
            this.setTitle(user);
        }


        String profile = g.getUser();
        String email = g.getEmail();
        String pic = g.getPicture();
        Log.i("before", profile + email + pic);
        profile = mPrefs.getString("login", "0");
        email = mPrefs.getString("email", "0");
        pic = mPrefs.getString("pic", "0");

        Log.i("after", profile + email + pic);


        //mDrawerLayout = findViewById(R.id.drawer_layout);
        fragmentClass = fragment1.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();














        try {


        } catch (Exception e) {
            changetotwitchactivity();
        }
        ImageView twitchbut = (ImageView) findViewById(R.id.twitchtext);

        //TextView text = (TextView) header.findViewById(R.id.textView2);
        //text.setText(profile);

                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        Log.i("INTENTINTENTINTENTINTE", menuItem.toString());
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        //mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if (menuItem.getItemId() == R.id.menu2) {
                            fragmentClass = fragment2.class;
                        } else if (menuItem.getItemId() == R.id.menu3) {
                            //snapchat
                            fragmentClass = fragment3.class;
                        } else if (menuItem.getItemId() == R.id.menu4) {
                            //instagram
                            fragmentClass = fragment4.class;
                        } else if (menuItem.getItemId() == R.id.menu5) {
                            //twitch
                            fragmentClass = fragment5.class;
                        } else if (menuItem.getItemId() == R.id.menu6) {
                            //twitch
                            //fragmentClass = com.example.oauthtest.kevzterfinal.fortnitefragment.class;
                        }
                        else if (menuItem.getItemId() == R.id.menu7) {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                        return true;
                    }
                };
        //firebase stuff for notifications
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNoti = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNoti.createNotificationChannel(mChannel);


        }
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //fortniteshop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fortniteshopf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            post1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final OkHttpClient client = new OkHttpClient();

    public void changetotwitchactivity() {
        Log.i("INTENTINTENTINTENTINTE", "changerun");
        Intent intent = new Intent(this, twitchAuthO.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        SharedPreferences.Editor mEditor = mPrefs.edit();
        if (user == "0") {
            Log.i("workflow", "" + "user == 0  ondestroy mainactivity" + saved + "" + user);
            mEditor.putString("saved", "0").commit();
            mEditor.putString("saved", "0").apply();

        } else {
            Log.i("workflow", "" + "user == 1  ondestroy mainactivity" + saved + "" + user);
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
        }


        super.onDestroy();

    }

    public void onResume() {

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Log.i("tag", "onstart" + saved);
        if (user.length() <= 1) {

            mEditor.putString("login", "Guest").commit();
            mEditor.putString("login", "Guest").apply();
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();


        } else {
            Log.i("workflow", "" + "saved == 0 else  onResume mainactivity" + user.length() + user);
        }
        super.onResume();

    }

    public void onPause() {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Log.i("tag", "onp" + saved + user);
        if (user == "0") {
            Log.i("workflow", "" + "user == 0   onPause mainactivity " + saved);
            mEditor.putString("saved", "0").commit();
            mEditor.putString("saved", "0").apply();
            //changetotwitchactivity();
        } else {
            Log.i("workflow", "" + "user == 0 else   onPause mainactivity " + saved);
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
        }

        super.onPause();

    }

    public void onStop() {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        Log.i("tag", "onstop" + saved + user);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        if (user == "0") {
            Log.i("workflow", "" + "user == 0   onStop mainactivity " + saved);
            mEditor.putString("saved", "0").commit();
            mEditor.putString("saved", "0").apply();
        } else {
            Log.i("workflow", "" + "user == 0 else    onStop mainactivity " + saved);
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();

        }

        super.onStop();

    }

    public  void onclicktext(ImageView img,Fragment frag){
        ImageView imgFavorite = img;
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //here will the code to save  youtube data and refresh every4h
    public void run() throws Exception {
        Log.i("json", "start");
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCl1ihalsfme2TpTFg1TPXdg&maxResults=20&order=date&key=AIzaSyAYUGkL36EioOyA4EfyQL9vjm0-lf7JW5s")
                .build();
        Log.i("url", "" + request.toString());
        Log.i("header", "" + request.headers());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("INTENTINTENTINTENTINTE", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("json", "start1");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Log.i("json", "start2");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String in = response.body().string();
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + in);


                    try {


                        JSONObject jObj1 = new JSONObject(in);
                        JSONArray data = jObj1.getJSONArray("items");
                        int secondloopparam = 0;
                        for (int i = 0; i < 20; i++) {
                            String temperature = data.getString(i);
                            JSONObject jObj2 = new JSONObject(temperature);
                            String temp2 = jObj2.getString("snippet");
                            JSONObject jObj3 = new JSONObject(temp2);
                            //gettitel here
                            String temp3 = jObj3.getString("thumbnails");
                            //Getthumbnailhere
                            JSONObject jObj4 = new JSONObject(temp3);
                            String temp4 = jObj4.getString("medium");
                            JSONObject jObj5 = new JSONObject(temp4);
                            String temp5 = jObj2.getString("id");
                            JSONObject jObj6 = new JSONObject(temp5);
                            String bild = jObj5.getString("url");
                            String titel = jObj3.getString("title");
                            String typee = jObj6.getString("kind");
                            //bild

                            try {
                                String videoId = jObj6.getString("videoId");
                                SharedPreferences mPrefs = getSharedPreferences("label", 0);
                                SharedPreferences.Editor mEditor = mPrefs.edit();
                                String loopintt = String.valueOf(secondloopparam);
                                mEditor.putString("titel" + loopintt, titel);
                                mEditor.putString("bild" + loopintt, bild);
                                mEditor.putString("videoId" + loopintt, videoId);
                                mEditor.apply();
                                mEditor.commit();
                                Log.i("loopjson", "video" + secondloopparam);
                                secondloopparam = secondloopparam + 1;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        String loopinttt = String.valueOf(secondloopparam);
                        mEditor.putString("numberof", loopinttt).apply();
                        mEditor.commit();

                        String saved = mPrefs.getString("titel2", "0");
                        Log.i("titel", saved);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }


    public void fortniteshop() throws Exception {
        Log.i("json", "start");
        Request request = new Request.Builder()
                .url("https://fnbr.co/api/shop").removeHeader("tags").addHeader("x-api-key", "265f9a53-b672-4db9-968d-d80c06c06264")
                .build();
        Log.i("urlf", "" + request.toString());
        Log.i("headerf", "" + request.headers());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("INTENTINTENTINTENTINTE", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("json", "start1");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Log.i("json", "start2");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String in = response.body().string();
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + in);


                    try {


                        JSONObject jObj1 = new JSONObject(in);
                        String temp = jObj1.getJSONObject("data").getString("daily");
                        JSONArray jarray = new JSONArray(temp);
                        temp = jarray.getString(1);
                        jObj1 = new JSONObject(temp);
                        temp = jObj1.getString("images");
                        jObj1 = new JSONObject(temp);
                        temp = jObj1.getString("gallery");
                        System.out.println("forttest" + temp);
                        int secondloopparam = 0;
                        int lenghtcounter = 0;
                        for (int i = 0; i < 10; i++) {


                            try {
                                temp = jarray.getString(secondloopparam);
                                jObj1 = new JSONObject(temp);
                                temp = jObj1.getString("images");
                                jObj1 = new JSONObject(temp);
                                if (jObj1.getString("gallery") == "false") {
                                    temp = jObj1.getString("icon");
                                } else {
                                    temp = jObj1.getString("gallery");
                                }


                                //String videoId = jObj6.getString("videoId");
                                SharedPreferences mPrefs = getSharedPreferences("fortnite", 0);
                                SharedPreferences.Editor mEditor = mPrefs.edit();
                                String loopintt = String.valueOf(secondloopparam);
                                if (temp.length() >= 0) {
                                    lenghtcounter++;
                                    mEditor.putInt("lenght", lenghtcounter);
                                }
                                mEditor.putString("shoppic" + loopintt, temp);

                                mEditor.apply();
                                mEditor.commit();
                                Log.i("loopjson1", "video" + temp);
                                secondloopparam = secondloopparam + 1;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    public void fortniteshopf() throws Exception {
        Log.i("json", "start");
        Request request = new Request.Builder()
                .url("https://fortnite-public-api.theapinetwork.com/prod09/store/get").removeHeader("tags")
                .build();
        Log.i("urlf", "" + request.toString());
        Log.i("headerf", "" + request.headers());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("INTENTINTENTINTENTINTE", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("json", "start1");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Log.i("json", "start2");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String in = response.body().string();
                    System.out.println("bbbbbbbbbbbbbbb" + in);


                    try {


                        JSONObject jObj1 = new JSONObject(in);
                        String temp = jObj1.getString("items");
                        JSONArray jarray = new JSONArray(temp);
                        temp = jarray.toString();
                        /*
                        temp = jarray.getString(1);
                        jObj1 = new JSONObject(temp);
                        temp = jObj1.getString("images");
                        jObj1 = new JSONObject(temp);
                        temp = jObj1.getString("gallery");
                        */

                        int secondloopparam = 0;
                        int lenghtcounter = 0;
                        for (int i = 0; i < 20; i++) {


                            try {
                                temp = jarray.getString(secondloopparam);
                                jObj1 = new JSONObject(temp);
                                temp = jObj1.getString("item");

                                jObj1 = new JSONObject(temp);
                                temp = jObj1.getString("images");

                                jObj1 = new JSONObject(temp);
                                temp = jObj1.getString("information");
                                Log.i("temptemp", "temp " + temp);
                                //String videoId = jObj6.getString("videoId");
                                SharedPreferences mPrefs = getSharedPreferences("fortnite", 0);
                                SharedPreferences.Editor mEditor = mPrefs.edit();
                                String loopintt = String.valueOf(secondloopparam);
                                if (temp.length() >= 0) {
                                    lenghtcounter++;
                                    mEditor.putInt("lenghtf", lenghtcounter);

                                    mEditor.putString("shoppic" + loopintt, temp);
                                }



                                mEditor.apply();
                                mEditor.commit();
                                Log.i("loopjson1", "video" + temp);
                                secondloopparam = secondloopparam + 1;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.get("text/x-markdown; charset=utf-8");


    final String basicAuth = "Basic " + Base64.encodeToString("kevzterclient:K3CvB3!30QvjiWk!l32Cv95XouL3GhwFv".getBytes(), Base64.NO_WRAP);

    public void post1() throws Exception {

        Request request = new Request.Builder()
                .url("https://trilleplay.net/proj-kevzter/restdata/api.php").removeHeader("tags").addHeader("Authorization", basicAuth).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, "{\n" +
                        "    \"email\": \"EMAIL\",\n" +
                        "    \"usrid\": \"Twitch Kontots unika ID.\",\n" +
                        "    \"username\": \"Twitch Anv Namn.\"\n" +
                        "}"))
                .build();
        Log.i("urld", "" + request.toString());
        Log.i("headerd", "" + request.headers());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("INTENTINTENTINTENTINTE", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String in = response.body().string();
                    System.out.println("bbbbbbbbbbbbbbbbbbb" + in + response);


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            fragment = (Fragment) fragment1.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
    public void showad(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

}
