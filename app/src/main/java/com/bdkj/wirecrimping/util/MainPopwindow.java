package com.bdkj.wirecrimping.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.adapter.MainPopwindowAdapter;
import com.bdkj.wirecrimping.bean.NameBean;
import com.bdkj.wirecrimping.bean.StandardValuesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/12/2.
 */

public class MainPopwindow extends PopupWindow {

    private Activity activity;
    private View view;
    private ListView listView;
    //展示及关闭动画
    private Animation animation;
    public MainPopwindow(Activity activity){
        this.activity=activity;
        initView();
    }


    private void initView(){
        view= LayoutInflater.from(activity).inflate(R.layout.popwindow_main,null);
        listView=view.findViewById(R.id.listView);
        this.setContentView(view);
        //设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置背景色
        ColorDrawable cd = new ColorDrawable(Color.argb(0, 0, 0, 0));
        this.setBackgroundDrawable(cd);
        //设置外部点击不会关闭
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    /**
     * 展开动画
     */
    public void openShow(){
        animation=new TranslateAnimation(0,0,-500,0);
        animation.setDuration(600);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }


    /**
     * 关闭动画弹框
     */
    public void closeShow(){
        animation=new TranslateAnimation(0,0,0,-500);
        animation.setDuration(600);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void setData(final EditText editText,final String message,final int type){
         List<StandardValuesBean.DataBean> list=new ArrayList<>();
         List<NameBean> list2=new ArrayList<>();
        MainPopwindowAdapter mainPopwindowAdapter=new MainPopwindowAdapter(activity);
         if(type==1){
             list= JsonUtil.stringToList(message, StandardValuesBean.DataBean.class);
             mainPopwindowAdapter.setData(list);
         }else{
             list2= JsonUtil.stringToList(message, NameBean.class);
             mainPopwindowAdapter.setData2(list2);
         }
         listView.setAdapter(mainPopwindowAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvName=view.findViewById(R.id.tv_name);
                editText.setText(tvName.getText().toString().trim());
                closeShow();
            }
        });
    }
}
