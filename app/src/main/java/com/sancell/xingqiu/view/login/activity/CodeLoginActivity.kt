package com.sancell.xingqiu.view.login.activity

import androidx.fragment.app.Fragment
import com.sancell.xingqiu.base.view.DefaultFragmetnAttachActivity
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.view.login.fragment.CodeLoginFragment

/**
 * Created by zj on 2020/5/28.
 */
class CodeLoginActivity : DefaultFragmetnAttachActivity<BaseViewModel>() {
    override val loadFragment: Fragment?
        get() = CodeLoginFragment()

    override fun initData() {
    }
}