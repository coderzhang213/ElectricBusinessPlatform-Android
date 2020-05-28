package com.sancell.xingqiu.view.login.fragment

import android.content.Context
import android.graphics.Paint
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.view.BaseDataFragmentKt
import com.sancell.xingqiu.constants.UserManager
import com.sancell.xingqiu.help.CodeTextWatcher
import com.sancell.xingqiu.help.NumberTextWatcher
import com.sancell.xingqiu.help.TimeCount
import com.sancell.xingqiu.help.ToastHelper
import com.sancell.xingqiu.mvvm.viewmodel.LoginViewModel
import com.sancell.xingqiu.utils.StringCheckUtils
import kotlinx.android.synthetic.main.activity_code_login_layout.*
import kotlinx.android.synthetic.main.layout_phone_code.*

/**
 * Created by zj on 2020/5/28.
 */
class CodeLoginFragment : BaseDataFragmentKt<LoginViewModel>(), View.OnClickListener {
    var mTimeCount: TimeCount? = null
    override fun onReloadData() {
    }

    override val isLoadNotDat: Boolean
        get() = true
    override val isShowTitle: Boolean
        get() = false

    override fun providerVMClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.activity_code_login_layout
    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mException.observe(this@CodeLoginFragment, Observer {
                ToastHelper.showToast(it.message)
            })
            errMsg.observe(this@CodeLoginFragment, Observer {
                ToastHelper.showToast(it)
            })
            mCodeResult.observe(this@CodeLoginFragment, Observer {
                ed_code.requestFocus()
                mTimeCount?.start()
                btn_getcode.isClickable = false

                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(
                    ed_code,
                    InputMethodManager.SHOW_IMPLICIT
                )
                ToastHelper.showToast("验证码发送成功")

            })
            mCodeLogin.observe(this@CodeLoginFragment, Observer {
                //把用户信息缓存在本地
                UserManager.cacheLocalUserInfo(it)
            })
        }

    }

    override fun initView() {
        btn_privacy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG) //下划线
        btn_privacy.getPaint().setAntiAlias(true) //抗锯齿
        btn_protocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG) //下划线
        btn_protocol.getPaint().setAntiAlias(true) //抗锯齿
        ed_login_mobile.requestFocus()
        mTimeCount = TimeCount(context, 60000, 1000, btn_getcode)
        ed_login_mobile.addTextChangedListener(
            NumberTextWatcher(
                ed_login_mobile,
                ed_code,
                btn_getcode,
                iv_clear,
                btn_login,
                context
            )
        )
        ed_code.addTextChangedListener(
            CodeTextWatcher(
                ed_login_mobile,
                ed_code,
                btn_login,
                iv_clear_code,
                context
            )
        )

        btn_getcode.setOnClickListener(this)
        btn_login.setOnClickListener(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        mTimeCount?.apply {
            this.cancel()
            mTimeCount = null
        }
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_getcode -> {//发送验证码
                val phoneCode = ed_login_mobile.getText().toString().replace(" ", "")
                if (!StringCheckUtils.isMobile(phoneCode)) {
                    ToastHelper.showToast("手机号码格式不正确")
                    return
                }
                mViewModel.getCode(phoneCode)
            }
            R.id.btn_login -> {//短信登录
                val ed_codeStr = ed_code.text.toString().trim()
                val phoneCode = ed_login_mobile.getText().toString().replace(" ", "")

                if (TextUtils.isEmpty(ed_codeStr)) {
                    ToastHelper.showToast("请输入验证码")
                    return
                }
                if (TextUtils.isEmpty(phoneCode)) {
                    ToastHelper.showToast("手机号为空")
                    return
                }
                if (ed_codeStr.length < 6) {
                    ToastHelper.showToast("验证码格式不正确")
                    return
                }
                mViewModel.codeLogin(phoneCode, ed_codeStr)
            }
        }
    }


}