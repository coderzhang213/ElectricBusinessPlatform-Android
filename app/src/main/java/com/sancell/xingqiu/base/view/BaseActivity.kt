package com.sancell.xingqiu.base.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cn.sancell.xingqiu.util.observer.ObserverKey
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.constants.OnLoadLinsener
import com.sancell.xingqiu.constants.network.NetWorkMonitorManager
import com.sancell.xingqiu.constants.network.NetWorkState
import com.sancell.xingqiu.constants.network.onNetWorkStateChangeLinsener
import com.sancell.xingqiu.constants.observer.ObserverManger
import com.sancell.xingqiu.constants.observer.OnObserver
import com.sancell.xingqiu.dialog.ComfirmDialog
import com.sancell.xingqiu.enump.LoadType
import kotlinx.android.synthetic.main.toolbar_base.*

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(),
    onNetWorkStateChangeLinsener , OnLoadLinsener {
    protected lateinit var mViewModel: VM
    protected var mFragment: Fragment? = null
    private var alterDialog: ComfirmDialog? = null
    var isSetView = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSetView) {
            setContentView(getLayoutResId())
        }
        setWindeMode()
        NetWorkMonitorManager.getInstance().register(this)
        ObserverManger.getInstance(ObserverKey.SERVER_ERROR).registerObserver(mServerEorrorObserver)
        initVM()
        initView()
        setSupportActionBar(mToolbar)
        initData()
        startObserve()
        lifecycle
    }

    override fun onEndLoadView(loadType: LoadType) {
    }

    override fun onStartLoadView(loadType: LoadType) {
    }
    //用来设置windows的属性
    open fun setWindeMode() {

    }

    open fun startObserve() {}

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel.bindOnLoadLinsener(this)
            mViewModel.let(lifecycle::addObserver)
        }
    }

    override fun onNetWorkStateChangeLinener(netWorkState: NetWorkState?) {
        if (netWorkState == NetWorkState.NONE) {
            if (alterDialog == null) {
                alterDialog = NetWorkMonitorManager.getInstance().getAlterDialog(this)
            }
            if (!alterDialog!!.isShowing) {
                alterDialog?.show()
            }
        } else {
            if (alterDialog != null && alterDialog!!.isShowing) {
                alterDialog?.dismiss()
            }

        }
    }

    private val mServerEorrorObserver = OnObserver {
        //
    }
    override fun onResume() {
        super.onResume()
        Log.i("ActivityManage:", this.javaClass.name) // 打印出每个activity的类名

    }

    /**
     * 添加
     *
     * @param frameLayoutId
     * @param fragment
     */
    protected open fun addFragment(frameLayoutId: Int, fragment: Fragment?) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment.isAdded) {
                if (mFragment != null) {
                    transaction.hide(mFragment!!).show(fragment)
                } else {
                    transaction.show(fragment)
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment!!).add(frameLayoutId, fragment)
                } else {
                    transaction.add(frameLayoutId, fragment)
                }
            }
            mFragment = fragment
            transaction.commit()
        }
    }

    open fun providerVMClass(): Class<VM>? = null

    protected fun startActivity(z: Class<*>) {
        startActivity(Intent(this, z))
    }

    protected fun showProgressDialog(content: String) {

    }

    protected fun showProgressDialog(resId: Int) {

    }

    protected fun dismissProgressDialog() {
    }

    override fun onDestroy() {
        if (::mViewModel.isInitialized) {
            mViewModel.let {
                lifecycle.removeObserver(it)
            }
        }
        super.onDestroy()
        NetWorkMonitorManager.getInstance().unregister(this)
        ObserverManger.getInstance(ObserverKey.SERVER_ERROR).removeObserver(mServerEorrorObserver)
    }
}