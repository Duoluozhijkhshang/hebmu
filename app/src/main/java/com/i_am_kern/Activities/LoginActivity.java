package com.i_am_kern.Activities;

import android.content.Intent;
import android.os.Build;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.i_am_kern.Class.Myuser;
import com.i_am_kern.MainActivity;
import com.i_am_kern.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    private Button login,sigup;
    private EditText studentId,psd;
    TextView  phonesigup;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        alertDialog = new SpotsDialog(this);
        //通知栏沉浸
        tongzhilan();
        //findId
        findID();

    }

    private void findID() {
        login = findViewById(R.id.login_btn_login);
        sigup = findViewById(R.id.login_btn_singup);
        studentId = findViewById(R.id.login_edt_studentId);
        psd = findViewById(R.id.login_edt_psd);
        phonesigup =  findViewById(R.id.login_tv_phonesigup);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                Myuser myuser = new Myuser();
                myuser.setUsername(studentId.getText().toString());
                myuser.setPassword(psd.getText().toString());
                myuser.login(new SaveListener<Myuser>() {

                    @Override
                    public void done(Myuser myuser, BmobException e) {
                        if (e==null){
                            alertDialog.dismiss();
                            ToastUtils.showLong("登陆成功！");
                            finish();
                        }else {
                            ToastUtils.showLong("登录失败！请检查账号密码！"+e);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, sigUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ap2, R.anim.ap1);
                finish();
            }
        });
        phonesigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLong("内测阶段，暂不开放0_0!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.ap2, R.anim.ap1);
        finish();
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

        }
    }


}
