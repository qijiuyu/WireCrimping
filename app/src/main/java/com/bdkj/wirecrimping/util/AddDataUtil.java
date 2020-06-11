package com.bdkj.wirecrimping.util;

import android.content.Context;

import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.bean.StandardValuesBean;

import java.util.ArrayList;
import java.util.List;

public class AddDataUtil {

    private String[] ZXModel=new String[]{"JYD-185/25","JYD-185/30","JYD-185/45","JYD-240/30","JYD-240/40","JYD-240/55","JYD-300/15","JYD-300/20","JYD-300/25","JYD-300/40","JYD-300/50","JYD-300/70","JYD-400/20","JYD-400/25","JYD-400/35","JYD-400/50","JYD-400/65","JYD-630/45","JYD-630/55"};

    private String[] ZXWire=new String[]{"LGJ-185/25","LGJ-185/30","LGJ-185/45","LGJ-240/30","LGJ-240/40","LGJ-240/55","LGJ-300/15","LGJ-300/20","LGJ-300/25","LGJ-300/40","LGJ-300/50","LGJ-300/70","LGJ-400/20","LGJ-400/25","LGJ-400/35","LGJ-400/50","LGJ-400/65","LGJ-630/45","LGJ-630/55"};

    private double[] GDMax=new double[]{18.3,18.3,24.4,20.3,20.3,22.3,18.3,18.3,20.3,20.3,22.3,24.4,18.3,20.3,22.3,24.4,26.4,24.4,26.4};

    private double[] GDMin=new double[]{17.8,17.8,23.8,19.8,19.8,21.8,17.8,17.8,19.8,19.8,21.8,23.8,17.8,19.8,21.8,23.8,25.8,23.8,25.8};

    private double[] GdMax=new double[]{10.7,11.8,14.2,12.2,13.5,16.2,8.55,10,11.44,13.5,15.2,18.2,10,11.4,12.7,15.6,17.4,14.2,16.2};

    private double[] GdMin=new double[]{10.3,11.4,13.8,11.8,13.1,15.8,8.25,9.6,11,13.1,14.8,17.8,9.6,11,12.3,15.2,17,13.8,15.8};

    private double[] GS=new double[]{15.68,15.68,20.84,17.4,17.4,19.12,15.68,15.68,17.4,17.4,19.12,20.84,15.68,17.4,19.12,20.84,22.56,20.84,22.56};

    private int[] GL=new int[]{120,120,90,100,100,120,70,60,90,100,120,130,80,90,100,100,130,110,120};

    private double[] LD=new double[]{34.6,34.6,34.6,36.6,36.6,36.6,40.6,40.6,40.6,40.6,40.6,42.6,45.6,45.6,45.6,45.6,48.6,61,61};

    private double[] Ld=new double[]{20.2,20.2,20.7,22.6,22.6,23.6,24.1,24.6,25.1,25.1,25.6,26.6,28.1,28.1,28.1,29.1,29.1,35.1,35.4};

    private double[] LS=new double[]{29.44,29.44,29.44,31.16,31.16,31.16,34.6,34.6,34.6,34.6,34.6,36.32,38.9,38.9,38.9,38.9,41.48,51.8,51.8};

    private int[] LL=new int[]{420,420,360,460,440,470,470,490,480,490,490,520,550,550,540,550,560,690,680};


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


    public void addNZdata(Context context){
        List<StandardValuesBean.DataBean> dataBeanList = new ArrayList<>();
        StandardValuesBean.DataBean dataBean = new StandardValuesBean.DataBean(1,"NY-150/20","LGJ-150/20",12.2, 11.8, 6.35, 6.05, 10.52, 290, 30.4, 29.6, 17.7, 26, 290,0);
        dataBeanList.add(dataBean);
        StandardValuesBean.DataBean dataBean1 = new StandardValuesBean.DataBean(2,"NY-150/25","LGJ-150/25",14.2, 13.8, 7.15, 6.85, 12.24, 290, 30.4, 29.6, 18.2, 26, 300,0);
        dataBeanList.add(dataBean1);
        StandardValuesBean.DataBean dataBean2 = new StandardValuesBean.DataBean(3,"NY-150/35","LGJ-150/35",16.3, 15.8, 8.35, 8.05, 13.96, 290, 30.4, 29.6, 18.7, 26, 320,0);
        dataBeanList.add(dataBean2);
        StandardValuesBean.DataBean dataBean3 = new StandardValuesBean.DataBean(4,"NY-185/25","LGJ-185/25",14.2, 13.8, 7.15, 6.85, 12.24, 290, 32.4, 31.6, 20.2, 27.72, 310,0);
        dataBeanList.add(dataBean3);
        StandardValuesBean.DataBean dataBean4= new StandardValuesBean.DataBean(5,"NY-185/30","LGJ-185/30",16.3, 15.8, 7.75, 7.45, 13.96, 290, 34.6, 20.2, 29.44, 320,1);
        dataBeanList.add(dataBean4);
        StandardValuesBean.DataBean dataBean5= new StandardValuesBean.DataBean(6,"NY-185/45","LGJ-185/45",18.3, 17.8, 9.8, 9.4, 15.58, 290, 34.6, 21.2, 29.44, 340,1);
        dataBeanList.add(dataBean5);
        StandardValuesBean.DataBean dataBean6= new StandardValuesBean.DataBean(7,"NY-210/25","LGJ-210/25",14.2, 13.8, 7.45, 7.15, 12.24, 290, 34.6, 21.2, 29.44, 330,1);
        dataBeanList.add(dataBean6);
        StandardValuesBean.DataBean dataBean7= new StandardValuesBean.DataBean(8,"NY-210/35","LGJ-210/35",16.3, 15.8, 8.35, 8.05, 13.96, 290, 34.6, 21.7, 29.44, 340,1);
        dataBeanList.add(dataBean7);
        StandardValuesBean.DataBean dataBean8= new StandardValuesBean.DataBean(9,"NY-210/50","LGJ-210/50",18.3, 17.8, 9.8, 9.4, 15.58, 290, 36.6, 22.1, 31.16, 360,1);
        dataBeanList.add(dataBean8);
        StandardValuesBean.DataBean dataBean9= new StandardValuesBean.DataBean(10,"NY-240/30","LGJ-240/30",16.3, 15.8, 7.95, 7.65, 13.96, 290, 36.6, 22.6, 31.16, 390,1);
        dataBeanList.add(dataBean9);
        StandardValuesBean.DataBean dataBean10= new StandardValuesBean.DataBean(11,"NY-240/40","LGJ-240/40",16.3, 15.8, 8.85, 8.55, 13.96, 290, 36.6, 22.6, 31.16, 390,1);
        dataBeanList.add(dataBean10);
        StandardValuesBean.DataBean dataBean11= new StandardValuesBean.DataBean(12,"NY-240/55","LGJ-240/55",20.3, 19.8, 10.5, 10.1, 17.4, 290, 36.6, 23.6, 31.16, 420,1);
        dataBeanList.add(dataBean11);
        StandardValuesBean.DataBean dataBean12= new StandardValuesBean.DataBean(13,"NY-300/15","LGJ-300/15",14.2, 13.8, 5.85, 5.55, 12.24, 290, 40.6, 24.1, 34.6, 385,1);
        dataBeanList.add(dataBean12);
        StandardValuesBean.DataBean dataBean13= new StandardValuesBean.DataBean(14,"NY-300/20","LGJ-300/20",14.2, 13.8, 6.65, 6.35, 12.24, 290, 40.6, 24.6, 34.6, 390,1);
        dataBeanList.add(dataBean13);
        StandardValuesBean.DataBean dataBean14= new StandardValuesBean.DataBean(15,"NY-300/25","LGJ-300/25",14.2, 13.8, 7.45, 7.15, 12.24, 290, 40.6, 25.1, 34.6, 400,1);
        dataBeanList.add(dataBean14);
        StandardValuesBean.DataBean dataBean15= new StandardValuesBean.DataBean(16,"NY-300/40","LGJ-300/40",16.3, 15.8, 8.85, 8.55, 13.96, 290, 40.6, 25.1, 34.6, 420,1);
        dataBeanList.add(dataBean15);
        StandardValuesBean.DataBean dataBean16= new StandardValuesBean.DataBean(17,"NY-400/20","LGJ-400/20",14.2, 13.8, 6.65, 6.35, 12.24, 290, 45.6, 28.1, 38.9, 425,1);
        dataBeanList.add(dataBean16);
        StandardValuesBean.DataBean dataBean17= new StandardValuesBean.DataBean(18,"NY-400/25","LGJ-400/25",14.2, 13.8, 7.45, 7.15, 12.24, 290, 45.6, 28.1, 38.9, 435,1);
        dataBeanList.add(dataBean17);
        StandardValuesBean.DataBean dataBean18= new StandardValuesBean.DataBean(19,"NY-400/35","LGJ-400/35",16.3, 15.8, 8.35, 8.05, 13.96, 290, 45.6, 28.1, 38.9, 440,1);
        dataBeanList.add(dataBean18);
        StandardValuesBean.DataBean dataBean19= new StandardValuesBean.DataBean(20,"NY-400/50","LGJ-400/50",20.3, 19.8, 10.1, 9.7, 17.4, 290, 45.6, 29.1, 38.9, 460,1);
        dataBeanList.add(dataBean19);
        StandardValuesBean.DataBean dataBean20= new StandardValuesBean.DataBean(21,"NY-400/65","LGJ-400/65",22.3, 21.8, 11.2, 10.8, 19.12, 290, 48.6, 29.1, 41.48, 480,1);
        dataBeanList.add(dataBean20);
        StandardValuesBean.DataBean dataBean21= new StandardValuesBean.DataBean(22,"NY-400/95","LGJ-400/95",26.4, 25.8, 13.4, 13, 22.56, 290, 48.6, 30.6, 41.48, 520,1);
        dataBeanList.add(dataBean21);
        StandardValuesBean.DataBean dataBean22= new StandardValuesBean.DataBean(23,"NY-630/45","LGJ-630/45",18.3, 17.8, 9.25, 8.95, 15.58, 290, 61, 35.1, 51.8, 490,1);
        dataBeanList.add(dataBean22);
        StandardValuesBean.DataBean dataBean23= new StandardValuesBean.DataBean(24,"NY-630/55","LGJ-630/55",20.3, 19.8, 10.5, 10.1, 17.4, 290, 61, 35.6, 51.8, 510,1);
        dataBeanList.add(dataBean23);
        StandardValuesBean.DataBean dataBean24= new StandardValuesBean.DataBean(25,"NY-630/80","LGJ-630/80",24.4, 23.8, 12.5, 12.1, 20.84, 290, 61, 36, 51.8, 550,1);
        dataBeanList.add(dataBean24);

        SpUtils.getInstance(context).savaString(Constant.STANDARDVALUESTWO, JsonUtil.objectToString(dataBeanList));
    }




}
