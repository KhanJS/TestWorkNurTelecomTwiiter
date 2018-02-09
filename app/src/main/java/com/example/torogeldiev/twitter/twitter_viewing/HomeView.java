package com.example.torogeldiev.twitter.twitter_viewing;

import com.example.torogeldiev.twitter.model.GetListTwiter;

import java.util.List;

/**
 * Created by torogeldiev on 06.02.2018.
 */

public interface HomeView {
    void getListTwitter(List<GetListTwiter> listTwiters);
    void getErrorBody(String errorToast);
}
