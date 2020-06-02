
package com.sancell.xingqiu.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.sancell.xingqiu.R;
import com.sancell.xingqiu.constants.img.GlideEngine;

import java.io.File;

public class DialogUtil {
    public static void showModifyPhoto(Activity mActivity) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.dialog_tow_select_operate,
                null);
        Dialog dialog_cancel = new Dialog(mActivity, R.style.transparentFrameWindowStyle);
        dialog_cancel.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_operate1 = view.findViewById(R.id.tv_operate1);
        TextView tv_operate2 = view.findViewById(R.id.tv_operate2);
        tv_operate1.setText(R.string.take);
        tv_operate2.setText(R.string.gallery);
        tv_operate1.setOnClickListener(view13 -> {
            dialog_cancel.dismiss();
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            PictureSelector.create(mActivity).openCamera(PictureMimeType.ofImage())
                    .isWeChatStyle(true)
                    .compress(true)
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.REQUEST_CAMERA);


        });
        tv_operate2.setOnClickListener(view12 -> {
            dialog_cancel.dismiss();
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            PictureSelector.create(mActivity).openGallery(PictureMimeType.ofImage())
                    .isWeChatStyle(true)
                    .selectionMode(PictureConfig.SINGLE)
                    .compress(true)
                    .compressSavePath(file.getPath())
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.CHOOSE_REQUEST);

        });
        tv_cancel.setOnClickListener(view1 -> dialog_cancel.dismiss());
        Window window = dialog_cancel.getWindow();
        /**
         * 位于底部
         */
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        /**
         * 设置弹出动画
         */
        window.setWindowAnimations(R.style.ani_bottom);
        // 设置点击外围解散
        dialog_cancel.setCanceledOnTouchOutside(true);
        dialog_cancel.show();
    }

}