package com.example.torogeldiev.twitter.twitter_viewing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torogeldiev.twitter.R;
import com.example.torogeldiev.twitter.adapter.RecyclerAdapter;
import com.example.torogeldiev.twitter.model.GetListTwiter;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.profile_image)
    CircleImageView imageView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_post_text)
    EditText text;
    @BindView(R.id.btn_for_sending_message)
    FloatingActionButton sendMessage;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private long idUser;
    private String token;
    private String sekret;
    private HomePresenter presenter;
    private RecyclerAdapter adapter;
    String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        idUser = getIntent().getLongExtra("userNameFromAuth", idUser);
        token = getIntent().getExtras().getString("userToken");
        sekret = getIntent().getExtras().getString("userTokenSekret");

//        int oauth_timestamp = (int) (new Date().getTime() / 1000);

        authorization = "OAuth oauth_consumer_key=\"" + getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY)
                + "\", oauth_token=\"" + token +
                "\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1518164753\", oauth_nonce=\"5eCW3lTkCSD\", oauth_version=\"1.0\", oauth_signature=\"Yga%2BXT%2B698BUW%2Fuh7rxXWkV6GGA%3D\"";

        presenter = new HomePresenter(this, this);

        functionFroGetListData();

        //Функция для авто обновления, период 7 сек.
        Timer repeatTask = new Timer();
        repeatTask.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        functionFroGetListData();
                    }
                });
            }
        }, 0, 60000);

    }

    @Override
    public void getListTwitter(List<GetListTwiter> listTwiters) {
        mSwipeRefreshLayout.setRefreshing(false);
        adapter = new RecyclerAdapter(this, listTwiters);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Picasso.with(this).load(String.valueOf(listTwiters.get(0).getUser().getProfile_image_url())).resize(420, 420).centerCrop().into(imageView);
        titleToolbar.setText(listTwiters.get(0).getUser().getName());
    }

    @Override
    public void onRefresh() {
        presenter.getListT(authorization, idUser);
    }

    void functionFroGetListData() {
        presenter.getListT(authorization, idUser);
    }

    //Данная функция не используется
    @Override
    public void sendTwitter(GetListTwiter getListTwiter) {
    }

    @Override
    public void getErrorBody(String errorToast) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, errorToast, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_for_sending_message)
    void send() {
        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();
        final Intent intent = new ComposerActivity.Builder(Home.this)
                .session(session)
                .createIntent();
        startActivity(intent);
    }

//    public static String getHash() {
//        String base64EncodedString = null;
//        try {
//            String encodedConsumerKey = URLEncoder.encode(String.valueOf(R.string.com_twitter_sdk_android_CONSUMER_KEY), "UTF-8");
//            String encodedConsumerSecret = URLEncoder.encode(String.valueOf(R.string.com_twitter_sdk_android_CONSUMER_SECRET), "UTF-8");
//            String authString = encodedConsumerKey + ":" + encodedConsumerSecret;
//            base64EncodedString = Base64.encodeToString(authString.getBytes("UTF-8"), Base64.NO_WRAP); //Changed here!!!
//        } catch (Exception ex) {
//            //do nothing for now...
//        }
//        return base64EncodedString;
//    }
//
//    public static String getHash() {
//        byte[] bytes = "mobileId:secret".getBytes(StandardCharsets.UTF_8);
//        return Base64.encodeToString(bytes, 2);
//    }
//
//    private byte[] generateSignature(String signatueBaseStr, String oAuthConsumerSecret, String oAuthTokenSecret) {
//        byte[] byteHMAC = null;
//        try {
//            Mac mac = Mac.getInstance("HmacSHA1");
//            SecretKeySpec spec;
//            if (null == oAuthTokenSecret) {
//                String signingKey = encode(oAuthConsumerSecret) + '&';
//                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
//            } else {
//                String signingKey = encode(oAuthConsumerSecret) + '&' + encode(oAuthTokenSecret);
//                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
//            }
//            mac.init(spec);
//            byteHMAC = mac.doFinal(signatueBaseStr.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Base64.decode(byteHMAC, 2);
//    }
}
