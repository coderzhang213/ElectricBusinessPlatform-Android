package com.sancell.xingqiu.base

import android.app.Application
import android.text.TextUtils
import com.sancell.xingqiu.constants.network.NetStateUtils
import com.sancell.xingqiu.constants.network.NetWorkMonitorManager
import com.sancell.xingqiu.help.ToastHelper

/**
 * Created by zj on 2020/5/19.
 */
class BaseApplication : Application() {

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
    }

    fun initApp() {

    }


}