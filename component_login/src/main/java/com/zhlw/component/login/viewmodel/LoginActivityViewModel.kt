package com.zhlw.component.login.viewmodel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.zhlw.module.base.ui.viewmodel.BaseViewModel
import com.zhlw.module.base.utils.SingleLiveEvent
import com.zhlw.module.common.utils.CCUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : BaseViewModel(){

    private val TAG = "LoginActivityViewModel"

    val loadStartCode = 2

    val loadSuccessCode = 0

    //0 成功 <0 失败 >0 业务逻辑失败 2 开始加载
    val loginEvent : SingleLiveEvent<Int>  = SingleLiveEvent()

    fun login(context :Context,userName : String?){
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(context, "用户名为空", Toast.LENGTH_SHORT).show()
        } else {
            loginEvent.value = loadStartCode
            val cc = CCUtils.loginCC(userName!!)
            cc.callAsyncCallbackOnMainThread { _, result ->
                val loginResult = result.code
                loginEvent.value = loginResult
            }
        }
    }

    fun sendLoginResult(callId : String?,isLoginSuccess : Boolean){
        if (!TextUtils.isEmpty(callId)){
            if (isLoginSuccess){
                CC.sendCCResult(callId, CCResult.success())
            } else {
                CC.sendCCResult(callId, CCResult.error("用户未登录"))
            }
        } else {
            CC.sendCCResult(callId, CCResult.error("登录失败 callId 为空"))
        }
    }

}