package com.myilmaz.firebaseordertesting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muzaffer YILMAZ on 7.1.2016.
 */
public class MyAdapter extends RecyclerView.Adapter {

    private ArrayList<String> mData = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(String text) {
        mData.add(text);
        notifyItemInserted(mData.size() - 1);
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }
}
