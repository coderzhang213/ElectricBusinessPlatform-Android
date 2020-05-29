package com.sancell.xingqiu.entity.live

class HomeRecommendLiverBean {

    var dataList: MutableList<LiverBean> = ArrayList()

    data class LiverBean(
            var nickName: String? = null,
            var gravatar: String? = null,
            val userId: String? = null
    )

}