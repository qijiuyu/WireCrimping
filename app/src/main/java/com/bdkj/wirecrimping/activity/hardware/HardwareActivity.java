package com.bdkj.wirecrimping.activity.hardware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.bean.HardwareBean;
import com.bdkj.wirecrimping.bean.HardwareTwoBean;
import com.bdkj.wirecrimping.bean.JSValueBean;
import com.bdkj.wirecrimping.bean.MeasureBean;
import com.bdkj.wirecrimping.bean.ModelBean;
import com.bdkj.wirecrimping.bean.MyObject;
import com.bdkj.wirecrimping.bean.PhotoAddressBean;
import com.bdkj.wirecrimping.dialog.MeasureDialog;
import com.bdkj.wirecrimping.util.DateUtils;
import com.bdkj.wirecrimping.util.HardwareLineExcel;
import com.bdkj.wirecrimping.util.HardwareStraightExcel;
import com.bdkj.wirecrimping.util.LogUtils;
import com.bdkj.wirecrimping.util.OpenFileUtils;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IBleCallback;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.common.PropertyType;
import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.exception.BleException;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.utils.HexUtil;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.OnClick;

public class HardwareActivity extends Activity  implements View.OnClickListener {
    LinearLayout ll_menu;
    static WebView wv_hardware_straight;
    TextView tv_date_time;

    private String signStr;
    private List<String> modelString = new ArrayList<>();//型号
    private List<String> wireString = new ArrayList<>(); //使用导线
    private Context mContext;

    private String macAddress = "FF:FF:40:00:01:AE";
    private String serviceUUID = "0000ffff-0000-1000-8000-00805f9b34fb";
    private String characteristicUUID = "0000ff00-0000-1000-8000-00805f9b34fb";
    private String descriptorUUID = "00001801-0000-1000-8000-00805f9b34fb";
    private MeasureBean measureBean = new MeasureBean();
    public static HardwareActivity activity;
    private int maxSelectNum = 1;
    private List<LocalMedia> selectImgs = new ArrayList<>();
    private CustomPopWindow popWindow;
    public static final int REQUEST_CHOOSEFILE = 0X01;
    /**
     * true：蓝牙连接成功
     * false：蓝牙连接失败
     */
    public boolean isConnection=false;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_straight);
        activity=this;
        mContext = this;
        EventBus.getDefault().register(this);
        /**
         * 去扫描并连接蓝牙
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                startScanAndConnect();
            }
        });
        initView();
    }


    private void initView() {
        signStr = getIntent().getStringExtra("sign");
        TextView tv_title=findViewById(R.id.tv_title);
        tv_title.setText("金具复测及对边测量");
        wv_hardware_straight=findViewById(R.id.wv_hardware_straight);
        tv_date_time=findViewById(R.id.tv_date_time);
        ll_menu=findViewById(R.id.ll_menu);
        findViewById(R.id.ib_back).setOnClickListener(this);
        ll_menu.setOnClickListener(this);

        WebSettings webSettings = wv_hardware_straight.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        wv_hardware_straight.setWebChromeClient(new WebChromeClient());
        if ("1".equals(signStr)) {
            wv_hardware_straight.loadUrl("file:///android_asset/hardware_straight.html");
        } else if ("2".equals(signStr)) {
            wv_hardware_straight.loadUrl("file:///android_asset/hardware_tension.html");
        } else if ("3".equals(signStr)) {
            wv_hardware_straight.loadUrl("file:///android_asset/hardware_lines_three.html");
        } else if ("4".equals(signStr)) {
            wv_hardware_straight.loadUrl("file:///android_asset/hardware_lines_two.html");
        }
        wv_hardware_straight.addJavascriptInterface(new MyObject(this,signStr), "myObj");
        wv_hardware_straight.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                /**
                 * 去扫描并连接蓝牙
                 */
                startScanAndConnect();

                /**
                 * 获取手机当前日期传给html显示
                 */
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(new Date());
                wv_hardware_straight.loadUrl("javascript:getTime('" + time+ "')");
            }
        });
    }

    @Subscribe
    public void ResultModel(ModelBean modelBean) {
        if ("型号".equals(modelBean.getModel())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("选择的压接型号是："+modelBean.getResultModel());
                    wv_hardware_straight.loadUrl("javascript:yajieguan('" + modelBean.getResultModel() + "')");
                }
            });
        }
    }

    @Subscribe
    public void ResultWire(ModelBean modelBean) {
        if ("导线".equals(modelBean.getModel())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wv_hardware_straight.loadUrl("javascript:daoxian('" + modelBean.getResultModel() + "')");
                }
            });
        }
    }

    @Subscribe
    public void saveExcel(HardwareBean hardwareBean) {
        if ("保存".equals(hardwareBean.getSaveInformation())) {
            if(TextUtils.isEmpty(hardwareBean.getProName())){
                ToastUtils.showLong("请输入工程名称");
                return;
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * 组装文件夹路径（工程名_桩号_桩号）
                     */
                    StringBuffer sb=new StringBuffer(hardwareBean.getProName()+"_");
                    for (int i = 0; i < hardwareBean.getZhuanghao().size(); i++) {
                        final String zhuanghao=hardwareBean.getZhuanghao().get(i);
                        if(!TextUtils.isEmpty(zhuanghao)){
                            sb.append(zhuanghao+"_");
                        }
                    }

                    File dir;
                    if ("1".equals(signStr)) {
                        dir=new File("/storage/emulated/0/金具复测及对边测量/直线液压管/"+sb.substring(0,sb.length()-1)+"/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        HardwareStraightExcel.rwExcel("/storage/emulated/0/金具复测及对边测量/hardware_straight.xls", dir.getPath()+"/"+sb.toString() + DateUtils.getCurrentDateTime() + ".xls", hardwareBean);
                    } else if ("2".equals(signStr)) {
                        dir=new File("/storage/emulated/0/金具复测及对边测量/耐张液压管/"+sb.substring(0,sb.length()-1)+"/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        HardwareStraightExcel.rwExcel("/storage/emulated/0/金具复测及对边测量/hardware_tension.xls", dir.getPath()+"/"+sb.toString() + DateUtils.getCurrentDateTime() + ".xls", hardwareBean);
                    }
                    ToastUtils.showShort("保存成功");
                    finish();
                }
            });
        }

    }

    @Subscribe
    public void SaveExcel(HardwareTwoBean hardwareTwoBean) {
        if ("保存数据".equals(hardwareTwoBean.getSaveInformation())) {
            if(TextUtils.isEmpty(hardwareTwoBean.getProName())){
                ToastUtils.showLong("请输入工程名称");
                return;
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * 组装文件夹路径（工程名_桩号_桩号）
                     */
                    StringBuffer sb=new StringBuffer(hardwareTwoBean.getProName()+"_");
                    if ("3".equals(signStr)) {
                        final String zhuanghao=hardwareTwoBean.getZhuanghaoStr();
                        if(!TextUtils.isEmpty(zhuanghao)){
                            sb.append(zhuanghao+"_");
                        }
                    }else{
                        for (int i = 0; i < hardwareTwoBean.getZhuanghao().size(); i++) {
                            final String zhuanghao=hardwareTwoBean.getZhuanghao().get(i);
                            if(!TextUtils.isEmpty(zhuanghao)){
                                sb.append(zhuanghao+"_");
                            }
                        }
                    }

                    File dir;
                    if ("3".equals(signStr)) {
                        dir=new File("/storage/emulated/0/金具复测及对边测量/线线3/"+sb.substring(0,sb.length()-1)+"/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        HardwareLineExcel.rwExcel("/storage/emulated/0/金具复测及对边测量/hardware_three.xls", dir.getPath() +"/"+sb.toString() + DateUtils.getCurrentDateTime() + ".xls", hardwareTwoBean,signStr);
                    } else if ("4".equals(signStr)) {
                        dir=new File("/storage/emulated/0/金具复测及对边测量/线线2/"+sb.substring(0,sb.length()-1)+"/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        HardwareLineExcel.rwExcel("/storage/emulated/0/金具复测及对边测量/hardware_two.xls", dir.getPath()+"/"+sb.toString() + DateUtils.getCurrentDateTime() + ".xls", hardwareTwoBean,signStr);
                    }
                    ToastUtils.showShort("保存成功");
                    finish();
                }
            });
        }
    }

    @Subscribe
    public void getValue(JSValueBean jsValueBean) {
        if ("value".equals(jsValueBean.getMaxOrminDes())) {
            measureBean.setMeasureValueJs(jsValueBean.getGqwnum1());
            MeasureDialog measureDialog = new MeasureDialog(mContext, R.style.MyDialog, signStr);
            measureDialog.show();
        }
    }


    @Subscribe
    public void SelectPhoto(String message) {
        if ("选择图片".equals(message)) {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(maxSelectNum)
                    .minSelectNum(1)
                    .imageSpanCount(4)
                    .selectionMode(PictureConfig.SINGLE)
                    .compress(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectImgs = PictureSelector.obtainMultipleResult(data);
                    LocalMedia localMedia = selectImgs.get(0);
                    String path;
                    if (localMedia.isCompressed()) {//为压缩
                        path = localMedia.getCompressPath();
                    } else {
                        path = localMedia.getPath();
                    }
                    upLoadImage(path);
                    break;
            }
        }
    }

    private void upLoadImage(String pictur) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wv_hardware_straight.loadUrl("javascript:resultPhoto('" + pictur + "')");
            }
        });
    }


    @Subscribe
    public void amplificationPhoto(PhotoAddressBean photoAddressBean) {
        if ("放大图片".equals(photoAddressBean.getPhotoDes())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wv_hardware_straight.loadUrl("javascript:amplificationPhoto('" + photoAddressBean.getPhotoSelect() + "')");
                }
            });
        }

    }

    @Subscribe
    public void narrowPhoto(String message) {
        if ("缩小图片".equals(message)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wv_hardware_straight.loadUrl("javascript:narrowPhoto()");
                }
            });
        }

    }

    public void setLoadUrl(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String MODEL_OR_CONDUCTOR=SpUtils.getInstance(mContext).getString(Constant.MODEL_OR_CONDUCTOR);
                if ("1".equals(signStr) || "2".equals(signStr)) {
                    if ("gqduibianmax".equals(MODEL_OR_CONDUCTOR)|| "gqduibianmin".equals(MODEL_OR_CONDUCTOR) || "lduibianmax".equals(MODEL_OR_CONDUCTOR) || "lduibianmin".equals(MODEL_OR_CONDUCTOR)) {
                        wv_hardware_straight.loadUrl("javascript:maxorminafter('" + message + "')");
                    } else {
                        wv_hardware_straight.loadUrl("javascript:maxormin('" + message + "')");
                    }
                } else {
                    if ("lhwqmax".equals(MODEL_OR_CONDUCTOR) || "lhwqmin".equals(MODEL_OR_CONDUCTOR) || "ghwqmax".equals(MODEL_OR_CONDUCTOR) || "ghwqmin".equals(MODEL_OR_CONDUCTOR)) {
                        wv_hardware_straight.loadUrl("javascript:maxorminafter('" + message + "')");
                    } else {
                        wv_hardware_straight.loadUrl("javascript:maxormin('" + message + "')");
                    }
                }
            }
        });
    }

    @OnClick({R.id.ib_back, R.id.ll_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ll_menu:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_menu_layout, null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(ll_menu, -30, -195);
                break;
        }
    }

    /**
     * 菜单处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null) {
                    popWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.tv_bending_measurement:
                        FileMannage();
                        break;
                    case R.id.tv_hardware_measurement:
                        FileMannage();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_bending_measurement).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_hardware_measurement).setOnClickListener(listener);
    }

    //打开系统文件管理器
    private void FileMannage() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.showShort("暂无SD卡");
            return;
        }
        //获取文件路径
        File dir = new File("/storage/emulated/0/弯曲度测量/线线2");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(dir), OpenFileUtils.DATA_TYPE_EXCEL);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CHOOSEFILE);
    }


    //扫描设备并连接
    private int connectNum=0; //重连次数
    private void startScanAndConnect() {
        ViseBle.getInstance().connectByMac(macAddress, new IConnectCallback() {
            public void onConnectSuccess(DeviceMirror deviceMirror) {
                isConnection=true;
                ToastUtils.showShort("蓝牙设备连接成功");
                BluetoothGattChannel bluetoothGattChannel = new BluetoothGattChannel.Builder()
                        .setBluetoothGatt(deviceMirror.getBluetoothGatt())
                        .setPropertyType(PropertyType.PROPERTY_INDICATE)
                        .setServiceUUID(UUID.fromString(serviceUUID))
                        .setCharacteristicUUID(UUID.fromString(characteristicUUID))
                        .setDescriptorUUID(UUID.fromString(descriptorUUID))
                        .builder();
                deviceMirror.bindChannel(new IBleCallback() {
                    @Override
                    public void onSuccess(byte[] data, BluetoothGattChannel bluetoothGattInfo, BluetoothLeDevice bluetoothLeDevice) {
                        ViseLog.i("callback success:" + HexUtil.encodeHexStr(data));
                        if (bluetoothGattInfo != null && (bluetoothGattInfo.getPropertyType() == PropertyType.PROPERTY_INDICATE
                                || bluetoothGattInfo.getPropertyType() == PropertyType.PROPERTY_NOTIFY)) {
                            setNotify(deviceMirror, bluetoothGattInfo.getGattInfoKey());

                        }

                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }
                }, bluetoothGattChannel);
                //在绑定初始化通道后需要注册通知，不然接收不到数据
                deviceMirror.registerNotify(true);

            }

            @Override
            public void onConnectFailure(BleException exception) {
                isConnection=false;
                //蓝牙连接失败后，就清空下蓝牙缓存数据
                ViseBle.getInstance().clear();
                if(connectNum==0){
                    ++connectNum;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Log.e("tag","重新扫描连接一次");
                            startScanAndConnect();
                        }
                    },1000);
                    return;
                }
                ToastUtils.showShort(exception.getDescription());
            }

            @Override
            public void onDisconnect(boolean isActive) {
            }
        });
    }

    private void setNotify(DeviceMirror deviceMirror, String key) {
        deviceMirror.setNotifyListener(key, new IBleCallback() {
            @Override
            public void onSuccess(byte[] data, BluetoothGattChannel bluetoothGattChannel, BluetoothLeDevice bluetoothLeDevice) {
                String successStr = HexUtil.encodeHexStr(data).substring(0, 6);
                String cc = Long.valueOf(successStr, 16).toString();
                double dd = (double) Integer.parseInt(cc) / 100;
                measureBean.setMeasureDes("测量值");
                measureBean.setMeasureValue(dd);
                EventBus.getDefault().post(measureBean);

            }

            @Override
            public void onFailure(BleException exception) {
                ViseLog.i("callback fail:" + exception);
            }
        });
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(runnable, 1000);
            long sysTime = System.currentTimeMillis();//获取系统时间
            CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);//时间显示格式
            tv_date_time.setText(sysTimeStr); //更新时间
            Log.d("time", sysTimeStr + "");
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ViseBle.getInstance().disconnect();
        ViseBle.getInstance().clear();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        SpUtils.getInstance(mContext).savaString(Constant.MODEL, "");
        SpUtils.getInstance(mContext).savaString(Constant.WIRE, "");
    }
}
