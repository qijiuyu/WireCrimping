package com.bdkj.wirecrimping.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.R;
import com.bdkj.wirecrimping.bean.MainBean;
import com.bdkj.wirecrimping.bean.NameBean;
import com.bdkj.wirecrimping.util.ActivitysLifecycle;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.LogUtils;
import com.bdkj.wirecrimping.util.MainPopwindow;
import com.bdkj.wirecrimping.util.OpenFileUtils;
import com.bdkj.wirecrimping.util.SPUtil;
import com.bdkj.wirecrimping.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity  implements View.OnFocusChangeListener, TextWatcher {
    @BindView(R.id.ib_back)
    ImageButton ib_back;
    @BindView(R.id.ll_menu)
    LinearLayout ll_menu;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_foot_des)
    TextView tv_foot_des;
    @BindView(R.id.et_project)
    EditText etProject;
    @BindView(R.id.et_model)
    EditText etModel;
    @BindView(R.id.et_supervision)
    EditText etSupervision;
    @BindView(R.id.et_construction)
    EditText etConstruction;
    private MainPopwindow mainPopwindow;
    private Context mContext;
    private CustomPopWindow popWindow;
    public static final int REQUEST_CHOOSEFILE = 0X01;
    public static final String FILENAME = "弯曲度测量";
    private Handler mHandler = new Handler();
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
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mContext = this;
        tv_foot_des.setVisibility(View.GONE);
        ib_back.setVisibility(View.GONE);

        /**
         * 监听获得焦点
         */
        etProject.setOnFocusChangeListener(this);
        etModel.setOnFocusChangeListener(this);
        etSupervision.setOnFocusChangeListener(this);
        etConstruction.setOnFocusChangeListener(this);
        /**
         * 监听输入框输入
         */
        etProject.addTextChangedListener(this);
        etModel.addTextChangedListener(this);
        etSupervision.addTextChangedListener(this);
        etConstruction.addTextChangedListener(this);

        String data=SPUtil.getInstance(context).getString(Constant.MAIN_DATA);
        if(!TextUtils.isEmpty(data)){
            final MainBean mainBean= (MainBean) JsonUtil.stringToObject(data,MainBean.class);
            if(mainBean!=null){
                etProject.setText(mainBean.getProject());
                etModel.setText(mainBean.getModel());
                etSupervision.setText(mainBean.getSuperVision());
                etConstruction.setText(mainBean.getConstruction());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(runnable);

    }

    @OnClick({ R.id.ll_menu,R.id.tv_login_out, R.id.tv_go})
    public void onClick(View view) {
        switch (view.getId()) {
            //退出
            case R.id.tv_login_out:
                SPUtil.getInstance(this).removeMessage(Constant.ACCOUNT);
                Intent intent=new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            //进入
            case R.id.tv_go:
                String project=etProject.getText().toString().trim();
                String model=etModel.getText().toString().trim();
                String superVision=etSupervision.getText().toString().trim();
                String construction=etConstruction.getText().toString().trim();
                if(TextUtils.isEmpty(project)){
                    ToastUtils.showLong("请输入工程名");
                    return;
                }
                if(TextUtils.isEmpty(model)){
                    ToastUtils.showLong("请输入导线型号");
                    return;
                }
                if(TextUtils.isEmpty(superVision)){
                    ToastUtils.showLong("请输入监理单位");
                    return;
                }
                if(TextUtils.isEmpty(construction)){
                    ToastUtils.showLong("请输入施工单位");
                    return;
                }
                MainBean mainBean=new MainBean(project,model,superVision,construction);
                SPUtil.getInstance(this).addString(Constant.MAIN_DATA,JsonUtil.objectToString(mainBean));

                startActivity(new Intent(MainActivity.this, HardwareAndCurvatureActivity.class).putExtra("enterSign", "1"));
                break;
            //菜单
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
     * 监听获得焦点
     */
    String tag;
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            tag=v.getTag().toString();
            //设置下拉框显示的数据
            if(((EditText)v).getText().length()==0){
                getShowData();
            }else{
                //关闭下拉框
                closePopwindow();
            }
        }else{
            //关闭下拉框
            closePopwindow();
            //保存最新的值
            saveNewData(((EditText)v).getText().toString().trim());
        }
    }


    /**
     * 监听输入框内容
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()>0){
            //关闭下拉框
            closePopwindow();
        }else{
            //设置下拉框显示的数据
            getShowData();
        }
    }


    /**
     * 设置下拉框显示的数据
     */
    private void getShowData(){
        String message;
        switch (tag){
            case "1":
                message=SPUtil.getInstance(this).getString(Constant.PROJECT_NAME);
                showPopwindow(etProject,message,2);
                break;
            case "2":
                message=SPUtil.getInstance(this).getString(Constant.STANDARDVALUES);
                showPopwindow(etModel,message,1);
                break;
            case "3":
                message=SPUtil.getInstance(this).getString(Constant.SUPERVISION);
                showPopwindow(etSupervision,message,2);
                break;
            case "4":
                message=SPUtil.getInstance(this).getString(Constant.CONSTRUCTION);
                showPopwindow(etConstruction,message,2);
                break;
            default:
                break;
        }
    }


    /**
     * 展示下拉框
     */
    private void showPopwindow(EditText editText,String message,int type){
        if(mainPopwindow!=null && mainPopwindow.isShowing()){
            return;
        }
        if(TextUtils.isEmpty(message)){
            return;
        }
        mainPopwindow = new MainPopwindow(this);
        mainPopwindow.showAsDropDown(editText);
        mainPopwindow.setData(editText,message,type);
        mainPopwindow.openShow();
    }


    /**
     * 关闭下拉框
     */
    private void closePopwindow(){
        if (mainPopwindow != null && mainPopwindow.isShowing()) {
            mainPopwindow.closeShow();
            mainPopwindow=null;
        }
    }


    /**
     * 保存最新的值
     */
    private void saveNewData(String data){
        if(TextUtils.isEmpty(data)){
            return;
        }
        String totalMsg=null;
        boolean isAdd=true;
        List<NameBean> dataBeanList=new ArrayList<>();
        NameBean nameBean=new NameBean();
        switch (tag){
            case "1":
                totalMsg=SPUtil.getInstance(this).getString(Constant.PROJECT_NAME);
                if (!TextUtils.isEmpty(totalMsg)) {
                    dataBeanList.addAll(JsonUtil.stringToList(totalMsg, NameBean.class));
                    for (int i=0,len=dataBeanList.size();i<len;i++){
                        if(dataBeanList.get(i).getName().equals(data)){
                            isAdd=false;
                            break;
                        }
                    }
                }
                if(isAdd){
                    nameBean.setName(data);
                    dataBeanList.add(nameBean);
                    SPUtil.getInstance(this).addString(Constant.PROJECT_NAME, JsonUtil.objectToString(dataBeanList));
                }
                break;
            case "2":
                break;
            case "3":
                totalMsg=SPUtil.getInstance(this).getString(Constant.SUPERVISION);
                if (!TextUtils.isEmpty(totalMsg)) {
                    dataBeanList.addAll(JsonUtil.stringToList(totalMsg, NameBean.class));
                    for (int i=0,len=dataBeanList.size();i<len;i++){
                        if(dataBeanList.get(i).getName().equals(data)){
                            isAdd=false;
                            break;
                        }
                    }
                }
                if(isAdd){
                    nameBean.setName(data);
                    dataBeanList.add(nameBean);
                    SPUtil.getInstance(this).addString(Constant.SUPERVISION, JsonUtil.objectToString(dataBeanList));
                }
                break;
            case "4":
                totalMsg=SPUtil.getInstance(this).getString(Constant.CONSTRUCTION);
                if (!TextUtils.isEmpty(totalMsg)) {
                    dataBeanList.addAll(JsonUtil.stringToList(totalMsg, NameBean.class));
                    for (int i=0,len=dataBeanList.size();i<len;i++){
                        if(dataBeanList.get(i).getName().equals(data)){
                            isAdd=false;
                            break;
                        }
                    }
                }
                if(isAdd){
                    nameBean.setName(data);
                    dataBeanList.add(nameBean);
                    SPUtil.getInstance(this).addString(Constant.CONSTRUCTION, JsonUtil.objectToString(dataBeanList));
                }
                break;
            default:
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
                        FileMannage("/storage/emulated/0/弯曲度测量");
                        break;
                    case R.id.tv_hardware_measurement:
                        FileMannage("/storage/emulated/0/金具复测及对边测量");
                        break;
                }
            }
        };
        contentView.findViewById(R.id.tv_bending_measurement).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_hardware_measurement).setOnClickListener(listener);
    }

    //打开系统文件管理器
    private void FileMannage(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //调用系统文件管理器打开指定路径目录
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(Uri.fromFile(dir), OpenFileUtils.DATA_TYPE_EXCEL);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_CHOOSEFILE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1){
            Uri uri = data.getData();
            OpenFileUtils.openFile(context,OpenFileUtils.uriToFile(context,uri));
        }
    }


    // 按两次退出
    protected long exitTime = 0;
    /**
     * 连续点击两次返回退出
     * @param event
     * @return
     */
    @SuppressLint("RestrictedApi")
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showLong("再按一次退出程序!");
                exitTime = System.currentTimeMillis();
            } else {
                ActivitysLifecycle.getInstance().exit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

}
