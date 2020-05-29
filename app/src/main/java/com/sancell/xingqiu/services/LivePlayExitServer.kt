package com.sancell.xingqiu.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import cn.sancell.xingqiu.util.observer.ObserverKey
import com.sancell.xingqiu.constants.Constants
import com.sancell.xingqiu.constants.ConstantsHttpUrl
import com.sancell.xingqiu.constants.ConstantsKey
import com.sancell.xingqiu.constants.observer.ObserverManger
import com.sancell.xingqiu.constants.observer.OnObserver
import com.sancell.xingqiu.constants.utils.ConvertUtils
import com.sancell.xingqiu.constants.utils.PreferencesUtils
import com.sancell.xingqiu.help.ToastHelper
import com.sancell.xingqiu.http.utils.OkHttpClientUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LivePlayExitServer : Service() {
    companion object {
        const val IS_LIVE_ROOM_IEXT = "is_live_room_exit"
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ObserverManger.getInstance(ObserverKey.LIVE_UP_PLAAY_EXIT).registerObserver(livePlayExitOberver)
        ObserverManger.getInstance(ObserverKey.GO_AWAY_LIVE).registerObserver(livePlayGoAnayOberver)
        intent?.apply {
            val isExit = getBooleanExtra(IS_LIVE_ROOM_IEXT, false)
            if (isExit) {//结束直播间
                exitLiveRoom()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        ObserverManger.getInstance(ObserverKey.LIVE_UP_PLAAY_EXIT).removeObserver(livePlayExitOberver)
        ObserverManger.getInstance(ObserverKey.GO_AWAY_LIVE).removeObserver(livePlayGoAnayOberver)
    }

    private val livePlayGoAnayOberver = OnObserver {
        val mOkHttpClient = OkHttpClientUtils.initOKHttp()
        it?.apply {
            if (this is String) {
                val batchId = this

                val par = ConvertUtils.getRequest()
                par["batchId"] = batchId
                val formBody = ConvertUtils.toHttpBuild(par)

                val request: Request = Request.Builder()
                        .url(Constants.HOST_RELEASE + ConstantsHttpUrl.GO_ANAY_LIVE)
                        .post(formBody)
                        .build()
                //3.创建一个call对象,参数就是Request请求对象
                val call = mOkHttpClient!!.newCall(request)
                call.enqueue(object : Callback {
                    override fun onFailure(p0: Call, p1: IOException) {
                        // showTosh("直播退出失败:" + p1.message)
                        Log.i("keey", "直播离开失败:" + p1.message)
                    }

                    override fun onResponse(p0: Call, p1: Response) {
                        //  showTosh("直播退出成功:")
                        Log.i("keey", "直播离开成功")
                    }
                })
            }


        }

    }

    /**
     * 退出直播间
     */
    fun exitLiveRoom() {
        val mOkHttpClient = OkHttpClientUtils.initOKHttp()
        val account = PreferencesUtils.getString(ConstantsKey.key_im_accid, "")
        val par = ConvertUtils.getRequest()
        par["liveStatus"] = "3"
        par["accid"] = account
        val formBody = ConvertUtils.toHttpBuild(par)


        val request: Request = Request.Builder()
                .url(Constants.HOST_RELEASE  + ConstantsHttpUrl.UP_ROOM_STATES)
                .post(formBody)
                .build()

        //3.创建一个call对象,参数就是Request请求对象
        val call = mOkHttpClient!!.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(p0: Call, p1: IOException) {
                // showTosh("直播退出失败:" + p1.message)
                Log.i("keey", "直播退出失败:" + p1.message)
            }

            override fun onResponse(p0: Call, p1: Response) {
                //  showTosh("直播退出成功:")
                Log.i("keey", "直播退出成功")
            }
        })

    }    //用来监听状态

    private val livePlayExitOberver = OnObserver {
        exitLiveRoom()

    }

    fun showTosh(msg: String) {
        val handel = Handler(Looper.getMainLooper())
        handel.post {
            ToastHelper.showToast(msg)

        }
    }
}