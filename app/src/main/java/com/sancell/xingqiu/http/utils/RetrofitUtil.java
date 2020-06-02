package com.sancell.xingqiu.http.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sancell.xingqiu.constants.Constants;
import com.sancell.xingqiu.http.server.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: liumingran.
 * @date: 2018/7/25
 * @description: retrofit请求工具类
 */

public class RetrofitUtil {
    /**
     * 超时时间
     */
    private static volatile RetrofitUtil mInstance;
    private ApiService allApi;

    /**
     * 单例封装
     *
     * @return
     */
    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化Retrofit
     */
    public ApiService initRetrofit() {
        return getServerRetrofit(Constants.INSTANCE.getHttpHost()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(ApiService.class);
    }

    private Retrofit.Builder getServerRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return new Retrofit.Builder()
                .client(OkHttpClientUtils.INSTANCE.initOKHttp())
                // 设置请求的域名
                .baseUrl(baseUrl)
                // 设置解析转换工厂
                .addConverterFactory(GsonConverterFactory.create(gson));
    }


}
