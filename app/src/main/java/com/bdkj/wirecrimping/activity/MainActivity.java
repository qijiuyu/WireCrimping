package com.bdkj.wirecrimping.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.util.OpenFileUtils;
import com.bdkj.wirecrimping.util.PermissionUtil;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.ib_back)
    ImageButton ib_back;
    @BindView(R.id.tv_title_one)
    TextView tv_title_one;
    @BindView(R.id.tv_title_two)
    TextView tv_title_two;
    @BindView(R.id.ll_menu)
    LinearLayout ll_menu;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_foot_des)
    TextView tv_foot_des;
    @BindView(R.id.tv_exit)
    TextView tv_exit;
    private Context mContext;
    private CustomPopWindow popWindow;
    private CustomPopWindow settingPopWindow;
    public static final int REQUEST_CHOOSEFILE = 0X01;
    public static final String FILENAME = "弯曲度测量";
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(runnable, 1000);
            long sysTime = System.currentTimeMillis();//获取系统时间
            CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);//时间显示格式
            tv_date_time.setText(sysTimeStr); //更新时间
            Log.d("time", sysTimeStr + "");
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mContext = this;
        ll_setting.setVisibility(View.VISIBLE);
        tv_exit.setVisibility(View.VISIBLE);
        tv_foot_des.setVisibility(View.GONE);
        ib_back.setVisibility(View.GONE);
//        TestUtils.main();

        String token = SpUtils.getInstance(this).getString(Constant.USERNAME);
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.getPermission(this,true,true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(runnable);

    }

    @OnClick({R.id.tv_title_one, R.id.tv_title_two, R.id.ll_menu, R.id.ll_setting, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            //金具复测及对边测量
            case R.id.tv_title_one:
                startActivity(new Intent(MainActivity.this, HardwareAndCurvatureActivity.class).putExtra("enterSign", "1"));
                break;
             //弯曲度测量
            case R.id.tv_title_two:
                startActivity(new Intent(MainActivity.this, HardwareAndCurvatureActivity.class).putExtra("enterSign", "2"));
                break;
            //菜单
            case R.id.ll_menu:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_menu_layout, null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(ll_menu, -30, -195);

                break;
            case R.id.ll_setting:
                View settingContentView = LayoutInflater.from(this).inflate(R.layout.popwindow_setting_layout, null);
                //处理popWindow 显示内容
                settingHandleLogic(settingContentView);
                settingPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(settingContentView)
                        .create()
                        .showAsDropDown(ll_setting, -30, -195);
                break;
            case R.id.tv_exit:
                SpUtils.getInstance(this).savaString(Constant.USERNAME, "");
                ActivityManager.getInstance().finishAllActivity();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

        }
    }

    /**
     * 菜单处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null) {
                    popWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()) {
                    case R.id.tv_bending_measurement:
                        showContent = "点击 Item菜单1";
//                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        FileMannage();
                        break;
                    case R.id.tv_hardware_measurement:
                        showContent = "点击 Item菜单2";
//                        startActivity(new Intent(MainActivity.this, TestTwoActivity.class));
                        FileMannage();
                        break;
                }
//                Toast.makeText(MainActivity.this, showContent, Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.tv_bending_measurement).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_hardware_measurement).setOnClickListener(listener);
    }

    /**
     * 设置处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void settingHandleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settingPopWindow != null) {
                    settingPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.tv_tension_line:
                        startActivity(new Intent(MainActivity.this, SettingTwoActivity.class));
                        break;
                    case R.id.tv_straight_line:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_tension_line).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_straight_line).setOnClickListener(listener);
    }

    //打开系统文件管理器
    private void FileMannage() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.showShort("暂无SD卡");
            return;
        }

        //获取文件路径
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILENAME;
        File dir = new File("/storage/emulated/0/弯曲度测量/线线2");
        //file:///storage/emulated/0/HardwareFile
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(dir), OpenFileUtils.DATA_TYPE_EXCEL);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CHOOSEFILE);
    }


}
