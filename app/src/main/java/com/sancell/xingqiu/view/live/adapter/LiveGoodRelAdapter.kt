package com.sancell.xingqiu.view.live.adapter

import android.graphics.Paint
import android.text.TextUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sancell.xingqiu.R
import com.sancell.xingqiu.entity.live.VideoRelationRes.InfoList
import com.sancell.xingqiu.utils.FontUtils

class LiveGoodRelAdapter(data: List<InfoList?>?) :
    BaseQuickAdapter<InfoList, BaseViewHolder>(
        R.layout.view_live_crem_layout,
        data
    ) {
    override fun convert(helper: BaseViewHolder, item: InfoList) {
        val iv_goods =
            helper.getView<AppCompatImageView>(R.id.iv_goods)
        Glide.with(mContext).load(item.coverPicThumb).into(iv_goods)
        if (TextUtils.isEmpty(item.titleAlias)) {
            helper.setText(R.id.tv_name, item.title)
        } else {
            helper.setText(R.id.tv_name, item.titleAlias)
        }
        helper.setText(R.id.tv_sort, (helper.layoutPosition + 1) .toString())
        helper.setText(
            R.id.tv_real_price,
            FontUtils.getInstance().changeTextSize(
                mContext,
                14,
                String.format(
                    mContext.resources.getString(R.string.price_rmb),
                    item.relPrice
                ),
                0,
                1
            )
        )
        val tv_markPrice =
            helper.getView<AppCompatTextView>(R.id.tv_price_market)
        tv_markPrice.text = String.format(
            mContext.resources.getString(R.string.price_rmb),
            item.getNewMarketPriceE2()
        )
        helper.setText(R.id.tv_cj_sum, item.liveVolume.toString() + "")
        helper.setText(R.id.tv_click_sum, item.goodsBrowse.toString() + "")
        tv_markPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
        helper.addOnClickListener(R.id.rl_item)
    }
}