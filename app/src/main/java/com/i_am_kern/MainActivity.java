package com.i_am_kern;

import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;


import com.i_am_kern.Adapter.TabFragmentPagerAdapter;
import com.i_am_kern.Fragments.FindFragment;

import com.i_am_kern.Fragments.FriendsFragment;
import com.i_am_kern.Fragments.MyinfoFragment;
import com.i_am_kern.Fragments.PostFragment;
import com.i_am_kern.Fragments.subFragment.FindlostFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.cookie.CookieJarImpl;
//import com.lzy.okgo.cookie.store.MemoryCookieStore;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.cookie.CookieJarImpl;
//import com.lzy.okgo.cookie.store.MemoryCookieStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements PostFragment.OnFragmentInteractionListener,FindFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,MyinfoFragment.OnFragmentInteractionListener{
    private ViewPager mViewPager;
    private List<android.support.v4.app.Fragment> fragmentList;
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Bmob

        Bmob.initialize(this,"fb249b2db3e65c53c5e7acd587e3d4e1");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(60000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(60000, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        OkGo.getInstance().init(this.getApplication()).setOkHttpClient(builder.build()).setCacheTime(-1).setRetryCount(3);
        //通知栏沉浸
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
            //取消标题



            //绑定控件
            PageNavigationView tab = (PageNavigationView) findViewById(R.id.tab);
            mViewPager = findViewById(R.id.main_vierpager);
            mViewPager.setOffscreenPageLimit(4);
            //初始化tab
            NavigationController navigationController = tab.material()
                    .addItem(R.drawable.ic_post, "论坛")
                    .addItem(R.drawable.ic_finding, "发现")
                    .addItem(R.drawable.ic_my_friends, "好友")
                    .addItem(R.drawable.ic_person, "我的")
                    .build();
            //初始化Fragment
            fragmentList = new ArrayList<>();
            fragmentList.add(new PostFragment());
            fragmentList.add(new FindFragment());
            fragmentList.add(new FriendsFragment());
            fragmentList.add(new MyinfoFragment());
            tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
            mViewPager.setAdapter(tabFragmentPagerAdapter);
            mViewPager.setCurrentItem(0);
            navigationController.setupWithViewPager(mViewPager);

        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
