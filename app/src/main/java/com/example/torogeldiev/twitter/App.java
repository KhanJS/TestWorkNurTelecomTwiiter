package com.example.torogeldiev.twitter;

import android.app.Application;

import com.example.torogeldiev.twitter.api.GetListsList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by torogeldiev on 06.02.2018.
 */

public class App extends Application {

    private static GetListsList getListTwiter;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder()
                .setDateFormat("EEE MMM dd yyyy hh:mm aaa")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        getListTwiter = retrofit.create(GetListsList.class);
    }

    public static GetListsList getGetListTwiter() {
        return getListTwiter;
    }
}
