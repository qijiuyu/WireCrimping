package com.bdkj.wirecrimping.util;

import android.content.Context;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.bean.StandardValuesBean;

import java.util.ArrayList;
import java.util.List;

public class AddDataUtil {

    private String[] ZXModel=new String[]{"JYD-185/25","JYD-185/30","JYD-185/45","JYD-240/30","JYD-240/40","JYD-240/55","JYD-300/15","JYD-300/20","JYD-300/25","JYD-300/40","JYD-300/50","JYD-300/70","JYD-400/20","JYD-400/25","JYD-400/35","JYD-400/50"};

    private String[] ZXWire=new String[]{"LGJ-185/25","LGJ-185/30","LGJ-185/45","LGJ-240/30","LGJ-240/40","LGJ-240/55","LGJ-300/15","LGJ-300/20","LGJ-300/25","LGJ-300/40","LGJ-300/50","LGJ-300/70","LGJ-400/20","LGJ-400/25","LGJ-400/35","LGJ-400/50"};

    private double[] GDMax=new double[]{18.3,18.3,24.4,20.3,20.3,22.3,18.3,18.3,20.3,20.3,22.3,24.4,18.3,20.3,22.3,24.4};

    private double[] GDMin=new double[]{17.8,17.8,23.8,19.8,19.8,21.8,17.8,17.8,19.8,19.8,21.8,23.8,17.8,19.8,21.8,23.8};

    private double[] GdMax=new double[]{10.7,11.8,14.2,12.2,13.5,16.2,8.55,10,11.44,13.5,15.2,18.2,10,11.4,12.7,15.6};

    private double[] GdMin=new double[]{10.3,11.4,13.8,11.8,13.1,15.8,8.25,9.6,11,13.1,14.8,17.8,9.6,11,12.3,15.2};

    private double[] GS=new double[]{15.68,15.68,20.84,17.4,17.4,19.12,15.68,15.68,17.4,17.4,19.12,20.84,15.68,17.4,19.12,20.84};

    private int[] GL=new int[]{120,120,90,100,100,120,70,60,90,100,120,130,80,90,100,100};

    private double[] LD=new double[]{34.6,34.6,34.6,36.6,36.6,36.6,40.6,40.6,40.6,40.6,40.6,42.6,45.6,45.6,45.6,45.6};

    private double[] Ld=new double[]{20.2,20.2,20.7,22.6,22.6,23.6,24.1,24.6,25.1,25.1,25.6,26.6,28.1,28.1,28.1,29.1};

    private double[] LS=new double[]{29.44,29.44,29.44,31.16,31.16,31.16,34.6,34.6,34.6,34.6,34.6,36.32,38.9,38.9,38.9,38.9};

    private int[] LL=new int[]{420,420,360,460,440,470,470,490,480,490,490,520,550,550,540,550};


    public void addZXdata(Context context){
        List<StandardValuesBean.DataBean> dataBeanList = new ArrayList<>();
        for (int i=0;i<ZXModel.length;i++){
            StandardValuesBean.DataBean dataBean = new StandardValuesBean.DataBean();
            dataBean.setModel(ZXModel[i]);
            dataBean.setApplyWire(ZXWire[i]);
            dataBean.setSteel_D_big(GDMax[i]);
            dataBean.setSteel_D_min(GDMin[i]);
            dataBean.setSteel_d_big(GdMax[i]);
            dataBean.setSteel_d_min(GdMin[i]);
            dataBean.setSteel_pressure_after(GS[i]);
            dataBean.setSteel_L(GL[i]);
            dataBean.setAluminum_D(LD[i]);
            dataBean.setAluminum_d(Ld[i]);
            dataBean.setAluminum_pressure_after(LS[i]);
            dataBean.setAluminum_L(LL[i]);

            dataBean.setSerialNumber(i+1);
            dataBeanList.add(dataBean);
        }

        SpUtils.getInstance(context).savaString(Constant.STANDARDVALUES, JsonUtil.objectToString(dataBeanList));
    }





}
