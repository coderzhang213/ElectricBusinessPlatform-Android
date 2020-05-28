package com.sancell.xingqiu.entity.login

/**
 * Created by zj on 2020/5/28.
 */
data class LoginResultData(
    var user: UserData? = null,
    var skey: String = "",
    var yunxin_accid: String = "",
    var yunxin_name: String = "",
    var yunxin_token: String = "",
    var newUser: String = ""
) {
}