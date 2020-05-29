package cn.sancell.xingqiu.live.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.sancell.xingqiu.R
import com.sancell.xingqiu.constants.UserManager
import com.sancell.xingqiu.entity.live.LivePlayInfo
import com.sancell.xingqiu.enump.LivePagerType
import com.sancell.xingqiu.view.live.fragment.base.LiveRoomConstant
import kotlinx.android.synthetic.main.fragment_live_res_tool_layout.*
import kotlinx.android.synthetic.main.view_acd_title.*

/**
 * Created by zj on 2020/5/25.
 */
abstract class LiveReservationBaseFragment : LiveRoomConstant() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_live_res_tool_layout, null)
        val fl_content = mView.findViewById<FrameLayout>(R.id.fl_content)
        fl_content.addView(inflater.inflate(layoutId, null))
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    fun initView() {
        iv_room_exit.setOnClickListener(mOnClickLinsener)
        tv_attention.setOnClickListener(mOnClickLinsener)
    }

    override fun bingParViewText(mLivePlayInfo: LivePlayInfo) {
        super.bingParViewText(mLivePlayInfo)

        mLivePlayInfo.apply {
            bingView(this)
            tv_user_name.setText(batchInfo.liveUserName)
            val userBean = UserManager.getUserInfo()!!.userInfo
            if (this.isFollow == "1" || userBean!!.userId.toInt() == this.batchInfo.liveUserId) {//如果是主播本人也不需要显示
                tv_attention.visibility = View.GONE
            }
            if (getCurrType == LivePagerType.RESERVATION.type) {//预约状态显示预约人数
                tv_qery_sum.setText(reserveCount.toString() + "人预约")
            }
        }
    }


    val mOnClickLinsener = object : View.OnClickListener {
        override fun onClick(v: View) {

            when (v.id) {
                R.id.iv_room_exit -> {//关闭
                    onPagerCloce()
                }
                R.id.tv_attention -> {//关注
                    attentionAnchor()

                }
            }
        }
    }

    override fun attentionAnchorSucess() {
        super.attentionAnchorSucess()
        tv_attention.visibility = View.GONE
    }
}