package com.bdkj.wirecrimping.activity.setting;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.adapter.BaseAdapter;
import com.bdkj.wirecrimping.adapter.OnItemClickListener;
import com.bdkj.wirecrimping.adapter.ViewHolder;
import com.bdkj.wirecrimping.bean.StandardValuesBean;
import com.bdkj.wirecrimping.dialog.AddStraightLineDialog;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 直线线夹数据列表
 */
public class StraightLineActivity extends Activity {

    @BindView(R.id.rv_recycleView)
    RecyclerView rvRecycleView;
    private Context context;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straight_line);
        context=this;
        ButterKnife.bind(this);
        //显示直线列表数据
        showListData();
    }

    @OnClick({R.id.ib_back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            //新增
            case R.id.tv_add:
                AddStraightLineDialog addDialog = new AddStraightLineDialog(this, R.style.MyDialog, null);
                addDialog.show();
                break;
            default:
                break;
        }
    }


    /**
     * 显示直线列表数据
     */
    public void showListData(){
        //获取保存的所有数据
        final String STRAIGHT_LINE= SPUtil.getInstance(this).getString(Constant.STANDARDVALUES);
        if (!TextUtils.isEmpty(STRAIGHT_LINE)) {
            List<StandardValuesBean.DataBean> dataBeanList= JsonUtil.stringToList(STRAIGHT_LINE, StandardValuesBean.DataBean.class);
            rvRecycleView.setLayoutManager(new LinearLayoutManager(this));
            BaseAdapter baseAdapter = new BaseAdapter<StandardValuesBean.DataBean>(this, R.layout.item_add_standard_values, dataBeanList) {
                public void convert(ViewHolder holder, StandardValuesBean.DataBean dataBean) {
                    holder.setText(R.id.tv_serialNumber, String.valueOf(holder.getPosition()+1));
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

                }
            };
            rvRecycleView.setAdapter(baseAdapter);

            /**
             * 修改与删除
             */
            baseAdapter.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                    AddStraightLineDialog addDialog = new AddStraightLineDialog(context, R.style.MyDialog, dataBeanList.get(position));
                    addDialog.show();
                }

                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("是否删除？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dataBeanList.remove(position);
                                    baseAdapter.notifyDataSetChanged();
                                    //更新存储的数据
                                    SPUtil.getInstance(context).addString(Constant.STANDARDVALUES, JsonUtil.objectToString(dataBeanList));
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    alertDialog.show();
                    return false;
                }
            });
        }
    }
}
