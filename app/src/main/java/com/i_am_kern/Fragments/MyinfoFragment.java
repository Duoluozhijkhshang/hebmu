package com.i_am_kern.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ToastUtils;

import com.i_am_kern.Activities.LoginActivity;
import com.i_am_kern.Activities.PersonalinfoActivity;
import com.i_am_kern.Class.Myuser;
import com.i_am_kern.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import com.i_am_kern.MainActivity.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyinfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MyinfoFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private SuperTextView personalinfo,blacklist,isstudent,setting,suggestion,about;
    private List<Integer> touxianglist;
    private Myuser myuser;

    public MyinfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myinfo, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personalinfo = view.findViewById(R.id.myinfo_personalinfo);
        blacklist = view.findViewById(R.id.myinfo_blacklist);
        isstudent = view.findViewById(R.id.myinfo_isstudent);
        setting = view.findViewById(R.id.myinfo_setting);
        suggestion = view.findViewById(R.id.myinfo_suggestion);
        about = view.findViewById(R.id.myinfo_about);
        personalinfo.setOnClickListener(this);
        blacklist.setOnClickListener(this);
        isstudent.setOnClickListener(this);
        setting.setOnClickListener(this);
        suggestion.setOnClickListener(this);
        about.setOnClickListener(this);
        initlist();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (BmobUser.getCurrentUser()!=null){
            setviews();

        }else {
            personalinfo.setLeftString("未登录");
            isstudent.setRightString("未认证");
            personalinfo.setLeftIcon(touxianglist.get(0));

        }

    }

    private void setviews() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    myuser = new Myuser();
                    myuser = BmobUser.getCurrentUser(Myuser.class);
                    Log.e(">>>","更新数据"+s);
                    JSONTokener jsonTokener = new JSONTokener(s);
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject = (JSONObject) jsonTokener.nextValue();
                        myuser.setHobbies(jsonObject.getString("hobbies"));
                        myuser.setBirthday(jsonObject.getString("birthday"));
                        myuser.setQq(jsonObject.getString("qq"));
                        myuser.setLocation(jsonObject.getString("location"));
                        myuser.setIS_STUDENT(jsonObject.getString("IS_STUDENT").equals("true")?true:false);
                        myuser.setUnit(jsonObject.getString("unit"));
                        myuser.setUsername(jsonObject.getString("username"));
                        myuser.setRealname(jsonObject.getString("realname"));
                        myuser.setTouxiang(Integer.valueOf(jsonObject.getString("touxiang")));
                        myuser.setMobilePhoneNumber(jsonObject.getString("mobilePhoneNumber"));
                        Log.e(">>>",myuser.getUsername());
                    } catch (JSONException e1) {
                        Log.e(">>>",e+"");
                        e1.printStackTrace();
                    }
                    personalinfo.setLeftIcon(touxianglist.get(myuser.getTouxiang()-1));
                    personalinfo.setLeftString(myuser.getRealname());
                    isstudent.setRightString(myuser.getIS_STUDENT()?"已认证":"未认证");
                }
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(">>>","onDestroyView");
    }

    @Override
    public void onDestroy() {
        Log.e(">>.","onDestory");
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.myinfo_personalinfo:
                if (BmobUser.getCurrentUser()==null){
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(personalinfo, personalinfo.getWidth() / 2, personalinfo.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(getContext(), new Intent(getActivity(), LoginActivity.class), compat.toBundle());

                }else {
                    Intent intent = new Intent(getContext(), PersonalinfoActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.ap2, R.anim.ap1);
                }
                break;
            case R.id.myinfo_blacklist:

                break;
            case R.id.myinfo_isstudent:

                break;
            case R.id.myinfo_setting:

                break;
            case R.id.myinfo_suggestion:

                break;
            case R.id.myinfo_about:

                break;
             default:

                 break;
        }
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
    private void initlist() {
        touxianglist = new ArrayList<>();
        touxianglist.add(R.drawable.cat_1);
        touxianglist.add(R.drawable.cat_2);
        touxianglist.add(R.drawable.cat_3);
        touxianglist.add(R.drawable.cat_4);
        touxianglist.add(R.drawable.cat_5);
        touxianglist.add(R.drawable.cat_6);
        touxianglist.add(R.drawable.cat_7);
        touxianglist.add(R.drawable.cat_8);
        touxianglist.add(R.drawable.cat_9);
        touxianglist.add(R.drawable.cat_10);
        touxianglist.add(R.drawable.cat_11);
        touxianglist.add(R.drawable.cat_12);
        touxianglist.add(R.drawable.cat_13);
        touxianglist.add(R.drawable.cat_14);
        touxianglist.add(R.drawable.cat_15);
        touxianglist.add(R.drawable.cat_16);
    }
}
