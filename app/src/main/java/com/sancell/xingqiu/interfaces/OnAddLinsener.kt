package com.sancell.xingqiu.interfaces

import com.sancell.xingqiu.entity.live.RecomLiveInfo

/**
 * Created by zj on 2020/3/18.
 */
interface OnAddRecommendLinsener {
    fun onAddRecommendData(dataList: List<RecomLiveInfo>?)
}