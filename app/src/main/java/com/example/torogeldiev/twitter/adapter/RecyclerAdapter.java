package com.example.torogeldiev.twitter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.torogeldiev.twitter.R;
import com.example.torogeldiev.twitter.adapter.holder.UserTwitterHolger;
import com.example.torogeldiev.twitter.model.GetListTwiter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by torogeldiev on 06.02.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<GetListTwiter> list;

    public RecyclerAdapter(Context context, List<GetListTwiter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_for_list, parent, false);
        return new UserTwitterHolger(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserTwitterHolger userTwitterHolger = (UserTwitterHolger) holder;
        GetListTwiter getListTwiter = list.get(position);
        Picasso.with(context).load(String.valueOf(getListTwiter.getUser().getProfile_image_url())).resize(640, 640).centerCrop().centerCrop().into(userTwitterHolger.avatar);
        userTwitterHolger.name.setText(getListTwiter.getUser().getName());
        userTwitterHolger.note.setText(getListTwiter.getText());
        userTwitterHolger.amountFavorite.setText(getListTwiter.getFavorite_count() != 0 ? String.valueOf(getListTwiter.getFavorite_count()) : "0");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
