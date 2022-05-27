package com.zhlw.module.base.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhlw.module.base.data.IResponseResult
import com.zhlw.module.base.data.ResponseThrowable
import com.zhlw.module.base.network.NetworkExceptionHandler
import com.zhlw.module.base.utils.SingleLiveEvent
import kotlinx.coroutines.*

private typealias CompleteCallback = suspend CoroutineScope.() -> Unit
private typealias ErrorCallback = suspend CoroutineScope.(ResponseThrowable) -> Unit

abstract class BaseViewModel :ViewModel(){

    // loading页展示
    protected val _showLoading = MutableLiveData<Boolean>()
    val isShowLoading: LiveData<Boolean> = _showLoading

    // 失败页展示
    protected val _showError = MutableLiveData<ResponseThrowable>()
    val isShowError: LiveData<ResponseThrowable> = _showError

    // UI事件
    val uiLiveEvent by lazy { UILiveEvent() }

    private fun launchRequest(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    private fun CreateException(throwable: Throwable) = NetworkExceptionHandler.handleException(throwable)

    private suspend fun handleLaunch(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (throwable: Throwable) {
                error(CreateException(throwable))
            } finally {
                complete()
            }
        }
    }

    /**
     * 适用于 block中 可以发起很多请求
     * @param uiState 处理UI状态
     * @param block 请求块
     * @param error 异常块 可选，可以区分不同业务的异常
     */
    fun request(
        uiState: UIState = UIState(),
        block: suspend CoroutineScope.() -> Unit,
        error: (ErrorCallback)? = null
    ) = with(uiState){
        if (isShowLoadingView) _showLoading.value = true
        launchRequest {
            handleLaunch(
                block =
                {
                    withContext(Dispatchers.IO){
                        block()
                    }
                },
                error =
                {
                    withContext(Dispatchers.Main) {
                        if (isShowErrorToast) uiLiveEvent.showToastEvent.postValue("${it.code}:${it.errMsg}")
                        if (isShowErrorView) _showError.value = it
                        error?.invoke(this, it)
                    }
                },
                complete =
                {
                    withContext(Dispatchers.Main) {
                        if (isShowLoadingView) _showLoading.value = false
                    }
                }
            )
        }
    }


    /**
     * 适用于 1对1 请求情况。 即 block 中只有一个请求
     * uiState 请求是否需要自动附带加载状态ui
     * success 只返回成功的数据
     * error 必传
     */
    fun <T> request(
        uiState: UIState = UIState(),
        block: suspend CoroutineScope.() -> IResponseResult<T>,
        success: (T) -> Unit,
        error: (ErrorCallback),
        complete: (CompleteCallback)? = null
    ) = with(uiState) {
        if (isShowLoadingView) _showLoading.value = true
        launchRequest {
            handleLaunch(
                block =
                {
                    withContext(Dispatchers.IO) {
                        block().let {
                            if (it.isSuccess()) {
                                withContext(Dispatchers.Main){
                                    success(it.data()!!)
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    //注：请求成功的情况下,但是返回结果为null这个请求会被当做异常，出现在 error 的回调中，而不是success
                                    val exception = ResponseThrowable(it.stateCode()!!,it.stateInfo()!!,null)
                                    if (isShowErrorView) _showError.value = exception
                                    if (isShowErrorToast) uiLiveEvent.showToastEvent.postValue("${exception.code}:${exception.errMsg}")
                                    error.invoke(this, exception)
                                }
                            }
                        }
                    }
                },
                error =
                {
                    withContext(Dispatchers.Main) {
                        if (isShowErrorToast) uiLiveEvent.showToastEvent.postValue("${it.code}:${it.errMsg}")
                        if (isShowErrorView) _showError.value = it
                        error.invoke(this, it)
                    }
                },
                complete =
                {
                    withContext(Dispatchers.Main) {
                        if (isShowLoadingView) _showLoading.value = false
                        complete?.invoke(this)
                    }
                }
            )
        }
    }

    inner class UILiveEvent {
        val showToastEvent by lazy { SingleLiveEvent<String>() }
    }

}


/**
 * UI状态
 * @param isShowLoadingView 是否自动展示加载中页面
 * @param isShowErrorView 是否自动展示错误页面
 */
data class UIState(
    val isShowLoadingView: Boolean = false,
    val isShowErrorView: Boolean = false
) {
    val isShowErrorToast: Boolean = false
}