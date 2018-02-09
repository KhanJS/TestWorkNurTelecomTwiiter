package com.example.torogeldiev.twitter.twitter_viewing;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.torogeldiev.twitter.R;
import com.example.torogeldiev.twitter.adapter.RecyclerAdapter;
import com.example.torogeldiev.twitter.model.GetListTwiter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.main_avatar_user)
    ImageView avatar;
    @BindView(R.id.tv_user_name)
    TextView name;
    @BindView(R.id.send_message)
    TextView sendMessage;
    @BindView(R.id.et_post_text)
    EditText text;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private long idUser;
    private HomePresenter presenter;
    private RecyclerAdapter adapter;
    String authorization = "OAuth oauth_consumer_key=\"Q3MVyWlEoRY6FTfMr1Wa4je8n\", oauth_token=\"961630064285798400-9r4tvC9o6W7AvjHaP3ofZHVshSepeQ5\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1518110055\", oauth_nonce=\"Cp9UlCCLkHq\", oauth_version=\"1.0\", oauth_signature=\"pUAaDhDiRyJ7oaMfgSY2l9mfWJU%3D\"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        idUser = getIntent().getLongExtra("userNameFromAuth", idUser);

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
        }, 0, 5000);

    }

    @Override
    public void getListTwitter(List<GetListTwiter> listTwiters) {
        mSwipeRefreshLayout.setRefreshing(false);
        adapter = new RecyclerAdapter(this, listTwiters);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Picasso.with(this).load(String.valueOf(listTwiters.get(0).getUser().getProfile_image_url())).resize(640,640).centerCrop().into(avatar);
        name.setText(listTwiters.get(0).getUser().getName());

    }

    @Override
    public void getErrorBody(String errorToast) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, errorToast, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.send_message)
    void send() {
        if (!text.getText().equals("") && text.getText() != null) {
            String texts = text.getText().toString();
            presenter.sendMessage(authorization, texts);
        } else {
            Toast.makeText(this, "Поле для твита пустое!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        presenter.getListT(authorization, idUser);
    }

    void functionFroGetListData() {
        presenter.getListT(authorization, idUser);
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
}
