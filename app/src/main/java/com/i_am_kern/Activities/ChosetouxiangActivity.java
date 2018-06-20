package com.i_am_kern.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.i_am_kern.R;

public class ChosetouxiangActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,imageView13,imageView14,imageView15,imageView16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosetouxiang);
        tongzhilan();
        imageView1 = findId(imageView1,R.id.imageView1);
        imageView2 = findId(imageView2,R.id.imageView2);
        imageView3 = findId(imageView3,R.id.imageView3);
        imageView4 = findId(imageView4,R.id.imageView4);
        imageView5 =  findId(imageView5,R.id.imageView5);
        imageView6 =  findId(imageView6,R.id.imageView6);
        imageView7 =  findId(imageView7,R.id.imageView7);
        imageView8 =  findId(imageView8,R.id.imageView8);
        imageView9 =  findId(imageView9,R.id.imageView9);
        imageView10 =  findId(imageView10,R.id.imageView10);
        imageView11 =  findId(imageView11,R.id.imageView11);
        imageView12 =   findId(imageView12,R.id.imageView12);
        imageView13 =  findId(imageView13,R.id.imageView13);
        imageView14 =   findId(imageView14,R.id.imageView14);
        imageView15 =  findId(imageView15,R.id.imageView15);
        imageView16 =  findId(imageView16,R.id.imageView16);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        imageView9.setOnClickListener(this);
        imageView10.setOnClickListener(this);
        imageView11.setOnClickListener(this);
        imageView12.setOnClickListener(this);
        imageView13.setOnClickListener(this);
        imageView14.setOnClickListener(this);
        imageView15.setOnClickListener(this);
        imageView16.setOnClickListener(this);
    }

    private ImageView findId(ImageView imageView,int Id) {
        imageView = findViewById(Id);
        return imageView;
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
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()){
            case R.id.imageView1 :
                intent.putExtra("touxiang",1);
                break;
            case R.id.imageView2 :
                intent.putExtra("touxiang",2);
                break;
            case R.id.imageView3 :
                intent.putExtra("touxiang",3);
                break;
            case R.id.imageView4 :
                intent.putExtra("touxiang",4);
                break;
            case R.id.imageView5 :
                intent.putExtra("touxiang",5);
                break;
            case R.id.imageView6 :
                intent.putExtra("touxiang",6);
                break;
            case R.id.imageView7 :
                intent.putExtra("touxiang",7);
                break;
            case R.id.imageView8 :
                intent.putExtra("touxiang",8);
                break;
            case R.id.imageView9 :
                intent.putExtra("touxiang",9);
                break;
            case R.id.imageView10 :
                intent.putExtra("touxiang",10);
                break;
            case R.id.imageView11 :
                intent.putExtra("touxiang",11);
                break;
            case R.id.imageView12 :
                intent.putExtra("touxiang",12);
                break;
            case R.id.imageView13 :
                intent.putExtra("touxiang",13);
                break;
            case R.id.imageView14 :
                intent.putExtra("touxiang",14);
                break;
            case R.id.imageView15 :
                intent.putExtra("touxiang",15);
                break;
            case R.id.imageView16 :
                intent.putExtra("touxiang",16);
                break;

            default:intent.putExtra("touxiang",1); break;
        }
        setResult(1,intent);
        finish();
    }
}
