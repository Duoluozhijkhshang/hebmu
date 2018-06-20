package com.i_am_kern.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.i_am_kern.Class.Myuser;
import com.i_am_kern.MainActivity;
import com.i_am_kern.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.db.CookieManager;
import com.lzy.okgo.model.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import dmax.dialog.SpotsDialog;

public class sigUpActivity extends AppCompatActivity {
    private EditText studentId,studentpsd,psd,agpsd;
    private String HIDEN_VIEWSTATE = "";
    private String HIDEN_VIEWSTATEGENERATOR = "";
    private String HIDEN_EVENTVALIDATION = "";
    private String HIDEN_EVENTTARGET = "";
    private String HIDEN_EVENTARGUMENT = "";
    private String HIDEN_VIEWSTATEENCRYPTED = "";
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_up);
        tongzhilan();
         alertDialog = new SpotsDialog(this);
        studentId = findViewById(R.id.sigUp_edt_studentId);
        studentpsd = findViewById(R.id.sigUp_edt_studentpsd);
        psd = findViewById(R.id.sigUp_edt_psd);
        agpsd = findViewById(R.id.sigUp_edt_agpsd);
    }

    public void oncomplete(View view) {
        if (!psd.getText().toString().equals(agpsd.getText().toString())){
            ToastUtils.showLong("两次密码输入不一致！");
            psd.setText("");agpsd.setText("");
            return;
        }

        alertDialog.show();
        OkGo.<String>post("http://202.206.48.114:8044/login.aspx").execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Refresh_hidens(Jsoup.parse(response.body()).select("div.aspNetHidden"));

                OkGo.<String>post("http://202.206.48.114:8044/login.aspx")
                        .params("__VIEWSTATE", HIDEN_VIEWSTATE)
                        .params("__VIEWSTATEGENERATOR", HIDEN_VIEWSTATEGENERATOR)
                        .params("__EVENTVALIDATION", HIDEN_EVENTVALIDATION)
                        .params("sort", "rbStu")
                        .params("txtUserName", studentId.getText().toString())
                        .params("txtPwd", studentpsd.getText().toString())
                        .params("btnLogin", "登录").execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        System.out.println(response.body());
                        if (response.body().contains("河北医科大学")) {
                            OkGo.<String>get(" http://202.206.48.114:8044/PersonInfo/PersonInfo.aspx").execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Myuser myuser = new Myuser();
                                    StringBuffer stringBuffer = new StringBuffer();
                                    int i = 0;
                                    Iterator it = Jsoup.parse((String) response.body()).select("td[class=left_txt2]").iterator();
                                    while (it.hasNext()) {
                                        Element element = (Element) it.next();
                                        if (i == 1) {
                                            myuser.setUsername(element.text());
                                        }
                                        if (i == 3) {
                                            myuser.setRealname(element.text());
                                        }
                                        if (i == 5) {
                                            myuser.setSex(element.text());
                                        }
                                        if (i == 7) {
                                            myuser.setUnit(element.text().substring(0,element.text().indexOf("级")+1));
                                        }
                                        i++;
                                    }
                                    if(myuser.getRealname()!=null){
                                        myuser.setPassword(psd.getText().toString());
                                        myuser.setIS_STUDENT(true);
                                        myuser.setBackupsd(psd.getText().toString());
                                        myuser.signUp(new SaveListener<Myuser>() {
                                            @Override
                                            public void done(Myuser myuser, BmobException e) {
                                                if (e==null){
                                                    alertDialog.dismiss();
                                                    ToastUtils.showLong("注册成功！可以在账户管理中继续补充个人信息！");
                                                    finish();
                                                }else {
                                                    alertDialog.dismiss();
                                                    ToastUtils.showLong("未知错误,等待修复！"+e);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }else {
                            alertDialog.dismiss();
                            ToastUtils.showLong("学生认证失败，请检查学号以及密码！");
                        }
                    }
                });
            }
        });
    }

    private void Refresh_hidens(Elements elements1){
        HIDEN_EVENTTARGET = elements1.select("input[name=__EVENTTARGET]").attr("value");
        HIDEN_EVENTARGUMENT = elements1.select("input[name=__EVENTARGUMENT]").attr("value");
        HIDEN_VIEWSTATE = elements1.select("input[name=__VIEWSTATE]").attr("value");
        HIDEN_VIEWSTATEGENERATOR = elements1.select("input[name=__VIEWSTATEGENERATOR]").attr("value");
        HIDEN_EVENTVALIDATION = elements1.select("input[name=__EVENTVALIDATION]").attr("value");
        HIDEN_VIEWSTATEENCRYPTED = elements1.select("input[name=__VIEWSTATEENCRYPTED]").attr("value");
        Log.e(" : ",HIDEN_EVENTTARGET);
        Log.e("HIDEN_EVENTARGUMENT : ",HIDEN_EVENTARGUMENT);
        Log.e("HIDEN_VIEWSTATE : ",HIDEN_VIEWSTATE);
        Log.e("HIDEN_VSTEGENERATOR : ",HIDEN_VIEWSTATEGENERATOR);
        Log.e("HIDEN_EVNTVALIDATION : ",HIDEN_EVENTVALIDATION);
        Log.e("HIDEN_VISTEENCRYPTED : ",HIDEN_VIEWSTATEENCRYPTED);
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
