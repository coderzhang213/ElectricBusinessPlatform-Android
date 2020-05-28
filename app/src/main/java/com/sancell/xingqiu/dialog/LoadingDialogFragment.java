/**
 * @TODO
 * Author 苟俊 jndx_taibai@163.com
 * Date  @2013-12-4
 * Copyright 2013 www.daoxila.com. All rights reserved.
 */
package com.sancell.xingqiu.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.sancell.xingqiu.R;

/**
 * @author goujun
 * 
 */
public class LoadingDialogFragment extends DialogFragment {

	private String loadingString;
	private RotateAnimation rotateAnimation;
	private DialogInterface.OnCancelListener onCancelListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDialog().setOnCancelListener(onCancelListener);
		View layout = inflater.inflate(R.layout.progress_dialog, null);
		TextView tvLoadingString = (TextView) layout.findViewById(R.id.progress_dialog_msg);
		tvLoadingString.setText(loadingString);
		if (TextUtils.isEmpty(loadingString)) {
			tvLoadingString.setVisibility(View.GONE);
		}
//		ImageView loadingView = (ImageView) layout.findViewById(R.id.progress_dialog_icon);
//		rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		loadingView.setAnimation(rotateAnimation);
//		rotateAnimation.setDuration(1500);
//		rotateAnimation.setFillAfter(true);
//		rotateAnimation.setFillBefore(true);
//		rotateAnimation.setInterpolator(new LinearInterpolator());
//		rotateAnimation.setRepeatCount(Animation.INFINITE);
//		rotateAnimation.start();
		return layout;
	}

	public void setLoadingString(String loadingString) {
		this.loadingString = loadingString;
	}

	/**
	 * @param onCancelListener
	 *            the onCancelListener to set
	 */
	public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	@Override
	public void dismissAllowingStateLoss() {
		// TODO Auto-generated method stub
		super.dismissAllowingStateLoss();
		if (rotateAnimation != null)
			rotateAnimation.cancel();
	}

}
