package com.sancell.xingqiu.constants

/**
 * Created by zj on 2020/5/28.
 */
object ConstantsHttpUrl {
    //短信验证码
    const val GET_CODE = "common/sms-code"

    //初始化
    const val START_UP = "api/start-up"

    //验证码登录
    const val GET_CODE_LOGIN = "auth/mobile-code-login"

    const val IM_LIVING_LIST = "community/v1/live/list"

    //直播登录
    const val LIVE_LOGIN = "/community/v1/live/login"

    //提交直播状态
    const val UP_ROOM_STATES = "/community/v2/live/edit-status"

    //直播推荐列表
    const val LIVE_V1_LIST = "/community/v1/live/get-rec-list"

    //获取直播详情
    const val GET_LIVE_INF = "/community/v1/live/get-live-batch"

    /**
     * 直播关注
     */
    const val LIVE_UP_FOLLOW = "/community/v1/live/follow"

    /**
     * 获取直播人数
     */
    const val GET_LIVE_SUMS = "/community/v1/live/online-user"

    /**
     * 直播预约
     */
    const val LIVE_RESER = "/community/v1/live/reserve"

    //直播点赞
    const val LIVE_GIVE_LINK = "/community/v1/live/like"

    //打赏
    const val LIVE_READ_LINE = "/community/v1/live/reward"

    //获取直播关注列表
    const val LIVE_FOLLEW_LIST = "/community/v1/live/home-live-list"

    //获取直播推荐
    const val LIVEW_RECOMMEND_LIST = "/community/v1/live/head-rec-list"

    //检查是否设置了交易密码
    const val IM_CHECK_PAY_PASS = "community/v1/red/account-password"

    /**
     * 直播间设置初始化
     */
    const val LIVE_SET_INIT = "/community/v2/live/setting-init"

    //添加商品或群组
    const val ADD_REMM_AND_GROUP = "/community/v1/live/add-relation"

    //删除直播群组或商品
    const val DELETE_REMM_AND_GROPU = "/community/v1/live/del-relation"

    //获取直播关联商品
    const val LIVE_GET_COMMINT = "shop-test/relations-list"

    //修改商品别名
    const val UP_REMMODIT_NAME = "/community/v1/live/relation-alias"

    //图片上传接口
    const val IMG_UP_LOAD = " /community/v1/live/upload"

    //直播设置保存
    const val LIVE_SET_SAVE = "/community/v2/live/setting"


    //直播热门搜索
    const val LIVE_HOT_SEARCH = "/community/v1/live/hot-search"

    //直播历史搜索
    const val LIVE_HISTORY_SEARCH = "/community/v1/live/search-history"

    //删除历史记录
    const val LIVE_HISTORY_CLEAR = "/community/v1/live/clear-history"

    const val IM_RP_YU_E = "community/v1/red/account"

    //检查是否有直播中的
    const val CHECK_LIVE_STATUS = "/community/v1/live/has-unfinished"

    //复用上一场信息
    const val UO_LAST_DATA = "community/v1/live/setting-use-relations"

    /**
     * 获取直播间状态
     */
    const val CHECK_LIVE_ROOM_STATUS = "/community/v1/live/query-batch-status"

    //订单关联的代金券
    const val VOUCHER_ASSOCIATE_GOOD = "/coupon/can-use-list"

    const val VOUCHER_CENTER = "coupon/user-owned-list"

    //获取直播间优惠券
    const val LIVE_ROOM_COUPONS = "coupon/get-live-coupons-list"
    const val GET_RECOMMEND_LIVER = "/shop-test/rec-broadcaster"

    //直播搜索
    const val LIVE_SEARCH_WORD = " /community/v1/live/search"

    //主播认证
    const val LIVER_VERIFY = "/community/v1/live/tv-user-verify"

    //身份证图片上传
    const val LIVER_CARD_UP = " /community/v1/live/upload"

    //认证审核结果
    const val LIVER_VERIFY_RESULT = "/community/v1/live/tv-user-verify-status"

    //用户页背景修改上传
    const val UP_USER_INFO_BG = "/community/v1/live/background-img-upload"

    //用户简介修改
    const val MODIFY_USER_INTRO = "/community/v1/live/edit-intro"

    //直播预约
    const val LIVE_APPOINTMENT = "/community/v1/live/reserve"

    //人员基本信息
    const val LIVE_USER_INFO = "/community/v1/live/user-center-info"

    //用户粉丝列表
    const val LIVE_USER_FANS = "/community/v1/live/fans-user-list"

    //用户关注列表
    const val LIVE_USER_FOLLOW = "/community/v1/live/follow-user-list"

    //用户主页直播列表
    const val LIVE_USER_LIVE_LIST = "/community/v1/live/user-center-live-list"

    //用户消息列表
    const val USER_MSG_LIST = "/community/v1/private-msg/msg-list"


    const val USER_MSG_RECORD = "/community/v1/private-msg/msg-record"

    const val USER_SEND_MSG = "/community/v1/private-msg/send"

    const val USER_PROFILE = "community/v1/live/income-list"

    const val USER_LETTER_RED = "/community/v1/private-msg/have-read"
    const val LIKE_VIDEO = "/community/v1/live/user-like-live-list"

    //领取优惠券
    const val GET_LIVE_ROOM_COUPONS = "/coupon/receive"

    //增加库存
    const val ADD_STOCK = "shop-test/stock"

    //首页轮播图数据
    const val GET_BANNER_LIST = "ad/list"

    //离开直播间
    const val GO_ANAY_LIVE = "community/v1/live/leave-live"

}