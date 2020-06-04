package com.bdkj.wirecrimping.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.bean.StandardValuesBean;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AddTwoDialog extends Dialog {
    private Context context;
    private EditText et_model, et_applyWire, et_max_D, et_min_D, et_max_d, et_min_d, et_steel_pressure, et_steel_L,
            et_aluminum_D, et_aluminum_d, et_aluminum_pressure, et_aluminum_L, et_steel_diameter, et_pressureDistrict;
    private Spinner spinner;
    private StandardValuesBean.DataBean dataBeanModify = new StandardValuesBean.DataBean();
    private List<StandardValuesBean.DataBean> dataBeanList = new ArrayList<>();
    private String standardStr;
    private TextView tv_add_current;
    //铝管的基本尺寸
    private final String[] lgSize={"D≤32","32<D≤50","50<D≤80"};
    public AddTwoDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddTwoDialog(Context context, int themeStyle, StandardValuesBean.DataBean dataBean) {
        super(context, themeStyle);
        this.context = context;
        this.dataBeanModify = dataBean;

    }

    protected AddTwoDialog(Context context, boolean cancelable
            , OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_add_two, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setWindowAnimations(R.style.AnimBottom);
        window.setAttributes(lp);
        tv_add_current = findViewById(R.id.tv_add_current);
        et_model = (EditText) findViewById(R.id.et_model);
        et_applyWire = (EditText) findViewById(R.id.et_applyWire);
        et_max_D = (EditText) findViewById(R.id.et_max_D);
        et_min_D = (EditText) findViewById(R.id.et_min_D);
        et_max_d = (EditText) findViewById(R.id.et_max_d);
        et_min_d = (EditText) findViewById(R.id.et_min_d);
        et_steel_pressure = (EditText) findViewById(R.id.et_steel_pressure);
        et_steel_L = (EditText) findViewById(R.id.et_steel_L);
        et_aluminum_D = (EditText) findViewById(R.id.et_aluminum_D);
        et_aluminum_d = (EditText) findViewById(R.id.et_aluminum_d);
        et_aluminum_pressure = (EditText) findViewById(R.id.et_aluminum_pressure);
        et_aluminum_L = (EditText) findViewById(R.id.et_aluminum_L);
//        et_steel_diameter = (EditText) findViewById(R.id.et_steel_diameter);
//        et_pressureDistrict = (EditText) findViewById(R.id.et_pressureDistrict);
        spinner=findViewById(R.id.sp_lg);
        /**
         * 初始化铝管基本尺寸
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lgSize);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        view.findViewById(R.id.ib_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_model.getText().toString())) {
                    ToastUtils.showShort("请输入型号");
                    return;
                } else if (TextUtils.isEmpty(et_applyWire.getText().toString())) {
                    ToastUtils.showShort("请输入适用导线");
                    return;
                } else if (TextUtils.isEmpty(et_max_D.getText().toString())) {
                    ToastUtils.showShort("请输入钢管D最大值");
                    return;
                } else if (TextUtils.isEmpty(et_min_D.getText().toString())) {
                    ToastUtils.showShort("请输入钢管D最小值");
                    return;
                } else if (TextUtils.isEmpty(et_max_d.getText().toString())) {
                    ToastUtils.showShort("请输入钢管d最大值");
                    return;
                } else if (TextUtils.isEmpty(et_min_d.getText().toString())) {
                    ToastUtils.showShort("请输入钢管d最小值");
                    return;
                } else if (TextUtils.isEmpty(et_steel_pressure.getText().toString())) {
                    ToastUtils.showShort("请输入钢管压后值");
                    return;
                } else if (TextUtils.isEmpty(et_steel_L.getText().toString())) {
                    ToastUtils.showShort("请输入钢管L值");
                    return;
                } else if (TextUtils.isEmpty(et_aluminum_D.getText().toString())) {
                    ToastUtils.showShort("请输入铝管D值");
                    return;
                } else if (TextUtils.isEmpty(et_aluminum_d.getText().toString())) {
                    ToastUtils.showShort("请输入铝管d值");
                    return;
                } else if (TextUtils.isEmpty(et_aluminum_pressure.getText().toString())) {
                    ToastUtils.showShort("请输入铝管压后值");
                    return;
                } else if (TextUtils.isEmpty(et_aluminum_L.getText().toString())) {
                    ToastUtils.showShort("请输入铝管L值");
                    return;
                }
//                else if (TextUtils.isEmpty(et_steel_diameter.getText().toString())) {
//                    ToastUtils.showShort("请输入钢环直径值");
//                    return;
//                } else if (TextUtils.isEmpty(et_pressureDistrict.getText().toString())) {
//                    ToastUtils.showShort("请输入不区压长值");
//                    return;
//                }
                StandardValuesBean.DataBean dataBean = new StandardValuesBean.DataBean();
                dataBean.setModel(et_model.getText().toString());
                dataBean.setApplyWire(et_applyWire.getText().toString());
                dataBean.setSteel_D_big(Double.parseDouble(et_max_D.getText().toString()));
                dataBean.setSteel_D_min(Double.parseDouble(et_min_D.getText().toString()));
                dataBean.setSteel_d_big(Double.parseDouble(et_max_d.getText().toString()));
                dataBean.setSteel_d_min(Double.parseDouble(et_min_d.getText().toString()));
                dataBean.setSteel_pressure_after(Double.parseDouble(et_steel_pressure.getText().toString()));
                dataBean.setSteel_L(Integer.parseInt(et_steel_L.getText().toString()));
                dataBean.setAluminum_D(Double.parseDouble(et_aluminum_D.getText().toString()));
                dataBean.setAluminum_d(Double.parseDouble(et_aluminum_d.getText().toString()));
                dataBean.setAluminum_pressure_after(Double.parseDouble(et_aluminum_pressure.getText().toString()));
                dataBean.setAluminum_L(Integer.parseInt(et_aluminum_L.getText().toString()));
//                dataBean.setSteel_diameter(Integer.parseInt(et_steel_diameter.getText().toString()));
//                dataBean.setPressureDistrict(Integer.parseInt(et_pressureDistrict.getText().toString()));
                dataBeanList.add(dataBean);
                int num;
                for (int i = 0; i < dataBeanList.size(); i++) {
                    num = i + 1;
                    dataBeanList.get(i).setSerialNumber(num);
                }
                standardStr = JsonUtil.objectToString(dataBeanList);
                SpUtils.getInstance(context).savaString(Constant.STANDARDVALUESTWO, standardStr);
                EventBus.getDefault().post("耐张数据");
                dismiss();

            }
        });
        initDatas();
    }

    private void initDatas() {
        if (dataBeanModify != null) {
            tv_add_current.setText("修改");
            et_model.setText(dataBeanModify.getModel());
            et_applyWire.setText(dataBeanModify.getApplyWire());
            et_max_D.setText(String.valueOf(dataBeanModify.getSteel_D_big()));
            et_min_D.setText(String.valueOf(dataBeanModify.getSteel_D_min()));
            et_max_d.setText(String.valueOf(dataBeanModify.getSteel_d_big()));
            et_min_d.setText(String.valueOf(dataBeanModify.getSteel_d_min()));
            et_steel_pressure.setText(String.valueOf(dataBeanModify.getSteel_pressure_after()));
            et_steel_L.setText(String.valueOf(dataBeanModify.getSteel_L()));
            et_aluminum_D.setText(String.valueOf(dataBeanModify.getAluminum_D()));
            et_aluminum_d.setText(String.valueOf(dataBeanModify.getAluminum_d()));
            et_aluminum_pressure.setText(String.valueOf(dataBeanModify.getAluminum_pressure_after()));
            et_aluminum_L.setText(String.valueOf(dataBeanModify.getAluminum_L()));
//            et_steel_diameter.setText(String.valueOf(dataBeanModify.getSteel_diameter()));
//            et_pressureDistrict.setText(String.valueOf(dataBeanModify.getPressureDistrict()));

        }
        if (SpUtils.getInstance(context).getString(Constant.STANDARDVALUESTWO) != null && !"".equals(SpUtils.getInstance(context).getString(Constant.STANDARDVALUESTWO))) {
            dataBeanList.addAll(JsonUtil.stringToList(SpUtils.getInstance(context).getString(Constant.STANDARDVALUESTWO), StandardValuesBean.DataBean.class));
            if (!TextUtils.isEmpty(et_model.getText().toString())) {
                for (int i = 0; i < dataBeanList.size(); i++) {
                    if (et_model.getText().toString().equals(dataBeanList.get(i).getModel())) {
                        dataBeanList.remove(dataBeanList.get(i));
                        return;
                    }
                }
            }
        }
    }

}
