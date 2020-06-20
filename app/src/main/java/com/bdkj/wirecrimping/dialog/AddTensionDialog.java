package com.bdkj.wirecrimping.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.activity.setting.TensionActivity;
import com.bdkj.wirecrimping.bean.StandardValuesBean;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SPUtil;
import com.bdkj.wirecrimping.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class AddTensionDialog extends Dialog {
    private Context context;
    private EditText et_model, et_applyWire, et_max_D, et_min_D, et_max_d, et_min_d, et_steel_pressure, et_steel_L,et_aluminum_D,
            et_aluminum_D_big,et_aluminum_D_min, et_aluminum_d, et_aluminum_pressure, et_aluminum_L, et_steel_diameter, et_pressureDistrict;
    private LinearLayout linLG1,linLG2;
    private Spinner spinner;
    private TextView tv_add_current;
    //要编辑的对象
    private StandardValuesBean.DataBean editBean;
    //存储所有数据的集合
    private List<StandardValuesBean.DataBean> dataBeanList = new ArrayList<>();
    //铝管的基本尺寸
    private final String[] lgSize={"D≤32","32<D≤50","50<D≤80"};
    /**
     * 铝管对应的基本尺寸
     */
    private int lgType;
    public AddTensionDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddTensionDialog(Context context, int themeStyle, StandardValuesBean.DataBean editBean) {
        super(context, themeStyle);
        this.context = context;
        this.editBean = editBean;
    }

    protected AddTensionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_tension);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setWindowAnimations(R.style.AnimBottom);
        window.setAttributes(lp);
        tv_add_current = findViewById(R.id.tv_add_current);
        et_model =  findViewById(R.id.et_model);
        et_applyWire =  findViewById(R.id.et_applyWire);
        et_max_D =  findViewById(R.id.et_max_D);
        et_min_D =  findViewById(R.id.et_min_D);
        et_max_d =  findViewById(R.id.et_max_d);
        et_min_d =  findViewById(R.id.et_min_d);
        et_steel_pressure =  findViewById(R.id.et_steel_pressure);
        et_steel_L =  findViewById(R.id.et_steel_L);
        et_aluminum_D =  findViewById(R.id.et_aluminum_D);
        et_aluminum_D_big=findViewById(R.id.et_aluminum_D_big);
        et_aluminum_D_min=findViewById(R.id.et_aluminum_D_min);
        et_aluminum_d =  findViewById(R.id.et_aluminum_d);
        et_aluminum_pressure =  findViewById(R.id.et_aluminum_pressure);
        et_aluminum_L =  findViewById(R.id.et_aluminum_L);
        spinner=findViewById(R.id.sp_lg);
        linLG1=findViewById(R.id.lin_lg1);
        linLG2=findViewById(R.id.lin_lg2);

        /**
         * 初始化铝管基本尺寸
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,lgSize);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lgType=position;
                if(position==0){
                    linLG1.setVisibility(View.GONE);
                    linLG2.setVisibility(View.VISIBLE);
                }else{
                    linLG1.setVisibility(View.VISIBLE);
                    linLG2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //获取保存的所有数据
        final String TENSION= SPUtil.getInstance(context).getString(Constant.STANDARDVALUESTWO);
        if (!TextUtils.isEmpty(TENSION)) {
            dataBeanList.addAll(JsonUtil.stringToList(TENSION, StandardValuesBean.DataBean.class));
        }

        //显示要编辑的内容
        showEditData();


        //保存
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_model.getText().toString())) {
                    ToastUtils.showLong("请输入型号");
                    return;
                } else if (TextUtils.isEmpty(et_applyWire.getText().toString())) {
                    ToastUtils.showLong("请输入适用导线");
                    return;
                } else if (TextUtils.isEmpty(et_max_D.getText().toString())) {
                    ToastUtils.showLong("请输入钢管D最大值");
                    return;
                } else if (TextUtils.isEmpty(et_min_D.getText().toString())) {
                    ToastUtils.showLong("请输入钢管D最小值");
                    return;
                } else if (TextUtils.isEmpty(et_max_d.getText().toString())) {
                    ToastUtils.showLong("请输入钢管d最大值");
                    return;
                } else if (TextUtils.isEmpty(et_min_d.getText().toString())) {
                    ToastUtils.showLong("请输入钢管d最小值");
                    return;
                } else if (TextUtils.isEmpty(et_steel_pressure.getText().toString())) {
                    ToastUtils.showLong("请输入钢管压后值");
                    return;
                } else if (TextUtils.isEmpty(et_steel_L.getText().toString())) {
                    ToastUtils.showLong("请输入钢管L值");
                    return;
                }else if(lgType==0){
                    if (TextUtils.isEmpty(et_aluminum_D_big.getText().toString())) {
                        ToastUtils.showLong("请输入铝管D最大值");
                        return;
                    }
                    if (TextUtils.isEmpty(et_aluminum_D_min.getText().toString())) {
                        ToastUtils.showLong("请输入铝管D最小值");
                        return;
                    }
                }else if (lgType>0) {
                    if (TextUtils.isEmpty(et_aluminum_D.getText().toString())) {
                        ToastUtils.showLong("请输入铝管D值");
                        return;
                    }
                } else if (TextUtils.isEmpty(et_aluminum_d.getText().toString())) {
                    ToastUtils.showLong("请输入铝管d值");
                    return;
                } else if (TextUtils.isEmpty(et_aluminum_pressure.getText().toString())) {
                    ToastUtils.showLong("请输入铝管压后值");
                    return;
                } else if (TextUtils.isEmpty(et_aluminum_L.getText().toString())) {
                    ToastUtils.showLong("请输入铝管L值");
                    return;
                }
                StandardValuesBean.DataBean dataBean = new StandardValuesBean.DataBean();
                dataBean.setModel(et_model.getText().toString());
                dataBean.setApplyWire(et_applyWire.getText().toString());
                dataBean.setSteel_D_big(Double.parseDouble(et_max_D.getText().toString()));
                dataBean.setSteel_D_min(Double.parseDouble(et_min_D.getText().toString()));
                dataBean.setSteel_d_big(Double.parseDouble(et_max_d.getText().toString()));
                dataBean.setSteel_d_min(Double.parseDouble(et_min_d.getText().toString()));
                dataBean.setSteel_pressure_after(Double.parseDouble(et_steel_pressure.getText().toString()));
                dataBean.setSteel_L(Integer.parseInt(et_steel_L.getText().toString()));
                if(lgType==0){
                    dataBean.setAluminum_D_big(Double.parseDouble(et_aluminum_D_big.getText().toString()));
                    dataBean.setAluminum_D_min(Double.parseDouble(et_aluminum_D_min.getText().toString()));
                }else{
                    dataBean.setAluminum_D(Double.parseDouble(et_aluminum_D.getText().toString()));
                }
                dataBean.setAluminum_type(lgType);
                dataBean.setAluminum_d(Double.parseDouble(et_aluminum_d.getText().toString()));
                dataBean.setAluminum_pressure_after(Double.parseDouble(et_aluminum_pressure.getText().toString()));
                dataBean.setAluminum_L(Integer.parseInt(et_aluminum_L.getText().toString()));

                //判断是新增，还是修改
                if(editBean==null){
                    dataBeanList.add(dataBean);
                }else{
                    for (int i=0,len=dataBeanList.size();i<len;i++){
                        if(editBean.getModel().equals(dataBeanList.get(i).getModel())){
                            dataBeanList.set(i,dataBean);
                            break;
                        }
                    }
                }
                SPUtil.getInstance(context).addString(Constant.STANDARDVALUESTWO, JsonUtil.objectToString(dataBeanList));
                /**
                 * 更新列表页
                 */
                if(context instanceof TensionActivity){
                    ((TensionActivity)context).showListData();
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
            et_model.setText(editBean.getModel());
            et_applyWire.setText(editBean.getApplyWire());
            et_max_D.setText(String.valueOf(editBean.getSteel_D_big()));
            et_min_D.setText(String.valueOf(editBean.getSteel_D_min()));
            et_max_d.setText(String.valueOf(editBean.getSteel_d_big()));
            et_min_d.setText(String.valueOf(editBean.getSteel_d_min()));
            et_steel_pressure.setText(String.valueOf(editBean.getSteel_pressure_after()));
            et_steel_L.setText(String.valueOf(editBean.getSteel_L()));
            spinner.setSelection(editBean.getAluminum_type());
            if(editBean.getAluminum_type()==0){
                et_aluminum_D_big.setText(String.valueOf(editBean.getAluminum_D_big()));
                et_aluminum_D_min.setText(String.valueOf(editBean.getAluminum_D_min()));
                linLG1.setVisibility(View.GONE);
            }else{
                et_aluminum_D.setText(String.valueOf(editBean.getAluminum_D()));
                linLG2.setVisibility(View.GONE);
            }
            et_aluminum_d.setText(String.valueOf(editBean.getAluminum_d()));
            et_aluminum_pressure.setText(String.valueOf(editBean.getAluminum_pressure_after()));
            et_aluminum_L.setText(String.valueOf(editBean.getAluminum_L()));
        }
    }
}
