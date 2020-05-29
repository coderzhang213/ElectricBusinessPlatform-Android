package com.sancell.xingqiu.entity.login

/**
 * Created by zj on 2020/5/28.
 */
data class UserLastLoginData(
    var client_type: String = "",
    var created_at: String = "",
    var expire_duration: String = "",
    var imei: String = "",
    var updated_at: String = "",
    var skey: String = ""
)