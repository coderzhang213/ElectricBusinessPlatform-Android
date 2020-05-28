package com.sancell.xingqiu.base.viewmodel


import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sancell.xingqiu.constants.OnLoadLinsener
import com.sancell.xingqiu.entity.base.ResResponse
import com.sancell.xingqiu.enump.LoadType
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel(), LifecycleObserver {
    var mOnLoadLinsener: OnLoadLinsener? = null
    val mException: MutableLiveData<Exception> = MutableLiveData()

    val errMsg: MutableLiveData<String> = MutableLiveData()
    override fun onCleared() {
        super.onCleared()
        Log.i("keey", "onCleared")
        mOnLoadLinsener = null
    }

    /**
     * 设置一个加载框的回调
     */
    fun bindOnLoadLinsener(mOnLoadLinsener: OnLoadLinsener) {
        this.mOnLoadLinsener = mOnLoadLinsener
    }

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
        // GlobalScope.launch(Dispatchers.Main) { block() }
    }

    fun startLoadView() {
        startLoadView(LoadType.DIALOG_LOAD)
    }

    fun startLoadView(loadType: LoadType) {
        mOnLoadLinsener?.onStartLoadView(loadType)
    }

    fun endLoadView(loadType: LoadType) {
        mOnLoadLinsener?.onEndLoadView(loadType)
    }

    fun endLoadView() {
        endLoadView(LoadType.DIALOG_LOAD)
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }


    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                endLoadView(LoadType.DEFAULT_LOAD)
                endLoadView(LoadType.DIALOG_LOAD)

                Log.i("keey", "eeee:" + e.message)
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    //  ToastHelper.showToast("网络异常")
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun executeResponse(
        response: ResResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
        errorBlock: suspend CoroutineScope.() -> Unit
    ) {

        coroutineScope {
            if (response.retCode != 0) errorBlock()
            else successBlock()
        }
    }

}
