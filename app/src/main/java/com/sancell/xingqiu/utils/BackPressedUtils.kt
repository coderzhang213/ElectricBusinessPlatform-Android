package com.sancell.xingqiu.utils

import android.app.Activity
import com.sancell.xingqiu.interfaces.OnBackPressedLinsener
import com.sancell.xingqiu.interfaces.OnBackPressedRegLinsenr

/**
 * Created by zj on 2020/4/7.
 */
object BackPressedUtils {
    /**
     * 绑定回调
     */
    fun bindOnBack(activity: Activity?, mOnBackPressedLinsener: OnBackPressedLinsener) {
        if (activity == null) {
            return
        }
        if (activity is OnBackPressedRegLinsenr) {
            (activity as OnBackPressedRegLinsenr).onRegitsOnBackPressend(mOnBackPressedLinsener)
        }
    }

    /**
     * 解除回调
     */
    fun unBindOnBack(activity: Activity?, mOnBackPressedLinsener: OnBackPressedLinsener) {
        if (activity == null) {
            return
        }
        if (activity is OnBackPressedRegLinsenr) {
            (activity as OnBackPressedRegLinsenr).onRegitsOnBackPressend(mOnBackPressedLinsener)
        }
    }
}