package com.sancell.xingqiu.constants

import com.sancell.xingqiu.enump.LoadType

/**
 * Created by zj on 2020/5/28.
 */
interface OnLoadLinsener {
    fun onStartLoadView(loadType: LoadType = LoadType.DIALOG_LOAD)
    fun onEndLoadView(loadType: LoadType = LoadType.DIALOG_LOAD)
}