package com.bdkj.wirecrimping.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_pwd)
    EditText et_pwd;

    private String account;
    private String pwd;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        context = this;
        account = "admin";
        pwd = "123456";
    }

    @OnClick(R.id.tv_login)
    public void login() {
        final String etAccount=et_account.getText().toString().trim();
        final String etPwd=et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(etAccount)) {
            ToastUtils.showShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(etPwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if (!etAccount.equals(account) || !etPwd.equals(pwd)) {
            ToastUtils.showShort("账号或密码错误");
            return;
        } else {
            LoginAccount();
        }
    }

    private void LoginAccount() {
        startActivity(new Intent(context, MainActivity.class));
        SpUtils.getInstance(context).savaString(Constant.USERNAME, account);
        finish();

    }
}
