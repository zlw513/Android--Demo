package com.zhlw.component.login

import android.content.Intent
import android.util.Log
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.zhlw.component.login.ui.LoginActivity
import com.zhlw.module.common.constant.RouteConstant

class LoginComponent : IComponent {

    override fun getName(): String = RouteConstant.LOGIN_COMPONENT

    override fun onCall(cc: CC?): Boolean {
        val actionName = cc?.actionName
        Log.d(RouteConstant.LOGIN_COMPONENT, "actionName = $actionName")
        when (cc?.actionName) {
            RouteConstant.ACTION_TO_LOGIN_ACTIVITY -> {
                val activityIntent = Intent(cc.context, LoginActivity::class.java)
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activityIntent.putExtra(RouteConstant.PARAM_LOGIN_ACTIVITY,cc.callId)
                cc.context.startActivity(activityIntent)
                return true
            }
            else -> CC.sendCCResult(cc?.callId, CCResult.errorUnsupportedActionName())
        }
        return false
    }


}