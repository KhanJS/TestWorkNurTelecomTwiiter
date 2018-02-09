package com.example.torogeldiev.twitter.twitter_viewing;

import android.content.Context;
import android.util.Log;

import com.example.torogeldiev.twitter.App;
import com.example.torogeldiev.twitter.model.GetListTwiter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by torogeldiev on 06.02.2018.
 */

public class HomePresenter {
    private Context context;
    private HomeView homeView;

    public HomePresenter(Context context, HomeView homeView) {
        this.context = context;
        this.homeView = homeView;
    }

    void getListT(String authorization, long userId) {
        App.getGetListTwiter().getListTwitter(authorization, userId).enqueue(new Callback<List<GetListTwiter>>() {
            @Override
            public void onResponse(Call<List<GetListTwiter>> call, Response<List<GetListTwiter>> response) {
                if (response.isSuccessful()) {
                    homeView.getListTwitter(response.body());
                } else {
                    homeView.getErrorBody(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<GetListTwiter>> call, Throwable t) {
                Log.e("ERROR FAILURE: ", t.getMessage());
            }
        });
    }
    void sendMessage(String authorization, String textTweet){
        App.getGetListTwiter().postNewTwitter(authorization, textTweet).enqueue(new Callback<GetListTwiter>() {
            @Override
            public void onResponse(Call<GetListTwiter> call, Response<GetListTwiter> response) {
                if(response.isSuccessful()){
                    homeView.sendTwitter(response.body());
                }else{
                    homeView.getErrorBody("");
                }
            }

            @Override
            public void onFailure(Call<GetListTwiter> call, Throwable t) {

            }
        });
    }
}
