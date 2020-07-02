package com.bdkj.wirecrimping.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.activity.setting.SettingActivity;
import com.bdkj.wirecrimping.util.AddDataUtil;
import com.bdkj.wirecrimping.util.SPUtil;
import com.bdkj.wirecrimping.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    /**
     * 存储账号和密码
     */
    private Map<String ,String> accountMap=new HashMap<>();

    @Override
    protected int getContentViewId() {
        final String account=SPUtil.getInstance(this).getString(Constant.ACCOUNT);
        if(!TextUtils.isEmpty(account)){
            Intent intent=new Intent();
            if(account.equals("admin")){
                intent.setClass(this,SettingActivity.class);
            }else{
                intent.setClass(this,MainActivity.class);
            }
            startActivity(intent);
            finish();
        }
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        context = this;
        accountMap.put("admin","123456");
        accountMap.put("dxyj","123456");

        new AddDataUtil().addZXdata(this);
        new AddDataUtil().addNZdata(this);
    }

    @OnClick(R.id.tv_login)
    public void login() {
        final String account=et_account.getText().toString().trim();
        final String pwd=et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if(accountMap.get(account)!=null && accountMap.get(account).equals(pwd)){
            //登录成功
            loginSuccess(account);
        }else{
            ToastUtils.showLong("用户名或密码错误");
        }
    }

    /**
     * 登录成功
     */
    private void loginSuccess(String account){
        SPUtil.getInstance(this).addString(Constant.ACCOUNT,account);
        Intent intent=new Intent();
        if(account.equals("admin")){
            intent.setClass(this,SettingActivity.class);
        }else{
            intent.setClass(this,MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
