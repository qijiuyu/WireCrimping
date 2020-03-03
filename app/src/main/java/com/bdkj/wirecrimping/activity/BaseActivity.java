package com.bdkj.wirecrimping.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import butterknife.ButterKnife;


public abstract class BaseActivity extends FragmentActivity {
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        context = this;
        ActivityManager.getInstance().pushActivity(this);
        init();
    }

    /**
     * Activity返回布局ID
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * Activity初始化操作
     */
    protected abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
