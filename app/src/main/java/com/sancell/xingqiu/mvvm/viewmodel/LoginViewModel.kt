package com.sancell.xingqiu.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sancell.xingqiu.base.BaseApplication
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.constants.utils.AppUtils
import com.sancell.xingqiu.constants.ConstantsKey
import com.sancell.xingqiu.constants.utils.PreferencesUtils
import com.sancell.xingqiu.constants.utils.RSAUtils
import com.sancell.xingqiu.entity.base.DefalutResultInfo
import com.sancell.xingqiu.entity.login.LoginResultData
import com.sancell.xingqiu.entity.login.StartupResult
import com.sancell.xingqiu.mvvm.repository.LoginRepository
import com.sancell.xingqiu.utils.StringCheckUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by zj on 2020/5/28.
 * 登录model
 */
class LoginViewModel : BaseViewModel() {
    val mLoginRepository by lazy { LoginRepository() }
    val mCodeResult: MutableLiveData<DefalutResultInfo> = MutableLiveData()
    val mCodeLogin: MutableLiveData<LoginResultData> = MutableLiveData()
    val mStartUp: MutableLiveData<StartupResult> = MutableLiveData()

    //获取验证码
    fun getCode(mobile: String) {
        startLoadView()
        val params = HashMap<String, String>()
        params["mobile"] = mobile
        params["skey"] = PreferencesUtils.getString(ConstantsKey.KEY_SKEY, "")
        params["reqCacheType"] = "10003"
        params["reqTime"] = StringCheckUtils.getCurrentTime()
        params["hashToken"] = RSAUtils.encryptByPublic(params)
        launch {
            val respons = withContext(Dispatchers.IO) {
                mLoginRepository.getCode(params)
            }
            executeResponse(
                respons,
                {
                    endLoadView()
                    mCodeResult.value = respons.retData
                },
                {
                    endLoadView()
                    errMsg.value = respons.retMsg
                })
        }
    }

    /**
     * 短信验证码登录
     */
    fun codeLogin(mobile: String, smsCode: String) {
        startLoadView()
        val params = HashMap<String, String>()
        val imei: String = AppUtils.getDeviceId(BaseApplication.getInstance().applicationContext)
        params["mobile"] = mobile
        params["smsCode"] = smsCode
        params["skey"] = PreferencesUtils.getString(ConstantsKey.KEY_SKEY, RSAUtils.md5(imei))
        params["clientId"] = "3"
        params["imei"] = imei
        params["reqTime"] = StringCheckUtils.getCurrentTime()
        params["hashToken"] = RSAUtils.encryptByPublic(params)

        launch {
            val respons = withContext(Dispatchers.IO) {
                mLoginRepository.codeLogin(params)
            }
            executeResponse(
                respons,
                {
                    mCodeLogin.value = respons.retData
                    endLoadView()
                },
                {
                    errMsg.value = respons.retMsg
                    endLoadView()
                })
        }
    }

    //检查登录
    fun startUp() {
        val params = HashMap<String, String>()
        val imei: String = AppUtils.getDeviceId(BaseApplication.getInstance().applicationContext)
        params["version"] =
            AppUtils.getVersionName(BaseApplication.getInstance().applicationContext)
        params["skey"] = PreferencesUtils.getString(ConstantsKey.KEY_SKEY, RSAUtils.md5(imei))
        params["clientId"] = "3"
        params["imei"] = imei
        params["reqTime"] = StringCheckUtils.getCurrentTime()
        params["hashToken"] = RSAUtils.encryptByPublic(params)

        launch {
            val respons = withContext(Dispatchers.IO) {
                mLoginRepository.startUp(params)
            }
            executeResponse(
                respons,
                {
                    mStartUp.value = respons.retData
                },
                {
                    errMsg.value = respons.retMsg
                })
        }
    }
}