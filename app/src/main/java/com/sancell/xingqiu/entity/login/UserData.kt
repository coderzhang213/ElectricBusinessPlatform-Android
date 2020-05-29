package com.sancell.xingqiu.entity.login

/**
 * Created by zj on 2020/5/28.
 */
data class UserData(
    var mobile: String = "",
    var userInfo: UserInfoData? = null,
    var userLastLogin: UserLastLoginData? = null
)