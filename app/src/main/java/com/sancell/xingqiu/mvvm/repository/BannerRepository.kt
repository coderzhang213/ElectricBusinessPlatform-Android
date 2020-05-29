package com.sancell.xingqiu.mvvm.repository

import com.sancell.xingqiu.base.viewmodel.BaseRepository
import com.sancell.xingqiu.entity.banner.HomeBannerDataBean
import com.sancell.xingqiu.entity.base.ResResponse

/**
 * Created by zj on 2019/12/27.
 */
class BannerRepository : BaseRepository() {
    suspend fun getHomeBannerData(par: Map<String, String>): ResResponse<HomeBannerDataBean> {

        return mServe.getHomeBannerData(par)

    }
}