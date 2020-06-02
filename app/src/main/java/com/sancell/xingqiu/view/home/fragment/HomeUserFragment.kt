package com.sancell.xingqiu.view.home.fragment

import androidx.lifecycle.Observer
import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.view.BaseFragment
import com.sancell.xingqiu.entity.live.PublishParam
import com.sancell.xingqiu.enump.LoadType
import com.sancell.xingqiu.mvvm.viewmodel.LiveViewModel
import com.sancell.xingqiu.view.live.actviity.AnchorHomeActivity
import com.sancell.xingqiu.view.live.actviity.LiveIdentifyActivity
import com.sancell.xingqiu.view.live.actviity.LiveSettingActivity
import kotlinx.android.synthetic.main.fragmetn_home_user_layout.*

/**
 * Created by zj on 2020/5/29.
 */
class HomeUserFragment : BaseFragment<LiveViewModel>() {
    override fun getLayoutResId(): Int = R.layout.fragmetn_home_user_layout

    override fun initView() {
        iv_start_live.setOnClickListener {

            mViewModel.checkVerifyStatus()


        }
    }

    override fun providerVMClass(): Class<LiveViewModel>? = LiveViewModel::class.java
    override fun initData() {
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mLiveCheckStatus.observe(this@HomeUserFragment, Observer {
                it?.apply {
                    if (status == 2) {//认证通过
                        //检查是否有直播
                        mViewModel.checkLiveStatus()
                    } else {
                         LiveIdentifyActivity.start(context!!)

                    }

                }

            })
            mLiveCheckStaus.observe(this@HomeUserFragment, Observer {
                it?.apply {
                    if (has.equals("1")) {//有直播
                        //去直播
                        val publishParam = PublishParam()
                        publishParam.pushUrl = liveInfo.pushUrl
                        publishParam.definition = PublishParam.HD
                        AnchorHomeActivity.startLive(
                            context,
                            liveInfo.roomId,
                            liveInfo.batchId,
                            publishParam,
                            false
                        )
                    } else {//去创建直播
                        LiveSettingActivity.startIntent(context!!, false)
                    }
                }

            })

        }
    }

    override fun onStartLoadView(loadType: LoadType) {
    }

    override fun onEndLoadView(loadType: LoadType) {
    }
}