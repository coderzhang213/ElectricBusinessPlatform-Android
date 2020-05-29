package com.sancell.xingqiu.view.live.actviity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import com.sancell.xingqiu.R
import com.sancell.xingqiu.constants.LiveConstant
import com.sancell.xingqiu.constants.UiHelper
import com.sancell.xingqiu.constants.utils.PreferencesUtils
import com.sancell.xingqiu.entity.live.PublishParam
import com.sancell.xingqiu.help.ToastHelper
import com.sancell.xingqiu.interfaces.OnLiveBomTabLinsener
import com.sancell.xingqiu.mvvm.viewmodel.LiveViewModel
import com.sancell.xingqiu.utils.ServerUtils
import com.sancell.xingqiu.view.home.LiveHomePageFgm
import com.sancell.xingqiu.view.home.fragment.ChatGroupBaseFragment
import com.sancell.xingqiu.view.home.fragment.HomeUserFragment
import com.sancell.xingqiu.view.home.fragment.MallHomeFragment
import com.sancell.xingqiu.view.live.fragment.base.LivePlayBaseHoemFragment

/**
 * 直播播放列表
 */
class LivePlayHomeActivity : LiveHomeBaseActivity<LiveViewModel>() {

    companion object {
        const val LIVE_IS_SHOW_TAB = "live_is_show_tab"
        fun startLivePlay(
            context: Context,
            type: String,
            isShowTab: Boolean
        ) {//1主页推荐列表；2关注直播列表；3个人中心直播列表
            val intent = Intent(context, LivePlayHomeActivity::class.java)
            intent.putExtra(LiveConstant.LIVE_TYPE, type)
            intent.putExtra(LIVE_IS_SHOW_TAB, isShowTab)
            context.startActivity(intent)
        }
    }

    override fun onReloadData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //启动用来监听退出直播的服务
        ServerUtils.startServer(this, false)
        //NIMClient.getService(MsgServiceObserve::class.java).observeCustomNotification(mObserver, true)

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            //todo 处理跳转

        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mLiveCheckStatus.observe(this@LivePlayHomeActivity, Observer {
                if (it.status == 2) {
                    checkLiveIsPaly()
                } else {
                    //直播设置需要第一次认证欢迎
                    PreferencesUtils.put(UiHelper.KEY_LIVE_SETTING_WELCOME, true)
                    //  LiveIdentifyActivity.start(this@LivePlayHomeActivity)
                }
            })
            errMsg.observe(this@LivePlayHomeActivity, Observer {
                ToastHelper.showToast(it)
            })
            mLiveCheckStaus.observe(this@LivePlayHomeActivity, Observer {
                if (it.has.equals("1")) {//有直播，直接跳转到直播间
                    val publishParam = PublishParam()
                    publishParam.pushUrl = it.liveInfo.pushUrl
                    publishParam.definition = PublishParam.HD
                    // AnchorHomeActivity.startLive(this@LivePlayHomeActivity, it.liveInfo.roomId, it.liveInfo.batchId, publishParam, false)

                } else {//没有去设置界面
                    //LiveSettingActivity.startIntent(this@LivePlayHomeActivity, PreferencesUtils.getBoolean(UiHelper.KEY_LIVE_SETTING_WELCOME, false))
                }

            })
        }
    }

    //是否有直播间正在直播
    fun checkLiveIsPaly() {
        mViewModel.checkLiveStatus()
    }


    override val loadNotDat: Boolean
        get() = true
    override val isShowTitle: Boolean
        get() = false

    override fun getLayoutResId(): Int = R.layout.activit_live_paly_home_layout

    override fun initView() {
//        ll_bom.setOnLiveBomTabLinsener(object : OnLiveBomTabLinsener {
//            override fun onTabAddClickLinser() {
//                mViewModel.checkVerifyStatus()
//            }
//
//            override fun onTabClcikLinsener(postion: Int) {
//                if (lastTabIndex == postion) {
//                    return
//                }
//                lastTabIndex = postion
//                mLivePlayStatusManager.setHomeShowIndex(postion)
//                getShowFragment(postion)
//            }
//        })
    }

    override fun onResume() {
        super.onResume()
    }


    override fun providerVMClass(): Class<LiveViewModel>? {
        return LiveViewModel::class.java
    }

    override fun initData() {

        val isShowTab = intent.getBooleanExtra(LIVE_IS_SHOW_TAB, true)
        if (!isShowTab) {
            //ll_bom.visibility = View.GONE
        }

        Handler().postDelayed({
            getShowFragment(0)

        }, 500)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            for (fgm in mInitfragments) {
//                if (fgm is LiveUserInfoFgm) {
//                    fgm.onActivityResult(requestCode, resultCode, data)
//                } else if (fgm is LivePlayBaseHoemFragment) {
//                    //举报
//                    if (requestCode == RequestCode.LIVER_REPORT) {
//                        Handler().postDelayed(Runnable {
//                            SCApp.getInstance().showSystemCenterToast("举报成功，感谢您的反馈")
//                        }, 1000)
//                    }
//
//                }
//            }
            }
        }
    }

    override val getAddFragmentLayoutId: Int
        get() = R.id.rl_conent

    override fun initLoadFragment() {
        val framgent = LivePlayBaseHoemFragment.getInsener(
            intent.getStringExtra(LiveConstant.LIVE_TYPE),
            "",
            ""
        )
        mInitfragments.add(LiveHomePageFgm())
        // mInitfragments.add(LiveAttenListFragment())
        mInitfragments.add(framgent)
        mInitfragments.add(MallHomeFragment())
        mInitfragments.add(ChatGroupBaseFragment())
        mInitfragments.add(HomeUserFragment())
        // mInitfragments.add(LiveUserInfoFgm.newInstance(AppUtils.getUserId(),false))//LiveUserInfoFgm
    }

}