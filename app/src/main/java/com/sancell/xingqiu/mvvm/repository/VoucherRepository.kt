package com.sancell.xingqiu.mvvm.repository

import com.sancell.xingqiu.base.viewmodel.BaseRepository
import com.sancell.xingqiu.entity.base.DefalutResultInfo
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.entity.live.CouponInfo
import com.sancell.xingqiu.entity.live.VoucherCenterRes

class VoucherRepository : BaseRepository() {

    suspend fun getVoucherList(params: Map<String, String>): ResResponse<VoucherCenterRes> {
        return mServe.getVoucherCenter(params)
    }

    //直播间优惠券列表
    suspend fun getLiveRoomCoup(params: Map<String, String>): ResResponse<CouponInfo> {
        return mServe.getLiveRoomCoup(params)
    }

    suspend fun receiveLiveRoomCoupon(params: Map<String, String>): ResResponse<DefalutResultInfo> {
        return mServe.receiveLiveRoomCoupon(params)
    }
    suspend fun addStock(params: Map<String, String>): ResResponse<DefalutResultInfo> {
        return mServe.addStock(params)
    }

}