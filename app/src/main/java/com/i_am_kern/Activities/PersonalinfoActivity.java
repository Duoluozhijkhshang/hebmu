package com.i_am_kern.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonObject;
import com.i_am_kern.Class.Myuser;
import com.i_am_kern.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalinfoActivity extends AppCompatActivity implements View.OnClickListener{
    private SuperTextView basicinfo,location,hobbies,unit,birthday,qq,phonenum,logout;
    private List<Integer> touxianglist;
    LayoutInflater layoutInflater;
    private AlertDialog alertDialog;
    private String content;
    private String userObjectId;
    private Myuser currentuser;
    private int type;
    Myuser myuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        Intent intent = getIntent();
        myuser = BmobUser.getCurrentUser(Myuser.class);
        Log.e(">>>",myuser.getObjectId());
        if (intent!=null){
            type =intent.getIntExtra("type",1);
            userObjectId = intent.getStringExtra("userObjectId");
            if (userObjectId!=null){
                if (userObjectId.equals(BmobUser.getCurrentUser(Myuser.class).getObjectId())){
                    type = 1;
                    myuser = BmobUser.getCurrentUser(Myuser.class);
                    Log.e(">>>",myuser.getObjectId());
            }

            }
        }


        tongzhilan();

        TextView textView = findViewById(R.id.toolbar_title);
        if (type==2){
            textView.setText("个人信息");
        }else {
            textView.setText("账户管理");
        }


        findId();
        initlist();
        setviews();

    }

    private void setviews() {
        myuser = new Myuser();
        if (type ==2){
            BmobQuery<Myuser> myuserBmobQuery = new BmobQuery<Myuser>();
            myuserBmobQuery.getObject(userObjectId, new QueryListener<Myuser>() {
                @Override
                public void done(Myuser cmyuser, BmobException e) {
                    myuser =cmyuser;
                    basicinfo.setLeftTopString(myuser.getRealname());
                    basicinfo.setLeftIcon(touxianglist.get(myuser.getTouxiang()-1));
                    basicinfo.setRightString(myuser.getIS_STUDENT()?"已学生认证":"校外用户");
                    basicinfo.setLeftBottomString(myuser.getUsername());
                    location.setRightString(myuser.getLocation());
                    hobbies.setRightString(myuser.getHobbies());
                    unit.setRightString(myuser.getUnit());
                    birthday.setRightString(myuser.getBirthday());
                    qq.setRightString(myuser.getQq());
                    phonenum.setRightString(myuser.getMobilePhoneNumber());
                }
            });



            return;
        }

        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
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
                        Log.e(">>>",myuser.getTouxiang()+"  "+Integer.valueOf(jsonObject.getString("touxiang"))+" "+jsonObject.get("touxiang"));
                    } catch (JSONException e1) {
                        Log.e(">>>",e+"");
                        e1.printStackTrace();
                    }
                    basicinfo.setLeftTopString(myuser.getRealname());
                    basicinfo.setLeftIcon(touxianglist.get(myuser.getTouxiang()-1));
                    basicinfo.setRightString(myuser.getIS_STUDENT()?"已学生认证":"校外用户");
                    basicinfo.setLeftBottomString(myuser.getUsername());
                    location.setRightString(myuser.getLocation());
                    hobbies.setRightString(myuser.getHobbies());
                    unit.setRightString(myuser.getUnit());
                    birthday.setRightString(myuser.getBirthday());
                    qq.setRightString(myuser.getQq());
                    phonenum.setRightString(myuser.getMobilePhoneNumber());
                }
            }
        });

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

    private void findId() {
        basicinfo = findViewById(R.id.personal_basicinfo);
        location = findViewById(R.id.personal_location);
        hobbies = findViewById(R.id.personal_hobbies);
        unit = findViewById(R.id.personal_unit);
        birthday = findViewById(R.id.personal_birthday);
        qq = findViewById(R.id.personal_qq);
        phonenum  =findViewById(R.id.personal_phonenum);
        logout = findViewById(R.id.personal_logout);
        basicinfo.setOnClickListener(this);
        location.setOnClickListener(this);
        hobbies.setOnClickListener(this);
        unit.setOnClickListener(this);
        qq.setOnClickListener(this);
        phonenum.setOnClickListener(this);
        birthday.setOnClickListener(this);
        if (type==2){
            logout.setCenterString("加为好友");
        }
        logout.setOnClickListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==1&&data!=null){
            myuser.setTouxiang(data.getIntExtra("touxiang",1));
            myuser.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ToastUtils.showLong("头像更换成功");
                        basicinfo.setLeftIcon(touxianglist.get(myuser.getTouxiang()-1));
                    }else {
                        ToastUtils.showLong("头像更换失败"+e);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        myuser = BmobUser.getCurrentUser(Myuser.class);
        switch (view.getId()){
            case R.id.personal_basicinfo:
                startActivityForResult(new Intent(PersonalinfoActivity.this,ChosetouxiangActivity.class),1);
                //overridePendingTransition(R.anim.ap2,R.anim.ap1);
                break;
            case R.id.personal_location:
                showedtdiag("修改地址");
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (content.equals("123")){
                            return;
                        }


                        myuser.setLocation(content);
                        myuser.update(myuser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    location.setRightString(content);
                                }else{
                                    ToastUtils.showLong("更新用户信息失败:" + e.getMessage());
                                }
                            }
                        });
                    }
                });

                break;
            case R.id.personal_hobbies:
                showedtdiag("填写爱好");
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (content.equals("123")){
                            return;
                        }

                        myuser.setHobbies(content);
                        myuser.update(myuser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    hobbies.setRightString(content);
                                }else{
                                    ToastUtils.showLong("更新用户信息失败:" + e.getMessage());
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.personal_unit:
               ToastUtils.showLong("单位是自动获取的0_0！");
                break;
            case R.id.personal_birthday:
                showedtdiag("生日");
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (content.equals("123")){
                            return;
                        }


                        myuser.setBirthday(content);
                        myuser.update(myuser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    birthday.setRightString(content);
                                }else{
                                    ToastUtils.showLong("更新用户信息失败:" + e.getMessage());
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.personal_qq:
                showedtdiag("QQ");
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (content.equals("123")){
                            return;
                        }

                        myuser.setQq(content);
                        myuser.update(myuser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    qq.setRightString(content);
                                }else{
                                    ToastUtils.showLong("更新用户信息失败:" + e.getMessage());
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.personal_phonenum:
                showedtdiag("手机号");
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (content.equals("123")){
                            return;
                        }else if (content.length()!=11){
                            ToastUtils.showLong("请输入11位有效手机号码-_-!");
                            return;
                        }

                        myuser.setMobilePhoneNumber(content);
                        myuser.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    phonenum.setRightString(content);
                                }else{
                                    ToastUtils.showLong("手机号已被注册！");
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.personal_logout:
                BmobUser.logOut();
                finish();
                break;
            default: break;
        }
    }
    private String showedtdiag(String title){
        layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.edit_text,null);

         final EditText et=view.findViewById(R.id.editText);
        alertDialog = new AlertDialog.Builder(this).setView(view).setTitle(title).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                content = "123";
                dialogInterface.cancel();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (et.getText().toString().equals("")){
                    ToastUtils.showLong("不能修改为空！");
                    return;
                }
                content = et.getText().toString();
                dialogInterface.cancel();
            }
        }).create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(setDialogBack(16, 16, 16, 16, 200, 238, 238, 238));
        alertDialog.show();
        return content;
    }
    /**
     *
     * @author
     * @Description: 圆角布局
     * @param cTopLeft
     *            布局左上角 圆角半径
     * @param cTopRight
     *            布局右上角 圆角半径
     * @param cBottomLeft
     *            布局左下角 圆角半径
     * @param cBottomRight
     *            布局右下角 圆角半径
     * @param a 背景颜色透明度
     * @param r RGB颜色值中的R值，可用16进制表示
     * @param g RGB颜色值中的G值，可用16进制表示
     * @param b RGB颜色值中的B值，可用16进制表示
     */
    public Drawable setDialogBack(float cTopLeft, float cTopRight, float cBottomLeft,
                                  float cBottomRight, int a, int r, int g, int b) {
        float outRectr[] = new float[] { cTopLeft, cTopLeft, cTopRight, cTopRight, cBottomRight, cBottomRight, cBottomLeft, cBottomLeft };
        RoundRectShape rectShape = new RoundRectShape(outRectr, null, null);
        ShapeDrawable normalDrawable = new ShapeDrawable(rectShape);
        normalDrawable.getPaint().setColor(Color.argb(a, r, g, b));
        return normalDrawable;
    }
}
