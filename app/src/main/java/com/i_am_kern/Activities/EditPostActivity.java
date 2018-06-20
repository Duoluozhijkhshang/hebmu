package com.i_am_kern.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.i_am_kern.Class.Comment;
import com.i_am_kern.Class.Myuser;
import com.i_am_kern.Class.Post;
import com.i_am_kern.R;
import com.shizhefei.view.indicator.slidebar.SpringBar;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import dmax.dialog.SpotsDialog;

public class EditPostActivity extends AppCompatActivity {
    private Spinner spinner;
    private RecyclerView recyclerView;
    private String[] piclist;
    private MyAdapter myAdapter;
    private ImageView chosepic;
    private final int REQUEST_LIST_CODE = 44;
    private String TAG = "EditPostA..";
    private Boolean Ispicsshow = false;
    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private AlertDialog alertDialog;
    private EditText title,content;
    private int post_tpye=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        title = findViewById(R.id.editpost_title);
        content = findViewById(R.id.editpost_content);

        alertDialog= new SpotsDialog(this);
        alertDialog.setCancelable(false);
        piclist = null;
        layoutInflater = getLayoutInflater();
        tongzhilan();
        spinner = findViewById(R.id.editpot_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                post_tpye = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        chosepic = findViewById(R.id.editpost_chosepic);
        linearLayout = findViewById(R.id.editpost_liner);
        chosepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Ispicsshow){
                    chosepic.setImageResource(R.drawable.topic_camera_icon);
                    linearLayout.setVisibility(View.GONE);
                    Ispicsshow = false;
                }else {
                    Ispicsshow = true;
                    chosepic.setImageResource(R.drawable.topic_camera_press_icon);
                    chosepic();
                    linearLayout.setVisibility(View.VISIBLE);

                }


            }
        });
        recyclerView = findViewById(R.id.editpost_chosepics_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        myAdapter = new MyAdapter(R.layout.chosen_pics_item,new ArrayList());
        recyclerView.setAdapter(myAdapter);
        myAdapter.setEmptyView(layoutInflater.inflate(R.layout.picsemptyview,null));
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.btn_cancel_action){
                    adapter.remove(position);
                    Log.e(TAG,adapter.getItemCount()+" "+adapter.getEmptyViewCount());
                    if (adapter.getItemCount()==1&&adapter.getEmptyViewCount()==1){
                        chosepic.setImageResource(R.drawable.topic_camera_icon);
                        Ispicsshow = false;
                    }
                }
            }
        });
        myAdapter.openLoadAnimation();
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

    private void chosepic() {
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(true)
                // “确定”按钮背景色
                .btnBgColor(R.color.yinse)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLACK)
                // 使用沉浸式状态栏
                .statusBarColor(Color.WHITE)
                // 返回图标ResId
                .backResId(R.drawable.hwpush_ic_toolbar_back)
                // 标题
                .title("选择图片")
                // 标题文字颜色
                .titleColor(Color.BLACK)
                // TitleBar背景色
                .titleBgColor(Color.WHITE)
                // 裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, 200, 200)
//                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();

// 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调

        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            int i= 0;
            piclist = new String[pathList.size()];
            for (String path:pathList){
                myAdapter.addData(path);
                piclist[i] = path;
                i++;
            }
            myAdapter.notifyDataSetChanged();
        }
    }

    public void upload(View view) {
        if (BmobUser.getCurrentUser()==null){
            ToastUtils.showLong("请先登录！");
            return;
        }
        alertDialog.show();
        if (title.getText().toString().equals("")||content.getText().toString().equals("")){
            ToastUtils.showLong("请填写完整");
            alertDialog.dismiss();
        }else if (post_tpye ==0){
            ToastUtils.showLong("请选择分类");
            alertDialog.dismiss();
        }else {
            if (piclist==null){
                Post post2 = new Post();
                post2.setAuthor(BmobUser.getCurrentUser(Myuser.class));
                post2.setTitle(title.getText().toString());
                post2.setContent(content.getText().toString());
                post2.save( new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            Post post1 = new Post();
                            post1.setObjectId(s);
                            Comment comment = new Comment();
                            comment.setContent(content.getText().toString());
                            comment.setPost(post1);
                            comment.setUser(BmobUser.getCurrentUser(Myuser.class));
                            comment.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        ToastUtils.showLong("发表成功！");
                                        alertDialog.dismiss();
                                        finish();
                                    }else {
                                        alertDialog.dismiss();
                                        ToastUtils.showLong("发表失败"+e);
                                    }
                                }
                            });
                        }else {
                            alertDialog.dismiss();
                            ToastUtils.showLong("帖子缩略图设置失败！"+e);
                        }
                    }

                });


                return;
            }
            Log.e(TAG,"You图");
            BmobFile.uploadBatch(piclist, new UploadBatchListener() {
                @Override
                public void onSuccess(final List<BmobFile> list, final List<String> list1) {
                    List<String> suoluetuUrls = new ArrayList<>();
                    int itemType = 0;
                    if (list1.size()==piclist.length){
                        if (list1.size()>=3){
                            suoluetuUrls.add(list1.get(0)+"!/both/360x360");
                            suoluetuUrls.add(list1.get(1)+"!/both/360x360");
                            suoluetuUrls.add(list1.get(2)+"!/both/360x360");
                            itemType =2;
                        }else if (list1.size()==2||list1.size()==1){
                            suoluetuUrls.add(list1.get(0)+"!/both/360x360");
                            itemType = 1;
                        }
                        Post post2 = new Post();
                        post2.setAuthor(BmobUser.getCurrentUser(Myuser.class));
                        post2.setTitle(title.getText().toString());
                        post2.setContent(content.getText().toString());
                        post2.setValue("suoluetuUrls",suoluetuUrls);
                        post2.setItemType(itemType);
                        post2.save( new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    ToastUtils.showLong("帖子发表成功！");
                                    Log.e(TAG,list1.size()+" >"+piclist.length);
                                    Post post1 = new Post();
                                    post1.setObjectId(s);
                                    Comment comment = new Comment();
                                    comment.setContent(content.getText().toString());
                                    comment.setImages(list);
                                    comment.setPost(post1);
                                    comment.setUser(BmobUser.getCurrentUser(Myuser.class));
                                    comment.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e==null){
//                                                            ToastUtils.showLong("发表成功！");
                                                alertDialog.dismiss();
                                                finish();
                                            }else {
                                                alertDialog.dismiss();
                                                ToastUtils.showLong("发表失败"+e);
                                            }
                                        }
                                    });
                                }else {
                                    alertDialog.dismiss();
                                    ToastUtils.showLong("帖子缩略图设置失败！"+e);
                                }
                            }

                        });


                    }

                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                    Log.e(TAG,">>"+piclist[i-1]);
                }

                @Override
                public void onError(int i, String s) {
                    alertDialog.dismiss();
                    ToastUtils.showLong("上传失败"+s);
                    Log.e(TAG,s+"???");
                }
            });

        }

    }

    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public MyAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            Glide.with(getApplicationContext()).load(item).into((ImageView) helper.getView(R.id.chosen_pic1));
            helper.addOnClickListener(R.id.btn_cancel_action);
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.hwpush_ic_toolbar_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
