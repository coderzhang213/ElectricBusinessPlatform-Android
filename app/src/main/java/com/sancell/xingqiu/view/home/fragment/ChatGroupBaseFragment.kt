package com.sancell.xingqiu.view.home.fragment

import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.view.BaseDataFragmentKt
import com.sancell.xingqiu.base.viewmodel.BaseViewModel

/**
 * Created by zj on 2020/5/29.
 */
class ChatGroupBaseFragment:BaseDataFragmentKt<BaseViewModel>() {
    override fun onReloadData() {
        TODO("Not yet implemented")
    }

    override val isLoadNotDat: Boolean
        get() = false
    override val isShowTitle: Boolean
        get() = false

    override fun getLayoutResId(): Int = R.layout.fragmetn_home_group_layout

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}