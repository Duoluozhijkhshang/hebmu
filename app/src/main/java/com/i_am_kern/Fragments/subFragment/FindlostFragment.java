package com.i_am_kern.Fragments.subFragment;

import android.content.Intent;
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
import com.hanks.htextview.base.HTextView;
import com.i_am_kern.Activities.Post_DetailActivity;
import com.i_am_kern.Class.Comment;
import com.i_am_kern.Class.Post;
import com.i_am_kern.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
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
import cn.bmob.v3.listener.CountListener;
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
    private  int i = 0;
    private Post apost;
    private boolean loadmoredataok;
    private int hasloaded_count;
    public static FindlostFragment newInstance(int posttype) {
        FindlostFragment findlostFragment = new FindlostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("posttype",posttype);
        findlostFragment.setArguments(bundle);
        return findlostFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            posttype = args.getInt("posttype");
        }
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
    private boolean loadmoredata(){

        BmobQuery<Post> bmobQuery = new BmobQuery<Post>();
        bmobQuery.include("author.realname");
        if (posttype!=0){
            bmobQuery.addWhereEqualTo("type",posttype);
        }

        bmobQuery.setLimit(10);
        bmobQuery.setSkip(hasloaded_count);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                smartRefreshLayout.finishLoadMore();
                if (e==null){
                    loadmoredataok =true;
                    hasloaded_count += 10;
                    if (list.isEmpty()){
                        HTextView view = adapter.getEmptyView().findViewById(R.id.htext);
                        view.animateText("竟然没有帖子0_0！");
                    }
                    for (Post post:list){

                        adapter.addData(post);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    loadmoredataok = false;
                    ToastUtils.showLong("帖子加载失败"+e);
                }

            }
        });
        return loadmoredataok;
    }
    private boolean initdata() {

        final BmobQuery<Post> bmobQuery = new BmobQuery<Post>();
        bmobQuery.include("author.realname");
        bmobQuery.addWhereEqualTo("type",posttype);
        bmobQuery.setLimit(10);

        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                smartRefreshLayout.finishRefresh();
                if (e==null){

                    hasloaded_count = 10;
                    adapter.setNewData(null);
                    if (list.isEmpty()){

                        HTextView view = adapter.getEmptyView().findViewById(R.id.htext);
                        view.animateText("竟然没有帖子0_0！");
                        return;
                    }postList = list;
                    i=0;
                    while (i<=postList.size()-1){
                        Log.e("i>>>>",i+"???");
                        get(i);
                        i++;
                    }
                    //while (i<=postList.size()-1){
                }else {
                    ToastUtils.showLong("帖子加载失败"+e);
                }

            }
        });



        if (hasloaded_count==10){
            return true;
        }else {
            return false;
        }
    }
    void get(int inum){
        BmobQuery<Comment> bmobQuery1 = new BmobQuery<Comment>();
        bmobQuery1.addWhereEqualTo("post",postList.get(inum).getObjectId());
        final int finalInum = inum;
        bmobQuery1.count(Comment.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    postList.get(finalInum).setSum_comment_num(integer-1);
                    adapter.addData(postList.get(finalInum));
                    adapter.notifyDataSetChanged();

                }else {
                    ToastUtils.showLong("帖子评论数获取失败！");
                    adapter.addData(postList.get(finalInum));
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
            helper.setText(R.id.post_sumcommentnum0,String.valueOf(item.getSum_comment_num()));

            break;
            case 1:
                helper.setText(R.id.post_author1,item.getAuthor().getRealname());
                helper.setText(R.id.post_content1,item.getContent());
                helper.setText(R.id.post_title1,item.getTitle());
                //helper.setVisible(R.id.post_icon1, false);
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(0)).into((ImageView) helper.getView(R.id.post_pic1));
                helper.setText(R.id.post_sumcommentnum1,String.valueOf(item.getSum_comment_num()));
                break;
            default:
                helper.setText(R.id.post_author2,item.getAuthor().getRealname());
                helper.setText(R.id.post_content2,item.getContent());
                helper.setText(R.id.post_title2,item.getTitle());
                helper.setVisible(R.id.post_icon2, true);
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(0)).into((ImageView) helper.getView(R.id.post_pic21));
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(1)).into((ImageView) helper.getView(R.id.post_pic22));
                Glide.with(getContext()).load(item.getSuoluetuUrls().get(2)).into((ImageView) helper.getView(R.id.post_pic23));
                helper.setText(R.id.post_sumcommentnum2,String.valueOf(item.getSum_comment_num()));
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
                 initdata();


            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e(">>>","loadmore");
                loadmoredata();


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
        adapter.setEmptyView(R.layout.emptyview);
        Glide.with(getContext()).load(R.drawable.a2).into(imageView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(">>>",((Post)(adapter.getData().get(position))).getObjectId()+"");
                Intent intent  = new Intent(getContext(), Post_DetailActivity.class);
                intent.putExtra("ObjectId",((Post)(adapter.getData().get(position))).getObjectId());
                intent.putExtra("sumcomment",((Post)adapter.getData().get(position)).getSum_comment_num());
                startActivity(intent);
            }
        });

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
