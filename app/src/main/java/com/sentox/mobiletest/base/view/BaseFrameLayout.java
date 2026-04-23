package com.sentox.mobiletest.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.sentox.mobiletest.base.activity.ViewBindingUtil;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFrameLayout<VB extends ViewBinding> extends FrameLayout implements DefaultLifecycleObserver {
    private boolean mViewCreatedCalled = false;
    @Nullable
    private VB mViewBinding;

    public BaseFrameLayout(Context context) {
        this(context, null);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs, defStyle, defStyleRes);
        init();
    }

    private void init() {
        mViewBinding = ViewBindingUtil.inflateWithView(this, LayoutInflater.from(getContext()), this);
        findView();
    }

    @CallSuper
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mViewCreatedCalled) {
            onLazyLoad();
            mViewCreatedCalled = true;
        }
    }

    /**
     * 重写该方法可实现懒加载数据，即当前控件第一次可见时才会执行一次，不同于在构造函数里面使用预加载机制直接加载数据的方式
     */
    @CallSuper
    protected void onLazyLoad() {
        setView();
        setListener();
    }

    @Nullable
    protected final VB getBinding() {
        return mViewBinding;
    }

    /**
     * 来自于Activity或Fragment的onCreate()方法被回调
     *
     * @param owner 生命周期提供者，一般是Activity或Fragment
     */
    @CallSuper
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    /**
     * 来自于Activity或Fragment的onStart()方法被回调
     *
     * @param owner 生命周期提供者，一般是Activity或Fragment
     */
    @CallSuper
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    /**
     * 来自于Activity或Fragment的onResume()方法被回调
     *
     * @param owner 生命周期提供者，一般是Activity或Fragment
     */
    @CallSuper
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    /**
     * 来自于Activity或Fragment的onPause()方法被回调
     *
     * @param owner 生命周期提供者，一般是Activity或Fragment
     */
    @CallSuper
    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    /**
     * 来自于Activity或Fragment的onStop()方法被回调
     *
     * @param owner 生命周期提供者，一般是Activity或Fragment
     */
    @CallSuper
    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }


    ////////////////////////////////////////以下是提供给子类复写的方法////////////////////////////////////////

    /**
     * 该方法是在构造函数里执行，可用于处理控件的初始化
     */
    protected void findView() {

    }

    /**
     * 该方法是在onLazyLoad()方法里执行，可用于处理控件的加载数据
     */
    protected void setView() {

    }

    /**
     * 该方法是在onLazyLoad()方法里执行，可用于处理控件的设置监听器
     */
    protected void setListener() {

    }

}