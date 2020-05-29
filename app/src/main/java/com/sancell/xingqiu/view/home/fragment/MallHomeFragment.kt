package com.sancell.xingqiu.view.home.fragment

import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.view.BaseDataFragmentKt
import com.sancell.xingqiu.base.viewmodel.BaseViewModel

/**
 * Created by zj on 2020/5/29.
 */
class MallHomeFragment :BaseDataFragmentKt<BaseViewModel>() {
    override fun onReloadData() {
    }

    override val isLoadNotDat: Boolean
        get() = false
    override val isShowTitle: Boolean
        get() = false

    override fun getLayoutResId(): Int = R.layout.fragmetn_home_mill_layout
    override fun initView() {
    }

    override fun initData() {
    }
}