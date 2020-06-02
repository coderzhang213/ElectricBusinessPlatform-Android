package com.sancell.xingqiu.view.live.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sancell.xingqiu.dialog.ComfirmDialog;


/**
 * Created by zhukkun on 1/5/17.
 */
public class BaseLiveFragment extends Fragment {
    protected Activity mActivity;

    protected View findViewById(int id) {
        return getView().findViewById(id);
    }

    private Toast mToast;

    protected void showToast(final String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext().getApplicationContext(), text, Toast.LENGTH_LONG);
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(text);
                    mToast.show();
                }
            });
        } else {
            mToast.setText(text);
            mToast.show();
        }
    }

    protected void showConfirmDialog(String title, String message, DialogInterface.OnClickListener okEvent, DialogInterface.OnClickListener cancelEvent) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        ComfirmDialog mComfirmDialog = new ComfirmDialog(context);
        mComfirmDialog.setCommitMsg("确定");
        mComfirmDialog.setMsg(message);
        mComfirmDialog.setCancelable(false);

        mComfirmDialog.setOnCutCityLinsener(new ComfirmDialog.OnCutCityLinsener() {
            @Override
            public void onConfirmLinsener() {
                okEvent.onClick(mComfirmDialog,1);
            }

            @Override
            public void onCancerLinsener() {

            }
        });
        mComfirmDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
