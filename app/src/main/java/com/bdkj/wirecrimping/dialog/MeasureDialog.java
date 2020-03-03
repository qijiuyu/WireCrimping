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
import com.bdkj.wirecrimping.bean.MeasureBean;
import com.bdkj.wirecrimping.bean.StandardValuesBean;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MeasureDialog extends Dialog {

    private Context context;
    private TextView tv_value_one, tv_value_two, tv_value_three, tv_value_four, tv_value_five, tv_confirm;
    private List<String> listValue = new ArrayList<>();
    private MeasureBean measure = new MeasureBean();
    private List<StandardValuesBean.DataBean> dataBeanList = new ArrayList<>();
    private static final int COMPLETED = 0;
    private String sign;
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
                if ("1".equals(sign) || "2".equals(sign)) {
                    if (!TextUtils.isEmpty(SpUtils.getInstance(context).getString(Constant.MODEL))) {
                        if (!TextUtils.isEmpty(tv_value_one.getText().toString()) && !TextUtils.isEmpty(tv_value_two.getText().toString())) {
                            if (SpUtils.getInstance(context).getString(Constant.STANDARDVALUES) != null && !"".equals(SpUtils.getInstance(context).getString(Constant.STANDARDVALUES))) {
                                dataBeanList.addAll(JsonUtil.stringToList(SpUtils.getInstance(context).getString(Constant.STANDARDVALUES), StandardValuesBean.DataBean.class));
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (dataBeanList.get(i).getModel().equals(SpUtils.getInstance(context).getString(Constant.MODEL))) {
                                        if ("gqwmax".equals(SpUtils.getInstance(context).getString(Constant.TYPE)) || "gqwmin".equals(SpUtils.getInstance(context).getString(Constant.TYPE))) {
                                            measure.setMax(dataBeanList.get(i).getSteel_D_big());
                                            measure.setMin(dataBeanList.get(i).getSteel_D_min());
                                        } else if ("gqnmax".equals(SpUtils.getInstance(context).getString(Constant.TYPE)) || "gqnmin".equals(SpUtils.getInstance(context).getString(Constant.TYPE))) {
                                            measure.setMax(dataBeanList.get(i).getSteel_d_big());
                                            measure.setMin(dataBeanList.get(i).getSteel_d_min());
                                        } else if ("gqduibianmax".equals(SpUtils.getInstance(context).getString(Constant.TYPE)) || "gqduibianmin".equals(SpUtils.getInstance(context).getString(Constant.TYPE))) {
                                            measure.setMax(dataBeanList.get(i).getSteel_pressure_after());
                                            measure.setMin(dataBeanList.get(i).getSteel_pressure_after());
                                        } else if ("lqwmax".equals(SpUtils.getInstance(context).getString(Constant.TYPE)) || "lqwmin".equals(SpUtils.getInstance(context).getString(Constant.TYPE))) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_D());
                                            measure.setMin(dataBeanList.get(i).getAluminum_D());
                                        } else if ("lnmax".equals(SpUtils.getInstance(context).getString(Constant.TYPE)) || "lnmin".equals(SpUtils.getInstance(context).getString(Constant.TYPE))) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_d());
                                            measure.setMin(dataBeanList.get(i).getAluminum_d());
                                        } else if ("lduibianmax".equals(SpUtils.getInstance(context).getString(Constant.TYPE)) || "lduibianmin".equals(SpUtils.getInstance(context).getString(Constant.TYPE))) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_pressure_after());
                                            measure.setMin(dataBeanList.get(i).getAluminum_pressure_after());
                                        }
                                    }
                                }
                                String aa = JsonUtil.objectToString(measure);
                                if (HardwareActivity.activity != null) {
                                    HardwareActivity.activity.setLoadUrl(JsonUtil.objectToString(measure));
                                    dismiss();
                                }

                            } else {
                                ToastUtils.showShort("请先录入标准值");
                            }
                        } else {
                            ToastUtils.showShort("请先测量值");
                        }
                    } else {
                        ToastUtils.showShort("请先选择压接管型号");
                    }
                } else {
                    if (!TextUtils.isEmpty(SpUtils.getInstance(context).getString(Constant.WIRE))) {
                        if (!TextUtils.isEmpty(tv_value_one.getText().toString()) && !TextUtils.isEmpty(tv_value_two.getText().toString())) {
                            if (SpUtils.getInstance(context).getString(Constant.STANDARDVALUES) != null && !"".equals(SpUtils.getInstance(context).getString(Constant.STANDARDVALUES))) {
                                dataBeanList.addAll(JsonUtil.stringToList(SpUtils.getInstance(context).getString(Constant.STANDARDVALUES), StandardValuesBean.DataBean.class));
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    if (dataBeanList.get(i).getApplyWire().equals(SpUtils.getInstance(context).getString(Constant.WIRE))) {
                                        if ("ggwqmax".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO)) || "ggwqmin".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO))) {
                                            measure.setMax(dataBeanList.get(i).getSteel_D_big());
                                            measure.setMin(dataBeanList.get(i).getSteel_D_min());
                                        } else if ("lqwqmax".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO)) || "lqwqmin".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO))) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_D());
                                            measure.setMin(dataBeanList.get(i).getAluminum_D());
                                        } else if ("ghwqmax".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO)) || "ghwqmin".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO))) {
                                            measure.setMax(dataBeanList.get(i).getSteel_pressure_after());
                                            measure.setMin(dataBeanList.get(i).getSteel_pressure_after());
                                        } else if ("lhwqmax".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO)) || "lhwqmin".equals(SpUtils.getInstance(context).getString(Constant.TYPETWO))) {
                                            measure.setMax(dataBeanList.get(i).getAluminum_pressure_after());
                                            measure.setMin(dataBeanList.get(i).getAluminum_pressure_after());
                                        }
                                    }
                                }
                                String aa = JsonUtil.objectToString(measure);
                                if (HardwareActivity.activity != null) {
                                    HardwareActivity.activity.setLoadUrl(JsonUtil.objectToString(measure));
                                    dismiss();
                                }

                            } else {
                                ToastUtils.showShort("请先录入标准值");
                            }
                        } else {
                            ToastUtils.showShort("请先测量值");
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
            listValue.add(String.valueOf(measureBean.getMeasureValue()));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = COMPLETED;
                    msg.obj = measureBean.getMeasureValue();
                    handler.sendMessage(msg);
                }
            }).start();


        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }
}
