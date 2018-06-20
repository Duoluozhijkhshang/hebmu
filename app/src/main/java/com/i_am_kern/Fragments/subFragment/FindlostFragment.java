package com.i_am_kern.Fragments.subFragment;

import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.i_am_kern.Class.Post;
import com.i_am_kern.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by florentchampigny on 24/04/15.
 */
public class FindlostFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private List<Post> postList;
    private MultipleItemQuickAdapter adapter;
    private int posttype;
    public static FindlostFragment newInstance(int posttype) {
        FindlostFragment findlostFragment = new FindlostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("posttype",posttype);
        findlostFragment.setArguments(bundle);
        return findlostFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initdata() {
        BmobQuery<Post> bmobQuery = new BmobQuery<Post>();
        bmobQuery.include("author.realname");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e==null){
                    if (list.isEmpty()){
                        ProgressBar view = adapter.getEmptyView().findViewById(R.id.post_loadprogressBar);
                        TextView textView = adapter.getEmptyView().findViewById(R.id.post_loading);
                        view.setVisibility(View.GONE);
                        textView.setText("竟然没有帖子0_0！");
                    }
                    for (Post post:list){
                        adapter.addData(post);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    ToastUtils.showLong("帖子加载失败"+e);
                }

            }
        });
    }

    public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<Post, BaseViewHolder> {

        public MultipleItemQuickAdapter(List data) {
            super(data);
            addItemType(0,R.layout.post_recycleview_item_text);

            addItemType(2, R.layout.post_recycleview_item_multipic1);
            addItemType(1, R.layout.post_recycleview_item_singlepic);
        }
        @Override
        protected void convert(BaseViewHolder helper, Post item) {

            switch (item.getItemType()){
            case 0:
            helper.setText(R.id.post_author0,item.getAuthor().getRealname());
            helper.setText(R.id.post_content0,item.getContent());
            helper.setText(R.id.post_title0,item.getTitle());
            //helper.setVisible(R.id.post_icon0, false);
            helper.setText(R.id.post_sumcommentnum0,"999");

            break;
            case 1:
                helper.setText(R.id.post_author1,item.getAuthor().getRealname());
                helper.setText(R.id.post_content1,item.getContent());
                helper.setText(R.id.post_title1,item.getTitle());
                //helper.setVisible(R.id.post_icon1, false);
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(0)).into((ImageView) helper.getView(R.id.post_pic1));
                helper.setText(R.id.post_sumcommentnum1,"999");
                break;
            default:
                helper.setText(R.id.post_author2,item.getAuthor().getRealname());
                helper.setText(R.id.post_content2,item.getContent());
                helper.setText(R.id.post_title2,item.getTitle());
                helper.setVisible(R.id.post_icon2, true);
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(0)).into((ImageView) helper.getView(R.id.post_pic21));
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(1)).into((ImageView) helper.getView(R.id.post_pic22));
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(2)).into((ImageView) helper.getView(R.id.post_pic23));
                helper.setText(R.id.post_sumcommentnum2,"999");
                break;

        }
    }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        smartRefreshLayout = getView().findViewById(R.id.findlost_refreshLayout1);
        recyclerView = getView().findViewById(R.id.findlost_RecycleView);
        imageView = getView().findViewById(R.id.findlost_imageview);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000,true);
            }
        });
        adapter = new MultipleItemQuickAdapter(postList);
        postList = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
//        linearLayout.setSmoothScrollbarEnabled(true);
//        linearLayout.setAutoMeasureEnabled(true);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.openLoadAnimation();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(R.layout.postlist_emptyview);
        Glide.with(getContext()).load(R.drawable.a2).into(imageView);


        initdata();

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(">>>","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(">>.","onDestory");
    }
}
