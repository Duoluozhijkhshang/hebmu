package com.i_am_kern.Adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.i_am_kern.Class.Post;

import java.util.List;

public class Findlost_fragemnt_RecycleviewAdapter extends BaseQuickAdapter {
    public Findlost_fragemnt_RecycleviewAdapter(int layoutResId, @Nullable List<Post> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
