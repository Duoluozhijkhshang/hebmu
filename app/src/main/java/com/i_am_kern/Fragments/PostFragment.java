package com.i_am_kern.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.i_am_kern.Activities.EditPostActivity;
import com.i_am_kern.Fragments.subFragment.FindlostFragment;
import com.i_am_kern.MainActivity;
import com.i_am_kern.R;
import com.i_am_kern.Viewpager.ChildViewPager;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PostFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private String TAG = "PostFragment";
    private android.support.v7.widget.Toolbar toolbar;
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private String[] names = {"综合热帖", "趣事吐槽","精彩活动", "失物招领", "表白部落", "跳蚤市场", "有待开发", "JELLY BEAN", "KITKAT"};
    private ScrollIndicatorView scrollIndicatorView;
    private int unSelectTextColor;
    private List<android.support.v4.app.Fragment> fragmentList;
    private ImageView imageView;
    private FloatingActionButton fab;


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG,"onViewCreated");
        //findid
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditPostActivity.class));
                getActivity().overridePendingTransition(R.anim.ap2,R.anim.ap1);
            }
        });
        //加入这个fragment的toolbar
        toolbar = getView().findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //加入数据
        fragmentList = new ArrayList<>();
        fragmentList.add(FindlostFragment.newInstance(0));
        fragmentList.add(FindlostFragment.newInstance(1));
        fragmentList.add(FindlostFragment.newInstance(2));
        fragmentList.add(FindlostFragment.newInstance(3));
        fragmentList.add(FindlostFragment.newInstance(4));
        fragmentList.add(FindlostFragment.newInstance(5));
        fragmentList.add(FindlostFragment.newInstance(6));
        //复制粘贴处
        ViewPager viewPager = (ViewPager) getView(). findViewById(R.id.moretab_viewPager);
        scrollIndicatorView = (ScrollIndicatorView) getView().findViewById(R.id.moretab_indicator);
        scrollIndicatorView.setBackgroundColor(Color.WHITE);
        scrollIndicatorView.setScrollBar(new DrawableBar(getContext(), R.drawable.round_border_white_selector, ScrollBar.Gravity.CENTENT_BACKGROUND) {
            @Override
            public int getHeight(int tabHeight) {
                return tabHeight - dipToPix(12);
            }

            @Override
            public int getWidth(int tabWidth) {
                return tabWidth - dipToPix(12);
            }
        });
        unSelectTextColor = Color.GRAY;
        // 设置滚动监听
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, unSelectTextColor));


        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        inflate = LayoutInflater.from(getContext());
        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(fragmentList.size()-1);
        indicatorViewPager.setAdapter(new PostFragment.MyAdapter(getFragmentManager(),fragmentList));
        scrollIndicatorView.setPinnedTabView(true);
        scrollIndicatorView.setPinnedTabBgColor(Color.WHITE);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private List<android.support.v4.app.Fragment> mList;
        public MyAdapter(FragmentManager fragmentManager, List<android.support.v4.app.Fragment> fragmentListragmentList) {

            super(fragmentManager);
            this.mList = fragmentListragmentList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position % names.length]);
            int padding = dipToPix(10);
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public android.support.v4.app.Fragment getFragmentForPage(int position) {
            return mList.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

    }

    /**
     * 根据dip值转化成px值
     *
     * @param dip
     * @return
     */
    private int dipToPix(float dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        return size;
    }
}
