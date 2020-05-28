package com.sancell.xingqiu.base.view

import android.content.Intent
import androidx.fragment.app.Fragment
import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.dialog.LoadingDialogUtils
import com.sancell.xingqiu.enump.LoadType

/**
 * Created by zj on 2018/7/5 0005.
 */
abstract class DefaultFragmetnAttachActivity<VM : BaseViewModel> : BaseActivity<VM>() {
    var mLoadFragment: Fragment? = null
    override fun getLayoutResId(): Int {
        return R.layout.activity_default_layout
    }

    override fun initView() {
        mLoadFragment = loadFragment
        bindIntent(intent, mLoadFragment!!)
        if (mLoadFragment != null) { //把fragment添加到activity
            addFragment(R.id.rl_content, mLoadFragment)
        }
    }

    open fun bindIntent(intent: Intent?, mLoadFragment: Fragment) {

    }

    /**
     * 给viewModel提供结束加载效果
     */
    override fun onEndLoadView(loadType: LoadType) {
        LoadingDialogUtils.dimsProgress()
    }

    /**
     * 给viewModel提供开始加载效果
     */
    override fun onStartLoadView(loadType: LoadType) {
        LoadingDialogUtils.showProgress(this, "正在加载数据...")
    }

    protected abstract val loadFragment: Fragment?
}