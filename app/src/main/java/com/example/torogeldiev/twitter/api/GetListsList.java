package com.example.torogeldiev.twitter.api;

import com.example.torogeldiev.twitter.model.GetListTwiter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by torogeldiev on 06.02.2018.
 */

public interface GetListsList {

    @GET("1.1/statuses/user_timeline.json")
    Call<List<GetListTwiter>> getListTwitter(@Header("Authorization") String authorization, @Query("user_id") long idUser);

    //Запрос не используется
    @POST("1.1/statuses/update.json")
    Call<GetListTwiter> postNewTwitter(@Header("Authorization") String authorization, @Query("status") String textForTweet);
}
