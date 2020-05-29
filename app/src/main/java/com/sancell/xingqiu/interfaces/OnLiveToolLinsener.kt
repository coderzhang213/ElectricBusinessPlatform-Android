package com.sancell.xingqiu.interfaces

import com.sancell.xingqiu.entity.live.LivePlayInfo

/**
 * Created by zj on 2020/5/25.
 */
interface OnLiveToolLinsener : OnPlayLinsenr {
    fun onToolShowCoupon()
    fun getToolLiveInfoData(): LivePlayInfo
    fun onToolPagerCloce()
    fun onToolUpLikeSum(sum: Int)
    fun onToolUpLiveWahtSum(sum: Int)
    fun onToolSendMsgIsShow(isShow: Boolean)
}