package com.bdkj.wirecrimping.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.activity.setting.ConstructionActivity;
import com.bdkj.wirecrimping.bean.NameBean;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SPUtil;
import com.bdkj.wirecrimping.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class AddConsDialog extends Dialog {
    private Context context;
    private EditText etName;
    private TextView tv_add_current;
    //要编辑的对象
    private NameBean editBean;
    //存储所有数据的集合
    private List<NameBean> dataBeanList = new ArrayList<>();
    public AddConsDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddConsDialog(Context context, int themeStyle, NameBean editBean) {
        super(context, themeStyle);
        this.context = context;
        this.editBean = editBean;
    }

    protected AddConsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_cons);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setWindowAnimations(R.style.AnimBottom);
        window.setAttributes(lp);
        tv_add_current = findViewById(R.id.tv_add_current);
        etName =  findViewById(R.id.et_name);

        //获取保存的所有数据
        final String CONSTRUCTION= SPUtil.getInstance(context).getString(Constant.CONSTRUCTION);
        if (!TextUtils.isEmpty(CONSTRUCTION)) {
            dataBeanList.addAll(JsonUtil.stringToList(CONSTRUCTION, NameBean.class));
        }

        //显示要编辑的内容
        showEditData();


        //保存
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ToastUtils.showLong("请输入施工单位");
                    return;
                }
                NameBean nameBean=new NameBean();
                nameBean.setName(etName.getText().toString().trim());

                //判断是新增，还是修改
                if(editBean==null){
                    dataBeanList.add(nameBean);
                }else{
                    for (int i=0,len=dataBeanList.size();i<len;i++){
                        if(editBean.getName().equals(dataBeanList.get(i).getName())){
                            dataBeanList.set(i,nameBean);
                            break;
                        }
                    }
                }
                SPUtil.getInstance(context).addString(Constant.CONSTRUCTION, JsonUtil.objectToString(dataBeanList));

                /**
                 * 更新列表页
                 */
                if(context instanceof ConstructionActivity){
                    ((ConstructionActivity)context).showListData();
                }
                dismiss();
            }
        });

        //关闭
        findViewById(R.id.ib_close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 显示要编辑的内容
     */
    private void showEditData() {
        if (editBean != null) {
            tv_add_current.setText("修改");
           etName.setText(editBean.getName());
        }
    }
}
