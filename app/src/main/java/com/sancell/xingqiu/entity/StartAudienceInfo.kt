package com.sancell.xingqiu.entity

data class StartAudienceInfo(val roomId: String,
                             val url: String,
                             val isSoftDecode: Boolean,
                             val batchId: String,
                             val liveSum: Int,
                             val isStart: Boolean,
                             val showPostion: Int,
                             val isLivePaly: Boolean,
                             var mType: String? = ""
) {
}