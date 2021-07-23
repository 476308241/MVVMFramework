package com.finest.chatlibrary.commons.model;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


public abstract class ViewHolder<DATA> extends BaseViewHolder {

    public abstract void onBind(DATA data);

    public ViewHolder(View itemView) {
        super(itemView);
    }
}
