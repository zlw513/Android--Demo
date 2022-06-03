package com.zhlw.module.common.utils

import android.content.Context
import android.content.Intent
import com.billy.cc.core.component.CC
import com.zhlw.module.base.ui.fragment.BaseFragment
import com.zhlw.module.common.constant.PACKAGE_ID
import com.zhlw.module.common.constant.RouteConstant
import com.zhlw.module.common.constant.START_MAIN_ACTIVITY

object CCUtils {

    /**
     * param : callId
     */
    fun startLoginActivityForResult() =
        CC.obtainBuilder(RouteConstant.LOGIN_COMPONENT)
            .setActionName(RouteConstant.ACTION_TO_LOGIN_ACTIVITY)
            .build()

    fun loginCC(userName : String) : CC {
        return CC.obtainBuilder(RouteConstant.MINE_COMPONENT)
            .setActionName(RouteConstant.ACTION_LOGIN)
            .addParam(RouteConstant.KEY_USERNAME,userName)
            .build()
    }

    fun startMainActivity(context: Context){
        val intent = Intent(START_MAIN_ACTIVITY)
        intent.setPackage(PACKAGE_ID)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun getExploreFragment() : BaseFragment<*,*> {
        return CC.obtainBuilder(RouteConstant.EXPLORE_COMPONENT)
            .setActionName(RouteConstant.GET_EXPLOREFRAGMENT)
            .build()
            .call()
            .getDataItem(RouteConstant.KEY_EXPLOREFRAGMENT)
    }

    fun getMineFragment() : BaseFragment<*,*> {
        return CC.obtainBuilder(RouteConstant.MINE_COMPONENT)
            .setActionName(RouteConstant.GET_MINEFRAGMENT)
            .build()
            .call()
            .getDataItem(RouteConstant.KEY_MINEFRAGMENT)
    }

}