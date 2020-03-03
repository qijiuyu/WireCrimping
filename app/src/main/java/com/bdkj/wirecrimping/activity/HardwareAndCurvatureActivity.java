package com.bdkj.wirecrimping.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.activity.curvature.CurvatureActivity;
import com.bdkj.wirecrimping.activity.hardware.HardwareActivity;
import com.bdkj.wirecrimping.util.OpenFileUtils;
import com.bdkj.wirecrimping.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class HardwareAndCurvatureActivity extends BaseActivity {
    @BindView(R.id.ib_back)
    ImageButton ib_back;
    @BindView(R.id.tv_hardware)
    TextView tv_hardware;
    @BindView(R.id.v_hardware_line)
    View v_hardware_line;
    @BindView(R.id.tv_curvature)
    TextView tv_curvature;
    @BindView(R.id.v_curvature_line)
    View v_curvature_line;
    @BindView(R.id.rl_straight)
    RelativeLayout rl_straight;
    @BindView(R.id.rl_tension)
    RelativeLayout rl_tension;
    @BindView(R.id.rl_lines_three)
    RelativeLayout rl_lines_three;
    @BindView(R.id.rl_lines_two)
    RelativeLayout rl_lines_two;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.ll_menu)
    LinearLayout ll_menu;
    private CustomPopWindow popWindow;
    public static final int REQUEST_CHOOSEFILE = 0X01;
    private String sign;
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
        return R.layout.activity_hardware_curvature;
    }

    @Override
    protected void init() {
        sign = getIntent().getStringExtra("enterSign");
        if ("1".equals(sign)) {
            tv_hardware.setTextColor(Color.parseColor("#afdcff"));
            v_hardware_line.setVisibility(View.VISIBLE);
            tv_curvature.setTextColor(Color.parseColor("#ffffff"));
            v_curvature_line.setVisibility(View.GONE);
        } else {
            tv_hardware.setTextColor(Color.parseColor("#ffffff"));
            v_hardware_line.setVisibility(View.GONE);
            tv_curvature.setTextColor(Color.parseColor("#afdcff"));
            v_curvature_line.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ib_back, R.id.tv_hardware, R.id.tv_curvature, R.id.rl_straight, R.id.rl_tension, R.id.rl_lines_three, R.id.rl_lines_two, R.id.ll_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_hardware:
                sign = "1";
                tv_hardware.setTextColor(Color.parseColor("#afdcff"));
                v_hardware_line.setVisibility(View.VISIBLE);
                tv_curvature.setTextColor(Color.parseColor("#ffffff"));
                v_curvature_line.setVisibility(View.GONE);
                break;
            case R.id.tv_curvature:
                sign = "2";
                tv_hardware.setTextColor(Color.parseColor("#ffffff"));
                v_hardware_line.setVisibility(View.GONE);
                tv_curvature.setTextColor(Color.parseColor("#afdcff"));
                v_curvature_line.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_straight:
                if ("1".equals(sign)) {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, HardwareActivity.class).putExtra("sign", "1"));
                } else {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, CurvatureActivity.class).putExtra("sign", "1"));
                }

                break;
            case R.id.rl_tension:
                if ("1".equals(sign)) {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, HardwareActivity.class).putExtra("sign", "2"));
                } else {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, CurvatureActivity.class).putExtra("sign", "2"));
                }
                break;
            case R.id.rl_lines_three:
                if ("1".equals(sign)) {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, HardwareActivity.class).putExtra("sign", "3"));
                } else {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, CurvatureActivity.class).putExtra("sign", "3"));
                }
                break;
            case R.id.rl_lines_two:
                if ("1".equals(sign)) {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, HardwareActivity.class).putExtra("sign", "4"));
                } else {
                    startActivity(new Intent(HardwareAndCurvatureActivity.this, CurvatureActivity.class).putExtra("sign", "4"));
                }
                break;
            case R.id.ll_menu:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_menu_layout, null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(ll_menu, -30, -195);
                break;

        }
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
                switch (v.getId()) {
                    case R.id.tv_bending_measurement:
                        FileMannage();
                        break;
                    case R.id.tv_hardware_measurement:
                        FileMannage();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_bending_measurement).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_hardware_measurement).setOnClickListener(listener);
    }

    //打开系统文件管理器
    private void FileMannage() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.showShort("暂无SD卡");
            return;
        }
        //获取文件路径
        File dir = new File("/storage/emulated/0/弯曲度测量/线线2");
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
