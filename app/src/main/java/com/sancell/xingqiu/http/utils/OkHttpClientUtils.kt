package com.sancell.xingqiu.http.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by zj on 2020/5/19.
 */
object OkHttpClientUtils {
    private var mOkHttpClient: OkHttpClient? = null
    const val TIMEOUT = 60L
    /**
     * 全局httpclient
     *
     * @return
     */
    fun initOKHttp(): OkHttpClient? {
        if (mOkHttpClient == null) {
          mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS) //设置连接超时时间
                .readTimeout(TIMEOUT, TimeUnit.SECONDS) //设置读取超时时间
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS) //设置写入超时时间
                .retryOnConnectionFailure(true) //添加日志拦截器
                //cookie
                //.addInterceptor(new HttpInterceptor(AppUtils.getVersionName(SCApp.context)))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
        return mOkHttpClient
    }


}