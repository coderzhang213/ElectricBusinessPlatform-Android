package com.sancell.xingqiu.base.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sancell.xingqiu.base.viewmodel.BaseViewModel
import com.sancell.xingqiu.constants.OnLoadLinsener
import com.sancell.xingqiu.enump.LoadType

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), OnLoadLinsener {
    protected lateinit var mViewModel: VM
    protected var mRoot: View? = null
    protected var mInflater: LayoutInflater? = null
    protected var mFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initVM()
        initView()
        initData()
        startObserve()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRoot = inflater.inflate(getLayoutResId(), null)
        return mRoot
    }

    fun findByView(rId: Int): View {
        return mRoot!!.findViewById(rId)
    }

    override fun onEndLoadView(loadType: LoadType) {

    }

    override fun onStartLoadView(loadType: LoadType) {
    }

    open fun startObserve() {}

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    fun onRestartInstance(bundle: Bundle) {

    }

    override fun onResume() {
        super.onResume()
        Log.i("ActivityManage:", this.javaClass.name) // 打印出每个activity的类名


    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel.bindOnLoadLinsener(this)
            mViewModel.let(lifecycle::addObserver)
        }
    }

    open fun providerVMClass(): Class<VM>? = null

    protected fun startActivity(z: Class<*>) {
        startActivity(Intent(activity, z))
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
    }

    /**
     * 添加
     *
     * @param frameLayoutId
     * @param fragment
     */
    protected open fun addFragment(frameLayoutId: Int, fragment: Fragment?) {
        if (fragment != null) {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
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

}