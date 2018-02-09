package com.example.torogeldiev.twitter.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.torogeldiev.twitter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by torogeldiev on 06.02.2018.
 */

public class UserTwitterHolger extends RecyclerView.ViewHolder{

    @BindView(R.id.v_user_image)
    public ImageView avatar;
    @BindView(R.id.tv_user_name)
    public TextView name;
    @BindView(R.id.tv_text_twitter)
    public TextView note;
    @BindView(R.id.tv_amount_favorite)
    public TextView amountFavorite;

    public UserTwitterHolger(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
