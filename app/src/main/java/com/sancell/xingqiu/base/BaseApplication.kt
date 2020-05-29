package com.sancell.xingqiu.base

import android.app.Application
import android.text.TextUtils
import com.sancell.xingqiu.constants.network.NetStateUtils
import com.sancell.xingqiu.constants.network.NetWorkMonitorManager
import com.sancell.xingqiu.help.ToastHelper
import com.tencent.liteav.demo.lvb.liveroom.debug.GenerateTestUserSig
import com.tencent.rtmp.TXLiveBase

/**
 * Created by zj on 2020/5/19.
 */
class BaseApplication : Application() {
    var licenceUrl =
        "http://license.vod2.myqcloud.com/license/v1/442a93c113745d2c786b12b0ea3ade73/TXLiveSDK.licence"
    var licenseKey = GenerateTestUserSig.SECRETKEY
    companion object {
        private var mAppInstance: BaseApplication? = null
        fun getInstance(): BaseApplication {
            return mAppInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mAppInstance = this

        val processName: String = NetStateUtils.getProcessName()
        if (!TextUtils.isEmpty(processName) && processName == this.packageName) { //只在主进程初始化
            mainInitApp()
        }
        initApp()
    }

    fun mainInitApp() {
        NetWorkMonitorManager.getInstance().init(mAppInstance)
        ToastHelper.init(this)
        TXLiveBase.getInstance()
            .setLicence(applicationContext, licenceUrl, licenseKey)
    }

    fun initApp() {

    }


}