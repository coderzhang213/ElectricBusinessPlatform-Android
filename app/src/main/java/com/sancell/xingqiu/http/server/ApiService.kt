package com.sancell.xingqiu.http.server

import com.sancell.xingqiu.constants.ConstantsHttpUrl
import com.sancell.xingqiu.entity.base.DefalutResultInfo
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.entity.login.LoginResultData
import com.sancell.xingqiu.entity.login.StartupResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by zj on 2020/5/28.
 */
interface ApiService {
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.START_UP)
    suspend fun startUp(@FieldMap maps: Map<String, String>): ResResponse<StartupResult>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_CODE)
    suspend fun getCode(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    //短信验证码登录
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_CODE_LOGIN)
    suspend fun codeLogin(@FieldMap maps: Map<String, String>): ResResponse<LoginResultData>
}