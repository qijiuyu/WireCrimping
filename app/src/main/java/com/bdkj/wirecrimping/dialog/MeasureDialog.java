package com.bdkj.wirecrimping.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.activity.hardware.HardwareActivity;
import com.bdkj.wirecrimping.bean.MainBean;
import com.bdkj.wirecrimping.bean.MeasureBean;
import com.bdkj.wirecrimping.bean.StandardValuesBean;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SPUtil;
import com.bdkj.wirecrimping.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MeasureDialog extends Dialog {

    private Context context;
    private TextView tv_value_one, tv_value_two, tv_value_three, tv_value_four, tv_value_five;
    //保存每次测量的值
    private List<Double> listValue = new ArrayList<>();
    private MeasureBean measure = new MeasureBean();
    private List<StandardValuesBean.DataBean> dataBeanList = new ArrayList<>();
    private static final int COMPLETED = 0;
    private String sign;
    //点击的位置是钢管还是铝管等
    private String MODEL_OR_CONDUCTOR;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == COMPLETED) {
                if (TextUtils.isEmpty(tv_value_one.getText().toString())) {
                    tv_value_one.setText(String.valueOf(msg.obj));
                } else if (TextUtils.isEmpty(tv_value_two.getText().toString())) {
                    tv_value_two.setText(String.valueOf(msg.obj));
                } else if (TextUtils.isEmpty(tv_value_three.getText().toString())) {
                    tv_value_three.setText(String.valueOf(msg.obj));
                } else if (TextUtils.isEmpty(tv_value_four.getText().toString())) {
                    tv_value_four.setText(String.valueOf(msg.obj));
                } else if (TextUtils.isEmpty(tv_value_five.getText().toString())) {
                    tv_value_five.setText(String.valueOf(msg.obj));
                } else {
                    ToastUtils.showShort("请确定测量值");
                }
            }
        }
    };


    public MeasureDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MeasureDialog(Context context, int themeStyle, String sign) {
        super(context, themeStyle);
        this.context = context;
        this.sign = sign;

        String STANDARDVALUES;
        if(sign.equals("1") || sign.equals("4")){
            STANDARDVALUES= SPUtil.getInstance(context).getString(Constant.STANDARDVALUES);
        }else{
            STANDARDVALUES=SPUtil.getInstance(context).getString(Constant.STANDARDVALUESTWO);
        }
        if (!TextUtils.isEmpty(STANDARDVALUES)) {
            dataBeanList.addAll(JsonUtil.stringToList(STANDARDVALUES, StandardValuesBean.DataBean.class));
        }
    }

    protected MeasureDialog(Context context, boolean cancelable
            , OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        View view = View.inflate(getContext(), R.layout.dialog_measure, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setWindowAnimations(R.style.AnimBottom);
        window.setAttributes(lp);
        tv_value_one = (TextView) findViewById(R.id.tv_value_one);
        tv_value_two = (TextView) findViewById(R.id.tv_value_two);
        tv_value_three = (TextView) findViewById(R.id.tv_value_three);
        tv_value_four = (TextView) findViewById(R.id.tv_value_four);
        tv_value_five = (TextView) findViewById(R.id.tv_value_five);
        view.findViewById(R.id.ib_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measure.setListValue(listValue);
                //获取点击的位置是钢管还是铝管等
                MODEL_OR_CONDUCTOR=SPUtil.getInstance(context).getString(Constant.MODEL_OR_CONDUCTOR);
                if ("1".equals(sign) || "2".equals(sign)) {

                    //要先选择压接管型号
                    if (!TextUtils.isEmpty(SPUtil.getInstance(context).getString(Constant.MODEL))) {

                        //最少测量三个数据
                        if (!TextUtils.isEmpty(tv_value_one.getText().toString()) && !TextUtils.isEmpty(tv_value_two.getText().toString()) && !TextUtils.isEmpty(tv_value_three.getText().toString())) {

                            if (dataBeanList!=null && dataBeanList.size()>0) {
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (dataBeanList.get(i).getModel().equals(SPUtil.getInstance(context).getString(Constant.MODEL))) {
                                        //钢管-压前值-D外径-最大和最小
                                        if ("gqwmax".equals(MODEL_OR_CONDUCTOR) || "gqwmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getSteel_D_big());
                                            measure.setMin(dataBeanList.get(i).getSteel_D_min());
                                        }
                                        //钢管-压前值-d内径-最大和最小
                                        else if ("gqnmax".equals(MODEL_OR_CONDUCTOR) || "gqnmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getSteel_d_big());
                                            measure.setMin(dataBeanList.get(i).getSteel_d_min());
                                        }
                                        //钢管-压后值-对边距s-最大和最小
                                        else if ("gqduibianmax".equals(MODEL_OR_CONDUCTOR) || "gqduibianmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getSteel_pressure_after());
                                            measure.setMin(0);
                                        }
                                        //铝管-压前值-D外径-最大和最小
                                        else if ("lqwmax".equals(MODEL_OR_CONDUCTOR) || "lqwmin".equals(MODEL_OR_CONDUCTOR)) {
                                            if ("1".equals(sign)){   //直线
                                                measure.setMax(dataBeanList.get(i).getAluminum_D());
                                                measure.setMin(0);
                                            }else{                   //耐张线
                                                if(dataBeanList.get(i).getAluminum_type()==0){
                                                    measure.setMax(dataBeanList.get(i).getAluminum_D_big());
                                                    measure.setMin(dataBeanList.get(i).getAluminum_D_min());
                                                }else{
                                                    measure.setMax(dataBeanList.get(i).getAluminum_D());
                                                    measure.setMin(0);
                                                }
                                            }
                                        }
                                        //铝管-压前值-内径-最大和最小
                                        else if ("lnmax".equals(MODEL_OR_CONDUCTOR) || "lnmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMin(dataBeanList.get(i).getAluminum_d());
                                            measure.setMax(0);
                                        }
                                        //铝管-压后值-对边距-最大和最小
                                        else if ("lduibianmax".equals(MODEL_OR_CONDUCTOR) || "lduibianmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_pressure_after());
                                            measure.setMin(0);
                                        }
                                    }
                                }
                                if (HardwareActivity.activity != null) {
                                    HardwareActivity.activity.setLoadUrl(JsonUtil.objectToString(measure));
                                    dismiss();
                                }

                            } else {
                                ToastUtils.showShort("请先录入标准值");
                            }
                        } else {
                            ToastUtils.showShort("最少测量三个数据");
                        }
                    } else {
                        ToastUtils.showShort("请先选择压接管型号");
                    }
                } else {
                    final MainBean mainBean= (MainBean) JsonUtil.stringToObject(SPUtil.getInstance(context).getString(Constant.MAIN_DATA),MainBean.class);
                    if (!TextUtils.isEmpty(mainBean.getModel())) {
                        //最少测量三个数据
                        if (!TextUtils.isEmpty(tv_value_one.getText().toString()) && !TextUtils.isEmpty(tv_value_two.getText().toString()) && !TextUtils.isEmpty(tv_value_three.getText().toString())) {

                            if (dataBeanList!=null && dataBeanList.size()>0) {
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (dataBeanList.get(i).getApplyWire().equals(mainBean.getModel())) {
                                        //压前钢管-外径
                                        if ("ggwqmax".equals(MODEL_OR_CONDUCTOR) || "ggwqmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getSteel_D_big());
                                            measure.setMin(dataBeanList.get(i).getSteel_D_min());
                                        }
                                        //压前铝管-外径
                                        else if ("lqwqmax".equals(MODEL_OR_CONDUCTOR) || "lqwqmin".equals(MODEL_OR_CONDUCTOR)) {
                                            if ("4".equals(sign)){   //直线
                                                measure.setMax(dataBeanList.get(i).getAluminum_D());
                                                measure.setMin(0);
                                            }else{                   //耐张线
                                                if(dataBeanList.get(i).getAluminum_type()==0){
                                                    measure.setMax(dataBeanList.get(i).getAluminum_D_big());
                                                    measure.setMin(dataBeanList.get(i).getAluminum_D_min());
                                                }else{
                                                    measure.setMax(dataBeanList.get(i).getAluminum_D());
                                                    measure.setMin(0);
                                                }
                                            }
                                        }
                                        //压后钢管-对边距
                                        else if ("ghwqmax".equals(MODEL_OR_CONDUCTOR) || "ghwqmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getSteel_pressure_after());
                                            measure.setMin(0);
                                        }
                                        //压后铝管-对边距
                                        else if ("lhwqmax".equals(MODEL_OR_CONDUCTOR) || "lhwqmin".equals(MODEL_OR_CONDUCTOR)) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_pressure_after());
                                            measure.setMin(0);
                                        }
                                    }
                                }
                                if (HardwareActivity.activity != null) {
                                    HardwareActivity.activity.setLoadUrl(JsonUtil.objectToString(measure));
                                    dismiss();
                                }
                            } else {
                                ToastUtils.showShort("请先录入标准值");
                            }
                        } else {
                            ToastUtils.showShort("最少测量三个数据");
                        }
                    } else {
                        ToastUtils.showShort("请先选择导线型号");
                    }
                }


            }
        });

    }

    @Subscribe
    public void getValue(MeasureBean measureBean) {
        if ("测量值".equals(measureBean.getMeasureDes())) {
            measure = measureBean;
            listValue.add(measureBean.getMeasureValue());
            Message msg = new Message();
            msg.what = COMPLETED;
            msg.obj = measureBean.getMeasureValue();
            handler.sendMessage(msg);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }
}
