package com.bdkj.wirecrimping.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.adapter.CommonAdapter;
import com.bdkj.wirecrimping.adapter.OnItemClickListener;
import com.bdkj.wirecrimping.adapter.ViewHolder;
import com.bdkj.wirecrimping.bean.StandardValuesTwoBean;
import com.bdkj.wirecrimping.dialog.AddTwoDialog;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置“耐张线”的标准值
 */
public class SettingTwoActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ib_back;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.ll_menu)
    LinearLayout ll_menu;
    @BindView(R.id.rv_recycleView)
    RecyclerView rv_recycleView;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    private CustomPopWindow popWindow;
    public static final String FILENAME = "HardwareFile";

    private List<StandardValuesTwoBean.DataBean> dataBeanList = new ArrayList<>();
    private CommonAdapter<StandardValuesTwoBean.DataBean> commonAdapter;
    private StandardValuesTwoBean.DataBean dataBean = new StandardValuesTwoBean.DataBean();
    private Context mContext;
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
        return R.layout.activity_setting_two;
    }

    @Override
    protected void init() {
        mContext = this;
        EventBus.getDefault().register(this);
        initView();

    }

    private void initView() {
        if (SpUtils.getInstance(mContext).getString(Constant.STANDARDVALUESTWO) != null && !"".equals(SpUtils.getInstance(mContext).getString(Constant.STANDARDVALUESTWO)))
            dataBeanList.addAll(JsonUtil.stringToList(SpUtils.getInstance(mContext).getString(Constant.STANDARDVALUESTWO), StandardValuesTwoBean.DataBean.class));
        rv_recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        commonAdapter = new CommonAdapter<StandardValuesTwoBean.DataBean>(mContext, R.layout.item_add_standard_values_two, dataBeanList) {
            @Override
            public void convert(ViewHolder holder, StandardValuesTwoBean.DataBean dataBean) {
                holder.setText(R.id.tv_serialNumber, String.valueOf(dataBean.getSerialNumber()));
                holder.setText(R.id.tv_model, dataBean.getModel());
                holder.setText(R.id.tv_applyWire, dataBean.getApplyWire());
                holder.setText(R.id.tv_steel_D_big, String.valueOf(dataBean.getSteel_D_big()));
                holder.setText(R.id.tv_steel_D_min, String.valueOf(dataBean.getSteel_D_min()));
                holder.setText(R.id.tv_steel_d_big, String.valueOf(dataBean.getSteel_d_big()));
                holder.setText(R.id.tv_steel_d_min, String.valueOf(dataBean.getSteel_d_min()));
                holder.setText(R.id.tv_steel_pressure_after, String.valueOf(dataBean.getSteel_pressure_after()));
                holder.setText(R.id.tv_steel_L, String.valueOf(dataBean.getSteel_L()));
                holder.setText(R.id.tv_aluminum_D, String.valueOf(dataBean.getAluminum_D()));
                holder.setText(R.id.tv_aluminum_d, String.valueOf(dataBean.getAluminum_d()));
                holder.setText(R.id.tv_aluminum_pressure_after, String.valueOf(dataBean.getAluminum_pressure_after()));
                holder.setText(R.id.tv_aluminum_L, String.valueOf(dataBean.getAluminum_L()));
                holder.setText(R.id.tv_steel_diameter, String.valueOf(dataBean.getSteel_diameter()));
                holder.setText(R.id.tv_pressureDistrict, String.valueOf(dataBean.getPressureDistrict()));
            }
        };
        rv_recycleView.setAdapter(commonAdapter);
        commonAdapter.notifyDataSetChanged();
        commonAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                AddTwoDialog addDialog = new AddTwoDialog(mContext, R.style.MyDialog, dataBeanList.get(position));
                addDialog.show();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(SettingTwoActivity.this)
                        .setTitle("是否删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int k = 0; k < dataBeanList.size(); k++) {
                                    if (position == k) {
                                        dataBeanList.remove(dataBeanList.get(position));
                                        commonAdapter.notifyItemRemoved(position);
                                    }
                                }
                                String standardStr = JsonUtil.objectToString(dataBeanList);
                                SpUtils.getInstance(context).savaString(Constant.STANDARDVALUESTWO, standardStr);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Subscribe
    public void refreshDatas(String message) {
        if ("耐张数据".equals(message)) {
            dataBeanList.clear();
            if (SpUtils.getInstance(mContext).getString(Constant.STANDARDVALUESTWO) != null && !"".equals(SpUtils.getInstance(mContext).getString(Constant.STANDARDVALUESTWO)))
                dataBeanList.addAll(JsonUtil.stringToList(SpUtils.getInstance(mContext).getString(Constant.STANDARDVALUESTWO), StandardValuesTwoBean.DataBean.class));
            commonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @OnClick({R.id.ib_back, R.id.tv_add, R.id.ll_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add:
                AddTwoDialog addDialog = new AddTwoDialog(context, R.style.MyDialog, null);
                addDialog.show();
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
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILENAME;
        File dir = new File(path);
        //file:///storage/emulated/0/HardwareFile
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(dir), "file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivity(intent);
    }
}
