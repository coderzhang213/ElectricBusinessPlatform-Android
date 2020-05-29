package com.sancell.xingqiu.constants

import com.google.gson.Gson
import com.sancell.xingqiu.base.BaseApplication
import com.sancell.xingqiu.constants.utils.PreferencesUtils
import com.sancell.xingqiu.entity.login.LoginResultData
import com.sancell.xingqiu.entity.login.StartupResult
import com.sancell.xingqiu.entity.login.UserData
import com.google.gson.GsonBuilder
import com.sancell.xingqiu.utils.JsonUtils

/**
 * Created by zj on 2020/5/28.
 */
object UserManager {
    fun cacheLocalUserInfo(mLoginResultData: LoginResultData?) {
        mLoginResultData?.apply {
            user?.apply {
                PreferencesUtils.put(ConstantsKey.KEY_USERINFO, this)
            }

            PreferencesUtils.put(ConstantsKey.KEY_SKEY, skey)
            PreferencesUtils.put(ConstantsKey.key_im_accid, yunxin_accid)
            PreferencesUtils.put(ConstantsKey.key_im_token, yunxin_token)
            PreferencesUtils.put(ConstantsKey.key_im_user_name, yunxin_name)
        }

    }

    /**
     * 检查数据登录缓存
     */
    fun startUpCacheUserInfo(mStartupResult: StartupResult?) {
        mStartupResult?.apply {
            user?.apply {
                PreferencesUtils.put(ConstantsKey.KEY_USERINFO, this)
            }
            PreferencesUtils.put(ConstantsKey.KEY_SKEY, skey)
        }

    }

    /**
     * 用户退出
     */
    fun userExit() {
        val content = BaseApplication.getInstance().applicationContext
        PreferencesUtils.remove(content, ConstantsKey.KEY_USERINFO)
        PreferencesUtils.remove(content, ConstantsKey.KEY_SKEY)
        PreferencesUtils.remove(content, ConstantsKey.key_im_accid)
        PreferencesUtils.remove(content, ConstantsKey.key_im_token)
        PreferencesUtils.remove(content, ConstantsKey.key_im_user_name)
    }

    //获取用户SKY
    fun getUserSkey(): String = PreferencesUtils.getString(ConstantsKey.KEY_SKEY, "")

    /**
     * 获取用户基本信息
     */
    fun getUserInfo(): UserData? {
        val userInfo = PreferencesUtils.getString(ConstantsKey.KEY_USERINFO, "")
        if (userInfo != null) {
           // return JsonUtils.getObject(userInfo, UserData::class.java)
            return Gson().fromJson(userInfo, UserData::class.java)
        }
        return null
    }
}