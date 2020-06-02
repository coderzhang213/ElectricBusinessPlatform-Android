package com.sancell.xingqiu.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.alibaba.fastjson.JSON
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.constants.utils.ConvertUtils
import com.sancell.xingqiu.entity.ImageUpParInfo
import com.sancell.xingqiu.entity.live.UpLoadPhotoInfoBean
import com.sancell.xingqiu.mvvm.repository.ImagUploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagUploadViewModel : BaseViewModel() {
    val mImagUploadRepository by lazy { ImagUploadRepository() }


    //上传图片
    fun upRemmoidtyName(fileType: String, objId: String, imgType: String, imgWidth: String, imgHeight: String, fileSize: String): MutableLiveData<UpLoadPhotoInfoBean> {
        val upload: MutableLiveData<UpLoadPhotoInfoBean> = MutableLiveData()
        val par = ConvertUtils.getRequest()
        val mImageUpParInfo = ImageUpParInfo(imgWidth, imgHeight, imgType, fileSize)
        val mList = ArrayList<ImageUpParInfo>()
        mList.add(mImageUpParInfo)
        par["rawArr"] = JSON.toJSONString(mList)

        par["objId"] = objId
        par["fileType"] = fileType
        launch {
            val response = withContext(Dispatchers.IO) {
                mImagUploadRepository.imgUpLoadInfo(par)
            }
            executeResponse(response, { upload.value = response.retData }, { errMsg.value = response.retMsg })
        }
        return upload
    }
}