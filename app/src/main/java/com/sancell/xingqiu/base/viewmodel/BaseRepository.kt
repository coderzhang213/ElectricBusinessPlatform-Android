package handbank.hbwallet

import com.sancell.xingqiu.http.utils.RetrofitUtil


open class BaseRepository {
    val mServe by lazy {  RetrofitUtil.getInstance().initRetrofit() }
    suspend fun <T : Any> apiCall(call: suspend () -> ResResponse<T>): ResResponse<T> {
        return call.invoke()
    }

}