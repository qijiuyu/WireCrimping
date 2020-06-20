package com.bdkj.wirecrimping.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.activity.LoginActivity;
import com.bdkj.wirecrimping.util.SPUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置页面
 */
public class SettingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_zx, R.id.tv_nz, R.id.tv_gc, R.id.tv_jl, R.id.tv_sg,R.id.tv_login_out})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            //直线线夹数据
            case R.id.tv_zx:
                intent.setClass(this,StraightLineActivity.class);
                startActivity(intent);
                break;
            //耐张线夹数据
            case R.id.tv_nz:
                intent.setClass(this,TensionActivity.class);
                startActivity(intent);
                break;
            //工程名数据
            case R.id.tv_gc:
                intent.setClass(this,ProjectNameActivity.class);
                startActivity(intent);
                break;
            //监理单位数据
            case R.id.tv_jl:
                intent.setClass(this,SupervisionActivity.class);
                startActivity(intent);
                break;
            //施工单位数据
            case R.id.tv_sg:
                intent.setClass(this,ConstructionActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_out:
                SPUtil.getInstance(this).removeMessage(Constant.ACCOUNT);
                intent.setClass(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                 break;
            default:
                break;
        }
    }
}
