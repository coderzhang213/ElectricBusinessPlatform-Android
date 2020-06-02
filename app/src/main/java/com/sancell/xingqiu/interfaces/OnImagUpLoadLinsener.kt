package com.sancell.xingqiu.interfaces

interface OnImagUpLoadLinsener {

    fun onImagUploadSucess(fildPaht: String)
    fun onImagUploadError(errorMsg: String)
}