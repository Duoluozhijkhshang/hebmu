package com.i_am_kern.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.i_am_kern.Class.Comment;
import com.i_am_kern.Class.Imageiofo;
import com.i_am_kern.Class.Myuser;
import com.i_am_kern.Class.Post;
import com.i_am_kern.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Post_DetailActivity extends AppCompatActivity {
    private TextView toolbartitle;
    private TextView postdetailtitle,postdetailtype,postdetaillikes,postdetailcommentsum;
    private ImageView postdetailicon;
    private String postObjectId;
    private RecyclerView recyclerView;
    private Post currentpost;
    private boolean loadedok,refreshok;
    private SmartRefreshLayout smartRefreshLayout;
    private Myadpter myadpter;
    private Button commitcomment;
    private ImageView likeicon;
    private int sumcomment;
    private int hasloaded;
    private int[][] subButtonColors = {{R.color.bottom_bg,R.color.bule},{R.color.bottom_bg,R.color.bule}};
    private BoomMenuButton boomMenuButton;
    private List<Imageiofo> imageiofoList;
    private int[] touxiangs = {R.drawable.cat_1,R.drawable.cat_2,R.drawable.cat_3,R.drawable.cat_4,R.drawable.cat_5,R.drawable.cat_6,R.drawable.cat_7,R.drawable.cat_8,R.drawable.cat_9,R.drawable.cat_10,R.drawable.cat_11,R.drawable.cat_12,R.drawable.cat_13,R.drawable.cat_14,R.drawable.cat_15,R.drawable.cat_16,};
    private List<Comment> commentList;
    private String[] names = {"综合热帖", "趣事吐槽","精彩活动", "失物招领", "表白部落", "跳蚤市场", "有待开发", "JELLY BEAN", "KITKAT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__detail);
        tongzhilan();
        toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText("帖子详情");
        Intent intent = getIntent();
        if (intent==null){
            finish();
        }else {
            postObjectId = intent.getStringExtra("ObjectId");
            sumcomment = intent.getIntExtra("sumcomment",0);
        }
        findId();
        recyclerView = findViewById(R.id.postdetail_RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadpter = new Myadpter(R.layout.postdetail_comment_item,null);
        recyclerView.setAdapter(myadpter);
        recyclerView.setNestedScrollingEnabled(false);
        myadpter.bindToRecyclerView(recyclerView);
        myadpter.setEmptyView(R.layout.emptyview);
        myadpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.postdetail_item_touxiang:
                        Intent intent1 = new Intent(Post_DetailActivity.this,PersonalinfoActivity.class);
                        intent1.putExtra("type",2);
                        intent1.putExtra("userObjectId",((Comment)adapter.getData().get(position)).getUser().getObjectId());
                        startActivity(intent1);

                        break;
                    default:

                        break;
                }
            }
        });
        intidata();

        ninegridviewadpter();
    }

    private void ninegridviewadpter() {

    }
    private class NineAdapter extends NineGridViewAdapter {
        public NineAdapter(Context context, List<ImageInfo> imageInfo) {
            super(context, imageInfo);
        }
    }

    private void onrefresh() {
        BmobQuery<Myuser> query1 = new BmobQuery<Myuser>();
        Post post = new Post();
        post.setObjectId(postObjectId);
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query1.addWhereRelatedTo("likes", new BmobPointer(post));
        query1.findObjects(new FindListener<Myuser>() {
            @Override
            public void done(List<Myuser> list, BmobException e) {
                if(e==null){
                    Myuser myuser = BmobUser.getCurrentUser(Myuser.class);
                    if (list.contains(myuser)){
                        likeicon.setImageResource(R.drawable.btn_comment_praise_blue);
                    }
                    Log.i("bmob","查询个数："+list.size());
                    postdetaillikes.setText(list.size()+"个人觉得很赞");

                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
        BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
        bmobQuery.addWhereEqualTo("post",postObjectId);
        bmobQuery.include("user");
        bmobQuery.setLimit(10);
        bmobQuery.order("createdAt");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                myadpter.setNewData(null);
                hasloaded = 0;
                Log.e(">>>>","评论刷新");
                smartRefreshLayout.finishRefresh();
                if (e==null){
                    if (list!=null){
                        hasloaded+=list.size();
                        commentList = list;
                        myadpter.addData(list);
                        myadpter.notifyDataSetChanged();

                    }else {

                    }


                }else {
                    Log.e("加载评论失败",e+"");
                }
            }
        });
    }

    private void intidata() {
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.getObject(postObjectId, new QueryListener<Post>() {

            @Override
            public void done(Post post, BmobException e) {
                if(e==null){
                    currentpost = post;
                    postdetailcommentsum.setText(String.valueOf(sumcomment));
                    postdetailtitle.setText(post.getTitle());
                    postdetailtype.setText(names[post.getType()]);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });

        BmobQuery<Myuser> query1 = new BmobQuery<Myuser>();
        Post post = new Post();
        post.setObjectId(postObjectId);
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query1.addWhereRelatedTo("likes", new BmobPointer(post));
        query1.findObjects(new FindListener<Myuser>() {
            @Override
            public void done(List<Myuser> list, BmobException e) {
                if(e==null){
                    Myuser myuser = BmobUser.getCurrentUser(Myuser.class);
                    if (list.contains(myuser)){
                        likeicon.setImageResource(R.drawable.btn_comment_praise_blue);
                    }
                    Log.i("bmob","查询个数："+list.size());
                    postdetaillikes.setText(list.size()+"个人觉得很赞");

                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
        BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
        bmobQuery.addWhereEqualTo("post",postObjectId);
        bmobQuery.include("user");
        bmobQuery.setLimit(10);
        bmobQuery.order("createdAt");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                myadpter.setNewData(null);
                if (e==null){
                    if (list!=null){
                        hasloaded+=list.size();
                        commentList = list;
                        myadpter.setNewData(list);
                        myadpter.notifyDataSetChanged();
                    }else {

                    }


                }else {
                    Log.e("加载评论失败",e+"");
                }
            }
        });

    }

    private void loadmore(){

        BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
        bmobQuery.addWhereEqualTo("post",postObjectId);
        bmobQuery.include("user");
        bmobQuery.setSkip(hasloaded);
        bmobQuery.order("createdAt");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                smartRefreshLayout.finishLoadMore();
                if (e==null){
                    if (!list.isEmpty()){
                        hasloaded+=list.size();
                        commentList = list;
                        myadpter.addData(list);
                        myadpter.notifyDataSetChanged();

                    }else {

                    }


                }else {
                    Log.e("加载评论失败",e+"");
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
    private void findId() {
        commitcomment = findViewById(R.id.postdtail_commitcomment);
        likeicon = findViewById(R.id.postdetail_likeicon);
        commitcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Post_DetailActivity.this,EditPostActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("postObjectId",postObjectId);
                startActivity(intent);
                overridePendingTransition(R.anim.ap2,R.anim.ap1);
            }
        });
        likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Myuser user = BmobUser.getCurrentUser(Myuser.class);
                Post post = currentpost;
//将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                BmobRelation relation = new BmobRelation();
//将当前用户添加到多对多关联中
                relation.add(user);
//多对多关联指向`post`的`likes`字段
                post.setLikes(relation);
                post.update(postObjectId,new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            likeicon.setImageResource(R.drawable.btn_comment_praise_blue);
                            Log.i("bmob","多对多关联添加成功");
                        }else{
                            Log.i("bmob","失败："+e.getMessage());
                        }
                    }

                });
            }
        });
        smartRefreshLayout = findViewById(R.id.postdetail_refreshLayout1);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("refre","???");
                onrefresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadmore();
            }
        });
        postdetailtitle= findViewById(R.id.postdetail_post_title);
        postdetailcommentsum = findViewById(R.id.postdetail_post_commentsum);
        postdetailtype = findViewById(R.id.postdetail_post_type);
        postdetaillikes  =findViewById(R.id.postdetail_post_likes);
        postdetailicon = findViewById(R.id.postdetail_post_icon);
        
    }


    private class Myadpter extends BaseQuickAdapter<Comment,BaseViewHolder>{

        public Myadpter(int layoutResId, @Nullable List<Comment> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Comment item) {
            if (helper.getAdapterPosition()==0){

            }else {
                helper.setText(R.id.postdetail_item_luoceng,helper.getAdapterPosition()+"楼");
            }
            helper.addOnClickListener(R.id.postdetail_item_touxiang);
            Log.e(">>>",helper.getAdapterPosition()+"");
            helper.setText(R.id.postdetail_item_content,item.getContent().toString());
            helper.setImageResource(R.id.postdetail_item_sexicon,item.getUser().getSex().equals("男")?R.drawable.ic_profile_gender_male:R.drawable.ic_profile_gender_female);
            helper.setText(R.id.postdetail_item_time,item.getCreatedAt());
            helper.setText(R.id.postdetail_item_unit,item.getUser().getUnit());
            helper.setImageResource(R.id.postdetail_item_touxiang,touxiangs[item.getUser().getTouxiang()-1]);
            helper.setText(R.id.postdetail_item_user,item.getUser().getRealname());
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            List<String> list = item.getImageurls();
            if (item.getImageurls() != null) {
                for (String imageurl : list) {
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(imageurl+"!/both/360x360");
                    info.setBigImageUrl(imageurl);
                    imageInfo.add(info);
                }
            }
            NineGridView nineGridView = helper.getView(R.id.postdetail_item_ninegridview);
            nineGridView.setAdapter(new NineGridViewClickAdapter(getApplicationContext(),imageInfo) {
            });
        }
    }


    private void tongzhilan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (true) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
            Toolbar toolbar = findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }
}
