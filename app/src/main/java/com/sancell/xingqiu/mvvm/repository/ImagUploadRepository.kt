package com.sancell.xingqiu.mvvm.repository

import com.sancell.xingqiu.base.viewmodel.BaseRepository
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.entity.live.UpLoadPhotoInfoBean

class ImagUploadRepository : BaseRepository() {


    //上传图片
    suspend fun imgUpLoadInfo(par: Map<String, String>): ResResponse<UpLoadPhotoInfoBean> {
        return mServe.imgUpLoadInfo(par)
    }
}