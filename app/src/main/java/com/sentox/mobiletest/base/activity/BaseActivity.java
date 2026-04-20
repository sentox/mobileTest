package com.sentox.mobiletest.base.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sentox.mobiletest.base.log.L;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewbinding.ViewBinding;

/**
 * 描述：Activity基类
 * 说明：
 * Created by Sentox
 * Created on 2022/9/14
 */
public class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding = null;
    public boolean mIsShowLifeCycle = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        binding = ViewBindingUtil.inflateWithActivity(this, getLayoutInflater());
        if (binding != null) {
            View mContentView = binding.getRoot();
//            mContentView.setTag(BaseViewTag.TAG_NAME, this);
            setContentView(mContentView);
        }
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onCreate");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onNewIntent");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onStart");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onResume");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onPause");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onStop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsShowLifeCycle) {
            L.info(BaseActivity.this.getClass().getSimpleName(), "onDestroy");
        }
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            L.info(BaseActivity.this.getClass().getSimpleName(), "onCreate");
        } catch (Exception e) {
            finish();
        }
    }

    public VB getBinding(){
        return binding;
    }

    //****************************************设置沉浸式状态栏*********************************************//

    private void setStatusBar(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            setStatusBarUpperAPI21();
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            setStatusBarUpperAPI19();
        }
    }

    // 5.0版本以上
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarUpperAPI21() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    // 4.4 - 5.0版本
    private void setStatusBarUpperAPI19() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        //移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null &&
                statusBarView.getLayoutParams().height == getStatusBarHeight()) {
            mContentView.removeView(statusBarView);
        }
        //不预留空间
        if (mContentView.getChildAt(0) != null) {
            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
        }
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height","dimen","android");
        if(resId>0){
            result = getResources().getDimensionPixelSize(resId);
        }
        return result;
    }
}
