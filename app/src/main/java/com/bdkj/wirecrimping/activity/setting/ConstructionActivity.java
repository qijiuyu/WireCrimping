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
import com.bdkj.wirecrimping.bean.NameBean;
import com.bdkj.wirecrimping.dialog.AddConsDialog;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 施工单位列表
 */
public class ConstructionActivity extends Activity {

    @BindView(R.id.rv_recycleView)
    RecyclerView rvRecycleView;
    private Context context;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction);
        context=this;
        ButterKnife.bind(this);
        //显示数据
        showListData();
    }

    @OnClick({R.id.ib_back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                 finish();
                break;
            case R.id.tv_add:
                AddConsDialog addDialog = new AddConsDialog(context, R.style.MyDialog,null);
                addDialog.show();
                break;
            default:
                break;
        }
    }


    /**
     * 显示数据
     */
    public void showListData(){
        //获取保存的所有数据
        final String CONSTRUCTION= SPUtil.getInstance(this).getString(Constant.CONSTRUCTION);
        if (!TextUtils.isEmpty(CONSTRUCTION)) {
            List<NameBean> dataBeanList= JsonUtil.stringToList(CONSTRUCTION, NameBean.class);
            rvRecycleView.setLayoutManager(new LinearLayoutManager(this));
            BaseAdapter baseAdapter = new BaseAdapter<NameBean>(context, R.layout.item_name_value, dataBeanList) {
                public void convert(ViewHolder holder, NameBean nameBean) {
                    holder.setText(R.id.tv_serialNumber, String.valueOf(holder.getPosition()+1));
                    holder.setText(R.id.tv_name, String.valueOf(nameBean.getName()));
                }
            };
            rvRecycleView.setAdapter(baseAdapter);

            /**
             * 修改与删除
             */
            baseAdapter.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                    AddConsDialog addDialog = new AddConsDialog(context, R.style.MyDialog, dataBeanList.get(position));
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
                                    SPUtil.getInstance(context).addString(Constant.CONSTRUCTION, JsonUtil.objectToString(dataBeanList));
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
