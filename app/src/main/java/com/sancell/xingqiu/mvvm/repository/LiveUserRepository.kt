package com.sancell.xingqiu.mvvm.repository

import com.sancell.xingqiu.base.viewmodel.BaseRepository
import com.sancell.xingqiu.entity.base.EmptyBean
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.entity.live.*
import com.sancell.xingqiu.entity.user.LiveParFollowInfo
import com.sancell.xingqiu.entity.user.ProfileRes
import com.sancell.xingqiu.entity.user.UserMsgRes

/**
 * 直播用户相关接口
 */
class LiveUserRepository : BaseRepository() {

    suspend fun getLiveFocus(params: Map<String, String>): ResResponse<LiveFocusRes> {
        return mServe.liveFocus(params)
    }

    suspend fun liveVerify(params: Map<String, String>): ResResponse<VerifyRes> {
        return mServe.liverVerify(params)
    }

    suspend fun getLiverVerifyResult(params: Map<String, String>): ResResponse<VerifyResultRes> {
        return mServe.liverVerifyResult(params)
    }

    suspend fun upImage(params: Map<String, String>): ResResponse<UpIdCardBean> {
        return mServe.liverUpCard(params)
    }

    suspend fun upImageBg(params: Map<String, String>): ResResponse<UpLoadPhotoInfoBean> {
        return mServe.liverUpBg(params)
    }

    suspend fun modifyUserIntro(params: Map<String, String>): ResResponse<EmptyBean> {
        return mServe.modifyUserIntro(params)
    }

    suspend fun liveAppointment(params: Map<String, String>): ResResponse<EmptyBean> {
        return mServe.liveAppointment(params)
    }

    suspend fun getUserInfo(params: Map<String, String>): ResResponse<LiveUserInfoBean> {
        return mServe.getUserInfo(params)
    }

    suspend fun getUserFans(params: Map<String, String>): ResResponse<FansRes> {
        return mServe.getUserFans(params)
    }

    suspend fun getUserFollow(params: Map<String, String>): ResResponse<FansRes> {
        return mServe.getUserFollow(params)
    }

    suspend fun getUserLiveList(params: Map<String, String>): ResResponse<LiveParFollowInfo> {
        return mServe.getUserLiveList(params)
    }

    suspend fun getMsgList(params: Map<String, String>): ResResponse<UserMsgRes> {
        return mServe.getUserMsgList(params)
    }

    suspend fun getMsgRecord(params: Map<String, String>): ResResponse<UserMsgRes> {
        return mServe.getUserMsgRecordList(params)
    }

    suspend fun sendMsg(params: Map<String, String>): ResResponse<EmptyBean> {
        return mServe.sendUserMsg(params)
    }

    suspend fun getProfile(params: Map<String, String>): ResResponse<ProfileRes> {
        return mServe.getUserProfile(params)
    }

    suspend fun readMsg(params: Map<String, String>): ResResponse<EmptyBean> {
        return mServe.readMsg(params)
    }

    suspend fun getUserLikeVideo(params: Map<String, String>): ResResponse<LiveParFollowInfo> {
        return mServe.getUserlikeVideo(params)
    }


}