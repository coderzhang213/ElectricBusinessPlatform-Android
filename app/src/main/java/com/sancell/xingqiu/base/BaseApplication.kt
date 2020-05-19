package com.sancell.xingqiu.base

import android.app.Application

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
    }


}