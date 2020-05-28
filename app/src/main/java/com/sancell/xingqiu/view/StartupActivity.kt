package com.sancell.xingqiu.view

import android.Manifest
import android.content.Intent
import androidx.lifecycle.Observer
import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.view.BaseDataActivityKt
import com.sancell.xingqiu.constants.UserManager
import com.sancell.xingqiu.help.ToastHelper
import com.sancell.xingqiu.mvvm.viewmodel.LoginViewModel
import com.sancell.xingqiu.view.login.activity.CodeLoginActivity

/**
 * Created by zj on 2020/5/28.
 * 启动界面
 */
class StartupActivity : BaseDataActivityKt<LoginViewModel>() {
    override fun onReloadData() {
    }

    override fun providerVMClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override val loadNotDat: Boolean
        get() = false
    override val isShowTitle: Boolean
        get() = false

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {

            mStartUp.observe(this@StartupActivity, Observer {
                if (it.isLogin) {//已经登录去首页
                    //缓存本地数据
                    UserManager.startUpCacheUserInfo(it)
                } else {//去登录界面
                    startActivity(Intent(this@StartupActivity, CodeLoginActivity::class.java))

                }

            })
            mException.observe(this@StartupActivity, Observer {
                ToastHelper.showToast("登录异常，请退出重新打开")

            })
            errMsg.observe(this@StartupActivity, Observer {
                ToastHelper.showToast(it)
            })
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_startup_layout

    override fun initView() {
    }

    override fun initData() {
        mViewModel.startUp()

    }

}