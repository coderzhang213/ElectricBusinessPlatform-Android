package com.sancell.xingqiu.http.server

import com.sancell.xingqiu.constants.ConstantsHttpUrl
import com.sancell.xingqiu.entity.banner.HomeBannerDataBean
import com.sancell.xingqiu.entity.base.DefalutResultInfo
import com.sancell.xingqiu.entity.base.EmptyBean
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.entity.live.*
import com.sancell.xingqiu.entity.user.LiveParFollowInfo
import com.sancell.xingqiu.entity.login.LoginResultData
import com.sancell.xingqiu.entity.login.StartupResult
import com.sancell.xingqiu.entity.user.ProfileRes
import com.sancell.xingqiu.entity.user.UserMsgRes
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by zj on 2020/5/28.
 */
interface ApiService {
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.START_UP)
    suspend fun startUp(@FieldMap maps: Map<String, String>): ResResponse<StartupResult>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_CODE)
    suspend fun getCode(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    //短信验证码登录
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_CODE_LOGIN)
    suspend fun codeLogin(@FieldMap maps: Map<String, String>): ResResponse<LoginResultData>

    /**
     * 直播列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.IM_LIVING_LIST)
    suspend fun getLiveList(@FieldMap params: Map<String, String>): ResResponse<LiveData>

    /**
     * 直播登录
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_LOGIN)
    suspend fun liveLogin(@FieldMap maps: Map<String, String>): ResResponse<LiveLoginInfo>

    /**
     * 直播登录
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.UP_ROOM_STATES)
    suspend fun upRoomStatus(@FieldMap maps: Map<String, String>): ResResponse<LiveRoomUpInfo>

    /**
     * 直播推荐列表
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_V1_LIST)
    suspend fun getLiveV1PlayList(@FieldMap maps: Map<String, String>): ResResponse<RecomLiveParInfo>

    /**
     * 获取直播详情
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_LIVE_INF)
    suspend fun getLivePlayInfo(@FieldMap maps: Map<String, String>): ResResponse<LivePlayInfo>

    /**
     * 直播关注
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_UP_FOLLOW)
    suspend fun upLiveFollow(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    /**
     * 获取直播人数
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_LIVE_SUMS)
    suspend fun getLiveSums(@FieldMap maps: Map<String, String>): ResResponse<LiveCountInfo>


    //直播点赞
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_GIVE_LINK)
    suspend fun upLiveGilveLink(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    //直播打赏
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_READ_LINE)
    suspend fun readPackLink(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>


    //检查红包交易密码
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.IM_CHECK_PAY_PASS)
    suspend fun checkPayPass(@FieldMap maps: Map<String, String>): ResResponse<IsSetPassInfo>


    //直播关注列表
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_FOLLEW_LIST)
    suspend fun getLiveFollowList(@FieldMap maps: Map<String, String>): ResResponse<HomeLiveListInfo>

    //直播推荐
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVEW_RECOMMEND_LIST)
    suspend fun getLiveRecommendList(@FieldMap maps: Map<String, String>): ResResponse<LiveRemmParInfo>

    //直播间初始化
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_SET_INIT)
    suspend fun initLiveSet(@FieldMap maps: Map<String, String>): ResResponse<LiveInitData>

    //获取直播关联商品
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_GET_COMMINT)
    suspend fun getLiveCommentList(@FieldMap maps: Map<String, String>): ResResponse<LiveRelevPar>

    //直播添加商品和群组
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.ADD_REMM_AND_GROUP)
    suspend fun addLiveReemAndGroup(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.DELETE_REMM_AND_GROPU)
    suspend fun deleteReemAndGroup(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.UP_REMMODIT_NAME)
    suspend fun upRemmoidtyName(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.IMG_UP_LOAD)
    suspend fun imgUpLoadInfo(@FieldMap maps: Map<String, String>): ResResponse<UpLoadPhotoInfoBean>

    //直播设置保存
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_SET_SAVE)
    suspend fun liveSetSave(@FieldMap maps: Map<String, String>): ResResponse<LiveSetResultParInfo>

    //获取红包余额
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.IM_RP_YU_E)
    suspend fun getReadPackAccout(@FieldMap maps: Map<String, String>): ResResponse<YueRes>

    //检查是否有直播中的
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.CHECK_LIVE_STATUS)
    suspend fun checkLiveStatus(@FieldMap maps: Map<String, String>): ResResponse<LiveBaseStatusInfo>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.UO_LAST_DATA)
    suspend fun upLastLiveData(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.CHECK_LIVE_ROOM_STATUS)
    suspend fun checkLiveRoomStatus(@FieldMap maps: Map<String, String>): ResResponse<LiveRoomStatus>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.VOUCHER_CENTER)
    suspend fun getVoucherCenter(@FieldMap maps: Map<String, String>): ResResponse<VoucherCenterRes>

    //获取直播间优惠券
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_ROOM_COUPONS)
    suspend fun getLiveRoomCoup(@FieldMap maps: Map<String, String>): ResResponse<CouponInfo>

    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_RECOMMEND_LIVER)
    suspend fun getRecommendLiver(@FieldMap maps: Map<String, String>): ResResponse<HomeRecommendLiverBean>

    //直播预约
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_RESER)
    suspend fun upLiveRescer(@FieldMap maps: Map<String, String>): ResResponse<DefalutResultInfo>

    /**
     * 关注主播
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_UP_FOLLOW)
    suspend fun liveFocus(@FieldMap params: Map<String, String>): ResResponse<LiveFocusRes>

    /**
     * 主播认证
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVER_VERIFY)
    suspend fun liverVerify(@FieldMap params: Map<String, String>): ResResponse<VerifyRes>

    /**
     * 认证审核结果
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVER_VERIFY_RESULT)
    suspend fun liverVerifyResult(@FieldMap params: Map<String, String>): ResResponse<VerifyResultRes>

    /**
     * 身份证上传
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVER_CARD_UP)
    suspend fun liverUpCard(@FieldMap params: Map<String, String>): ResResponse<UpIdCardBean>

    /**
     * 身份证上传
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVER_CARD_UP)
    suspend fun liverUpBg(@FieldMap params: Map<String, String>): ResResponse<UpLoadPhotoInfoBean>

    /**
     * 修改用户简介
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.MODIFY_USER_INTRO)
    suspend fun modifyUserIntro(@FieldMap params: Map<String, String>): ResResponse<EmptyBean>

    /**
     * 直播预约
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_APPOINTMENT)
    suspend fun liveAppointment(@FieldMap params: Map<String, String>): ResResponse<EmptyBean>

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_USER_INFO)
    suspend fun getUserInfo(@FieldMap params: Map<String, String>): ResResponse<LiveUserInfoBean>
    /**
     * 我的粉丝
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_USER_FANS)
    suspend fun getUserFans(@FieldMap params: Map<String, String>): ResResponse<FansRes>
    /**
     * 我关注的
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_USER_FOLLOW)
    suspend fun getUserFollow(@FieldMap params: Map<String, String>): ResResponse<FansRes>
    /**
     * 用户直播列表
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIVE_USER_LIVE_LIST)
    suspend fun getUserLiveList(@FieldMap params: Map<String, String>): ResResponse<LiveParFollowInfo>
    /**
     * 用户消息列表
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.USER_MSG_LIST)
    suspend fun getUserMsgList(@FieldMap params: Map<String, String>): ResResponse<UserMsgRes>


    /**
     * 用户消息对话列表
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.USER_MSG_RECORD)
    suspend fun getUserMsgRecordList(@FieldMap params: Map<String, String>): ResResponse<UserMsgRes>

    /**
     * 发送消息
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.USER_SEND_MSG)
    suspend fun sendUserMsg(@FieldMap params: Map<String, String>): ResResponse<EmptyBean>

    /**
     * 用户收益
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.USER_PROFILE)
    suspend fun getUserProfile(@FieldMap params: Map<String, String>): ResResponse<ProfileRes>

    /**
     * 消息读取
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.USER_LETTER_RED)
    suspend fun readMsg(@FieldMap params: Map<String, String>): ResResponse<EmptyBean>

    //用户点赞的列表
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.LIKE_VIDEO)
    suspend fun getUserlikeVideo(@FieldMap maps: Map<String, String>): ResResponse<LiveParFollowInfo>
    //领取直播间优惠券
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_LIVE_ROOM_COUPONS)
    suspend fun receiveLiveRoomCoupon(@FieldMap maps: Map<String, String>):ResResponse<DefalutResultInfo>

    //领取直播间优惠券
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.ADD_STOCK)
    suspend fun addStock(@FieldMap maps: Map<String, String>):ResResponse<DefalutResultInfo>

    /**
     * 首页轮播图数据
     */
    @FormUrlEncoded
    @POST(ConstantsHttpUrl.GET_BANNER_LIST)
    suspend fun getHomeBannerData(@FieldMap maps: Map<String, String>): ResResponse<HomeBannerDataBean>


}