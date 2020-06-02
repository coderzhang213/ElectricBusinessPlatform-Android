package com.sancell.xingqiu.constants

import com.sancell.xingqiu.BuildConfig

/**
 * Created by zj on 2020/5/19.
 */
object Constants {

    const val HOST_RELEASE_RDC = "http://devm.sanshaoxingqiu.cn/" //研发环境

    const val HOST_RELEASE = "https://mapi.sanshaoxingqiu.cn/" // 正式环境

    const val HOST_RELEASE_TEST = "http://testmapi1.sanshaoxingqiu.cn/" // 测试环境

    const val HOST_RELEASE_TEST_RDC = "http://testmapi.sancell.top/" // 测试环境

    const val HOST_PRE = "https://premapi.sanshaoxingqiu.cn/" //等保环境

    const val PRE_HOST_RELEASE = "https://mapi3.sanshaoxingqiu.cn/" //预生产

    const val PRE_HOST_MIN = "https://alanmapi.sanshaoxingqiu.cn/" //研发资金电脑

    /**
     * 获取请求地址
     */
    fun getHttpHost(): String {
        when (BuildConfig.ENVIRONMENT) {
            "off" -> {
                return HOST_RELEASE
            }
            "test" -> {
                return HOST_RELEASE_TEST
            }
            "rdc" -> {
                return HOST_RELEASE_RDC
            }

            "pre" -> {
                return HOST_PRE
            }
            "rp" -> {
                return HOST_RELEASE_TEST_RDC
            }
            "pre_re" -> {
                return PRE_HOST_RELEASE
            }
            "min" -> {
                return PRE_HOST_MIN
            }
        }
        return HOST_RELEASE
    }
}