package com.i_am_kern.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.i_am_kern.Class.Post;
import com.i_am_kern.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Bmob.initialize(this,"fb249b2db3e65c53c5e7acd587e3d4e1");
        Post post = new Post();
        post.setTitle("更新标题");
        post.update("23b13816f2", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Log.e(">>>","成功");
                }else {
                    Log.e(">>>","失败"+e);
                }
            }
        });


    }
}
