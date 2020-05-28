package com.sancell.xingqiu.dialog;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by zj on 2019/7/30.
 */
public class LoadingDialogUtils {
    private static LoadingDialogFragment loadingDialogFragment;

    public static void showProgress(FragmentActivity activity, String loadStr, boolean isCancleable) {

        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setLoadingString(loadStr);
        loadingDialogFragment.setCancelable(isCancleable);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.add(loadingDialogFragment, "loadingDialogFragment");
        ft.commitAllowingStateLoss();

    }

    public static void showProgress(FragmentActivity activity, String loadStr) {
        showProgress(activity, loadStr, true);
    }


    public static void dimsProgress() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismissAllowingStateLoss();
            loadingDialogFragment = null;
        }
    }
}
