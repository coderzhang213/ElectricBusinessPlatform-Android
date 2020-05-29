package com.sancell.xingqiu.view.live.fragment.base

import android.os.Bundle
import androidx.lifecycle.Observer
import cn.sancell.xingqiu.util.observer.ObserverKey
import com.sancell.xingqiu.constants.observer.ObserverManger
import com.sancell.xingqiu.constants.observer.OnObserver
import com.sancell.xingqiu.dialog.GiveARewardDialog
import com.sancell.xingqiu.enump.LivePlayType
import com.sancell.xingqiu.help.ToastHelper
import com.sancell.xingqiu.interfaces.OnGiveReadLinsenr
import com.sancell.xingqiu.utils.ScreenUtil
import com.sancell.xingqiu.view.live.help.LiveImHelp
import kotlinx.android.synthetic.main.fragment_live_tool_layout.*
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.*

/**
 * Created by zj on 2020/5/25.
 */
abstract class LiveRoomConstant : LiveCommFragment() {
    //点赞数
    var mlikeCount = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLiveImHelp = LiveImHelp(activity, getRoomId, "1")
        ObserverManger.getInstance(ObserverKey.LIVE_REM_CLOSE).registerObserver(liveStatusObeser)

    }

    open fun attentionAnchorSucess() {

    }

    //用来监听状态
    private val liveStatusObeser = OnObserver {
        if (currPagerIsShow()) {
            onPagerCloce()
        }
    }

    override fun setInPageTypeView(mType: String) {
        super.setInPageTypeView(mType)
        val layPar = v_top.layoutParams
        if (mType == LivePlayType.HOME_TO_TYPE.type) {//推荐进入
            layPar.height = ScreenUtil.dip2px(50f)
        } else {// 其他
            layPar.height = ScreenUtil.dip2px(50f)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ObserverManger.getInstance(ObserverKey.LIVE_REM_CLOSE).removeObserver(liveStatusObeser)

    }

    /**
     * 关注主播
     */
    fun attentionAnchor() {
        mLivePlayInfo?.apply {
            mLiveViewModel?.upLiveFollow(this.batchInfo.liveUserId.toString(), "1")?.observe(this@LiveRoomConstant, Observer {
                ToastHelper.showToast("关注成功")

                attentionAnchorSucess()
            })
        }
    }

    /**
     * 到用户主页
     */
    fun toUserHomePager() {
        //LiveOtherInfoActivity.start(context!!, mLivePlayInfo?.batchInfo?.liveUserId.toString())

    }

    /**
     * 点击群组
     */
    fun clickGroup() {
         mLiveImHelp?.click("b")
        //showFloat()
    }

    //点击商品
    fun clickCommodity() {
        mLiveImHelp?.click("a")
    }

    //点赞
    fun likeAnchor() {
        mLivePlayInfo?.apply {
            //点赞动画
            setPraise(iv_click_praise, iv_df_zan)
            //可以一直点赞，但必须等接口回调成功后
            iv_df_zan.setEnabled(false)
            mLiveViewModel?.upLiveGilveLink(this.batchInfo.liveBatchId, "1")?.observe(this@LiveRoomConstant, Observer {
                iv_df_zan.setEnabled(true)
                mlikeCount++
                setLikeCout()
                // ToastHelper.showToast("点赞成功")

            })
        }
    }

    open fun setLikeCout() {

    }

    /**
     * 打赏主播
     */
    fun rewardAnchor() {
        if (activity == null) {
            return
        }
        val mGiveARewardDialog = GiveARewardDialog(activity!!, mToReadLinsenr)
        mGiveARewardDialog.setReadPackValue(mLiveViewModel, this@LiveRoomConstant)
        mGiveARewardDialog.show()
    }

    private val mToReadLinsenr = object : OnGiveReadLinsenr {

        override fun onClcikGive(money: String) {
            toCheacPlayPasswork(money)
        }
    }

    /**
     * 检查支付 密码是否设置
     */
    fun toCheacPlayPasswork(money: String) {
        mLivePlayInfo?.apply {
            mLiveViewModel?.checkPayPass()?.observe(this@LiveRoomConstant, Observer {
                if (it.check == "1") {
                    showPlayPassDialog(money)
                } else {//跳转到设置界面
                    //跳转密码设置
                //    ModifyPayPwdCheckPhoneActivity.start(context)
                }

            })

        }
    }

    fun showPlayPassDialog(money: String) {
//        val mDial = PlayDialogUtils()
//        mDial.showCheckPayPwdDialog(context, object : OnPlayPasswordLinsenr {
//            override fun onComrinPlayPaasord(pass: String) {
//                mDial.dismiss()
//                toReadPack(money, pass)
//            }
//        })
    }

    /**
     * 去打赏
     */
    fun toReadPack(money: String, pass: String) {
        mLivePlayInfo?.apply {
            mLiveViewModel?.readPackLink(this.batchInfo.liveBatchId, money, pass)?.observe(this@LiveRoomConstant, Observer {
                ToastHelper.showToast("打赏成功")

            })

        }
    }

}