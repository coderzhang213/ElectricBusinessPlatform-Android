package cn.sancell.xingqiu.live.base

import android.os.Bundle
import android.os.UserManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import cn.sancell.xingqiu.util.observer.ObserverKey
import com.sancell.xingqiu.R
import com.sancell.xingqiu.constants.observer.ObserverManger
import com.sancell.xingqiu.dialog.CouponDialog
import com.sancell.xingqiu.entity.ObserverBuild
import com.sancell.xingqiu.entity.live.CouponItemInfo
import com.sancell.xingqiu.entity.live.LivePlayInfo
import com.sancell.xingqiu.enump.LivePagerType
import com.sancell.xingqiu.glide.ImageLoaderUtils
import com.sancell.xingqiu.help.ToastHelper
import com.sancell.xingqiu.interfaces.OnCoupnGetLinener
import com.sancell.xingqiu.utils.NumberUtils
import com.sancell.xingqiu.view.live.fragment.base.LiveRoomConstant
import kotlinx.android.synthetic.main.fragment_live_capture_tool_layout.rl_live_end
import kotlinx.android.synthetic.main.fragment_live_capture_tool_layout.tv_msg
import kotlinx.android.synthetic.main.fragment_live_tool_layout.*
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.*
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.iv_click_praise
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.iv_df_zan
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.iv_shar
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.ll_rig_tool
import kotlinx.android.synthetic.main.vie_live_toom_bom_tool.tv_input_mssage
import kotlinx.android.synthetic.main.view_acd_title.*
import kotlinx.android.synthetic.main.view_acd_title.rl_user_info_par
import kotlinx.android.synthetic.main.view_acd_title.tv_qery_sum
import kotlinx.android.synthetic.main.view_acd_title.tv_user_name
import kotlinx.android.synthetic.main.view_acd_title.uci_user_icon

/**
 * 客户端
 */
abstract class LivePlayToolBaseFragment : LiveRoomConstant() {

    //是否简洁模式
    var isConciseModel = false


    var mCouponDialog: CouponDialog? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_live_tool_layout, null)
        val fl_content = mView.findViewById<FrameLayout>(R.id.fl_content)
        fl_content.addView(inflater.inflate(layoutId, null))
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initBaseData()


    }


    /**
     * 开始直播
     */
    fun onPlayStart() {
        setRePlayStatus(true, false)
    }

    /**
     * 暂停
     */
    fun onPlayPause() {
        setRePlayStatus(false, false)
    }

    /**
     * 显示直播结束
     */
    fun showLivePlayEnd() {
        rl_live_end.visibility = View.VISIBLE
        ll_rig_tool.visibility = View.GONE
        tv_msg.text = "直播已结束"
        playIsEnd = true
    }

    /**
     * 显示播放异常
     */
    open fun showLivePlayError(error: String) {
        if (playIsEnd) {
            return
        }
        if (rl_live_end == null) {
            return
        }
        rl_live_end.visibility = View.VISIBLE
        tv_re_start.visibility = View.VISIBLE
        ll_rig_tool.visibility = View.GONE
        tv_msg.setText(error)
    }

    fun initView() {
        tv_re_start.setOnClickListener(mOnClickLinsener)
        tv_concise_model.setOnClickListener(mOnClickLinsener)
        tv_attention.setOnClickListener(mOnClickLinsener)

        iv_df_zan.setOnClickListener(mOnClickLinsener)
        iv_commodity.setOnClickListener(mOnClickLinsener)
        iv_group.setOnClickListener(mOnClickLinsener)
        iv_give.setOnClickListener(mOnClickLinsener)
        iv_shar.setOnClickListener(mOnClickLinsener)
        uci_user_icon.setOnClickListener(mOnClickLinsener)
        iv_room_exit.setOnClickListener(mOnClickLinsener)
        tv_input_mssage.setOnClickListener(mOnClickLinsener)
        tv_input_mssage.setText("加入聊天…")
        if (getCurrType == LivePagerType.REPLAY.type) {//只要回放才有暂停功能
           // tv_concise_model.visibility = View.INVISIBLE
            rl_all_conent.setOnClickListener(mOnClickLinsener)
            iv_play.setOnClickListener(mOnClickLinsener)

        } else if (getCurrType == LivePagerType.LIVE_PLAY.type) {//直播双击点赞
           // tv_concise_model.visibility = View.VISIBLE

            rl_all_conent.setOnClickListener {
//                if (System.currentTimeMillis() - mLikeLastTime <= 1000) {
//
//                }
                //   mLikeLastTime = System.currentTimeMillis()

            }
        }


    }

    override fun setShowCoupon() {
        if (getCurrType == LivePagerType.LIVE_PLAY.type) {
            iv_coupon_icon.visibility = View.VISIBLE
            iv_coupon_icon.setOnClickListener(mOnClickLinsener)
        }

    }

    /**
     * 输入框是否显示
     */
    fun setSendMsgIsShow(isShow: Boolean) {
        if (tv_input_mssage == null) {
            return
        }
        if (isShow) {
            tv_input_mssage.visibility = View.VISIBLE
        } else {
            tv_input_mssage.visibility = View.GONE
        }
    }

    fun initBaseData() {
        mLiveViewModel?.errMsg?.observe(viewLifecycleOwner, Observer {
            ToastHelper.showToast(it)
        })
    }

    /**
     * 设置点赞数
     */
    override fun setLikeCout() {
        super.setLikeCout()
        tv_praise_sum?.setText(NumberUtils.getNumberToWan(mlikeCount))
    }


    override fun bingParViewText(mLivePlayInfo: LivePlayInfo) {
        super.bingParViewText(mLivePlayInfo)
        this.mLivePlayInfo = mLivePlayInfo
        mLivePlayInfo.apply {
            mlikeCount = likeCount
            if (getCurrType == LivePagerType.REPLAY.type) {//回放
                ll_rep_name.visibility = View.VISIBLE
                tv_repl_name.setText(batchInfo.liveTitle)
            }
            if (getCurrType == LivePagerType.LIVE_PLAY.type) {
                if (isBindingCoupon) {//有优惠券才显示
                    setShowCoupon()
                }
            }

            onUpLiveWahtSum(onlineUserCount)
            setLikeCout()
            tv_user_name.setText(this.batchInfo.liveUserName)
            val userBean = com.sancell.xingqiu.constants.UserManager.getUserInfo()!!.userInfo

            if (this.isFollow == "1" || userBean?.userId!!.toInt() == this.batchInfo.liveUserId) {//如果是主播本人也不需要显示
                tv_attention.visibility = View.GONE
            }

            ImageLoaderUtils.loadImage(context, this.batchInfo.gravatar, uci_user_icon)

            if (getCurrType == LivePagerType.RESERVATION.type) {//预约状态显示预约人数
                tv_qery_sum.setText(this.reserveCount.toString() + "人预约")
            } else if (getCurrType == LivePagerType.REPLAY.type) {//回放
                getLivewSum()
            } else if (getCurrType == LivePagerType.LIVE_PLAY.type) {//直播要循环检查
                startLoopCheckLive()
            }
        }
    }

    override fun onDestroyView() {
        if (getCurrType == LivePagerType.LIVE_PLAY.type) { //只有直播才有
            goAwayLive()
        }
        super.onDestroyView()
        mRxTimerUtil?.cancel()
    }

    /**
     * 离开直播间
     */
    fun goAwayLive() {
        ObserverManger.getInstance(ObserverKey.GO_AWAY_LIVE).notifyObserver(getRoomId)
    }


    /**
     * 设置回放暂停
     */
    fun setRePlayStatus(isPlay: Boolean, isNotf: Boolean) {
        if (getCurrType == LivePagerType.REPLAY.type) {//只要回放才能暂停
            if (isPlay) {
                rl_content_play.visibility = View.GONE
            } else {
                rl_content_play.visibility = View.VISIBLE
            }
            if (isNotf) {
                onPlayStatus(isPlay)
            }

        }

    }

    override fun onUpLikeSum(sum: Int) {
        super.onUpLikeSum(sum)
        mlikeCount = sum
        setLikeCout()
        //点赞动画
        setPraise(iv_click_praise, iv_df_zan)
    }

    override fun onUpLiveWahtSum(sum: Int) {
        super.onUpLiveWahtSum(sum)
        if (getCurrType == LivePagerType.LIVE_PLAY.type) {
            if (tv_qery_sum != null) {
                tv_qery_sum.setText(sum.toString() + "观看")
            }
        }
    }

    open fun onPlayStatus(isPlay: Boolean) {


    }

    val mOnClickLinsener = object : View.OnClickListener {
        override fun onClick(v: View) {

            when (v.id) {
                R.id.uci_user_icon -> {//跳转到用户主页
                    toUserHomePager()
                }
                R.id.tv_concise_model -> {//简洁模式
                    if (isConciseModel) {
                        isConciseModel = false
                        tv_concise_model.setText("简洁模式")
                    } else {
                        isConciseModel = true
                        tv_concise_model.setText("互动模式")
                    }
                    if (getCurrType == LivePagerType.REPLAY.type) {
                        setVisStatus(ll_rep_name)
                    }
                    setVisStatus(rl_user_info_par)
                    val data = ObserverBuild("1", isConciseModel)
                    isShowStreamline(isConciseModel)
                    //去同事title改变状态
                    ObserverManger.getInstance(ObserverKey.LIVE_CONCISE_OPEN).notifyObserver(data)

                }
                R.id.tv_attention -> {//关注
                    attentionAnchor()

                }

                R.id.iv_df_zan -> {//赞点击
                    likeAnchor()
                }
                R.id.iv_commodity -> {//点击商品
                    clickCommodity()
                }
                R.id.iv_group -> {//点击群组
                    clickGroup()

                }
                R.id.iv_give -> {//打赏
                    rewardAnchor()
                }
                R.id.iv_shar -> {//分享
                    sharLive(mLivePlayInfo)
                }
                R.id.rl_all_conent -> {//点击整个播放界面
                    setRePlayStatus(false, true)
                }
                R.id.iv_play -> {
                    setRePlayStatus(true, true)
                }
                R.id.tv_re_start -> {//从新开始
                    tv_re_start.visibility = View.GONE
                    rl_live_end.visibility = View.GONE
                    ll_rig_tool.visibility = View.VISIBLE
                    //从新去加载
                    onRePlay()
                }
                R.id.iv_coupon_icon -> {//优惠券
                    mLiveViewModel?.getLiveRoomCoupon(getRoomId)?.observe(this@LivePlayToolBaseFragment, Observer {
                        it?.apply {
                            this.dataList.apply {
                                if (this.size > 0) {
                                    mCouponDialog?.apply {
                                        if (isShowing) {
                                            dismiss()
                                        }
                                    }
                                    mCouponDialog = CouponDialog(activity!!, object :
                                        OnCoupnGetLinener {
                                        override fun onGetCounGet(mInfo: CouponItemInfo) {
                                            receiveCoupon(mInfo)
                                        }
                                    }, this)
                                    mCouponDialog?.show()
                                } else {
                                    ToastHelper.showToast("暂无优惠券")
                                }


                            }

                        }
                    })

                }
                R.id.iv_room_exit -> {//关闭
                    onPagerCloce()
                }
                R.id.tv_input_mssage -> {//发送消息
                    sendMsg()
                }
            }
        }
    }

    /**
     * 发送消息
     */
    open fun sendMsg() {

    }

    override fun attentionAnchorSucess() {
        super.attentionAnchorSucess()
        tv_attention.visibility = View.GONE
    }

    /**
     * 领取优惠券
     */
    fun receiveCoupon(mInfo: CouponItemInfo) {
        mLiveViewModel?.receiveLiveRoomCoupon(mInfo.id)?.observe(this@LivePlayToolBaseFragment, Observer {
            ToastHelper.showToast("领取成功")

        })
    }


    fun setVisStatus(view: View) {
        if (view.visibility == View.VISIBLE) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.VISIBLE
        }


    }
}
