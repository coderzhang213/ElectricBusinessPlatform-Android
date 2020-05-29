package com.sancell.xingqiu.entity.user

import com.sancell.xingqiu.entity.live.LiveFollowInfo

data class LiveParFollowInfo(val dataCount: Int, val dataList: List<LiveFollowInfo>) {
}