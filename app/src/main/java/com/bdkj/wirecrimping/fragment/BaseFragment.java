package com.bdkj.wirecrimping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by bdkj on 2018/11/17.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Activity返回布局ID
     *
     * @return
     */
    protected abstract int getContentViewId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        bind.unbind();
    }
}
