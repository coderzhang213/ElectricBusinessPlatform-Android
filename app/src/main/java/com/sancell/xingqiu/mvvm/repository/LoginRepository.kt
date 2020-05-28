package com.sancell.xingqiu.mvvm.repository

import com.sancell.xingqiu.base.viewmodel.BaseRepository
import com.sancell.xingqiu.entity.base.DefalutResultInfo
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.entity.login.LoginResultData
import com.sancell.xingqiu.entity.login.StartupResult

/**
 * Created by zj on 2020/5/28.
 */
class LoginRepository : BaseRepository() {
    //检查登录
    suspend fun startUp(params: Map<String, String>): ResResponse<StartupResult> {
        return mServe.startUp(params)
    }

    //获取验证码
    suspend fun getCode(params: Map<String, String>): ResResponse<DefalutResultInfo> {
        return mServe.getCode(params)
    }

    /**
     * 短信验证码登录
     */
    suspend fun codeLogin(params: Map<String, String>): ResResponse<LoginResultData> {
        return mServe.codeLogin(params)
    }
}