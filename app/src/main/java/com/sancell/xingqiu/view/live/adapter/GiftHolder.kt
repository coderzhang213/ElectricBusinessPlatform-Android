package com.sancell.xingqiu.view.live.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sancell.xingqiu.R
import com.sancell.xingqiu.entity.live.RedEnvelopeInfo
import com.sancell.xingqiu.glide.ImageLoaderUtils
import com.sancell.xingqiu.interfaces.HolederBindDatLinsener

/**
 * Created by zj on 2020/4/3.
 */
class GiftHolder(contxt: Context, mView: View) : RecyclerView.ViewHolder(mView),
    HolederBindDatLinsener<RedEnvelopeInfo> {

    var mContext: Context? = null
    var mView: View? = null
    var uci_user_icon: ImageView? = null
    var tv_user_name: TextView? = null


    init {
        this.mView = mView
        this.mContext = contxt
        findView()
    }

    fun findView() {
        uci_user_icon = mView?.findViewById(R.id.uci_user_icon)
        tv_user_name = mView?.findViewById(R.id.tv_user_name)

    }

    override fun onBindViewData(data: RedEnvelopeInfo) {
        tv_user_name?.setText(data.nickName)
        ImageLoaderUtils.loadImage(mContext, data.gravatar, uci_user_icon!!)
    }




}