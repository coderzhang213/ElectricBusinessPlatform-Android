package com.sancell.xingqiu.view.live.actviity

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.sancell.xingqiu.base.view.DefaultFragmetnAttachActivity
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.view.live.fragment.LiveSettingFragment

/**
 * 直播设置
 */
class LiveSettingActivity : DefaultFragmetnAttachActivity<BaseViewModel>() {

    companion object {
        fun startIntent(context: Context, isShowAlter: Boolean) {
            val intent = Intent(context, LiveSettingActivity::class.java)
            intent.putExtra("isShowAlter", isShowAlter)
            context.startActivity(intent)
        }
    }

    override val loadFragment: Fragment?
        get() = LiveSettingFragment()

    override fun initData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mLoadFragment?.apply {
            onActivityResult(requestCode, resultCode, data)
        }
    }
}