package com.zhlw.component.login.viewmodel

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.zhlw.module.base.ui.viewmodel.BaseViewModel
import com.zhlw.module.base.utils.SingleLiveEvent
import com.zhlw.module.common.utils.CCUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : BaseViewModel(){

    val loadStartCode = 2

    val loadSuccessCode = 0

    //0 成功 <0 失败 >0 业务逻辑失败 2 开始加载
    val loginEvent : SingleLiveEvent<Int>  = SingleLiveEvent()

    fun login(context :Context,userName : String?){
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(context, "用户名为空", Toast.LENGTH_SHORT).show()
        } else {
            loginEvent.value = loadStartCode
            val cc = CCUtils.loginCC(context,userName!!)
            cc.callAsyncCallbackOnMainThread { _, result ->
                val loginResult = result.code
                loginEvent.value = loginResult
            }
        }
    }

}