package com.example.torogeldiev.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.torogeldiev.twitter.twitter_viewing.Home;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginTwitterActivity extends AppCompatActivity {

    @BindView(R.id.btn_for_enter)
    TwitterLoginButton loginButton;
    private TwitterAuthClient mTwitterAuthClient;
    private long userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);

        setContentView(R.layout.activity_main_twitter);
        ButterKnife.bind(this);
        mTwitterAuthClient = new TwitterAuthClient();

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                login(session);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    private void login(TwitterSession session) {

        userName = session.getUserId();

        Intent intent = new Intent(LoginTwitterActivity.this, Home.class);
        intent.putExtra("userNameFromAuth", userName);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
