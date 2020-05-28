package com.sancell.xingqiu.entity.login

/**
 * Created by zj on 2020/5/28.
 */
data class StartupResult(
    var isLogin: Boolean = false,
    var user: UserData? = null,
    var skey: String = ""
) {
}