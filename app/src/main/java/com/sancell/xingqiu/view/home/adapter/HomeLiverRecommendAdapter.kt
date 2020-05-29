package com.sancell.xingqiu.view.home.adapter

import android.graphics.BitmapFactory
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sancell.xingqiu.R
import com.sancell.xingqiu.entity.live.HomeRecommendLiverBean
import com.sancell.xingqiu.glide.ImageLoaderUtils


/**
 * 主播横向推荐列表
 */
class HomeLiverRecommendAdapter(data: List<HomeRecommendLiverBean.LiverBean>) :
        BaseQuickAdapter<HomeRecommendLiverBean.LiverBean, BaseViewHolder>(R.layout.recycle_home_live_recommend, data) {


    override fun convert(helper: BaseViewHolder, item: HomeRecommendLiverBean.LiverBean) {
        helper.getView<AppCompatTextView>(R.id.tv_liver_name).text = item.nickName
        val iv_tag = helper.getView<AppCompatImageView>(R.id.iv_liver_tag)
        val iv_recommend_img = helper.getView<AppCompatImageView>(R.id.iv_recommend_img)
        ImageLoaderUtils.loadCircleImage(mContext, item.gravatar, iv_recommend_img)
        Glide.with(mContext).load(R.drawable.icon_home_live_video_status_living).into(iv_tag)

    }

}