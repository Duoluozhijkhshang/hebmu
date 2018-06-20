package com.i_am_kern.Adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.i_am_kern.Class.Post;
import com.i_am_kern.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Findlost_RecyclerviewAdapter extends BaseQuickAdapter<Post, BaseViewHolder> {
    private List<Post> posts;

    public Findlost_RecyclerviewAdapter(int layoutResId, @Nullable List<Post> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {
//        ((SuperTextView)helper.getView(R.id.super_tv)).setLeftTopString(item.getTitle()).setLeftBottomString(item.getTime());
//        Picasso.with(mContext).load(item.getImgUrl()).placeholder(R.drawable.ic_person
//        ).into(((SuperTextView) helper.getView(R.id.super_tv))
//                .getLeftIconIV());
    }

}
