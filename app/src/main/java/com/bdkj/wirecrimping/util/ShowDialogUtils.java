package com.bdkj.wirecrimping.util;

import android.content.Context;

/**
 * Created by bdkj on 2018/11/22.
 */

public class ShowDialogUtils {

    public Context context;
    private static LoadingDialog loadingDialog;

    public static void showDialog(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        loadingDialog.show();

    }

    public static void dismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

}
