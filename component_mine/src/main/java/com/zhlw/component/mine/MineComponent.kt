package com.zhlw.component.mine

import android.text.TextUtils
import android.util.Log
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCResult.CODE_SUCCESS
import com.billy.cc.core.component.IComponent
import com.zhlw.component.mine.ui.fragment.MineFragment
import com.zhlw.module.common.constant.RouteConstant
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Desc:
 * @Author: zlw
 * @Date: 2022/5/12
 */
class MineComponent : IComponent{

    private val mScope : CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getName() = RouteConstant.MINE_COMPONENT

    override fun onCall(cc: CC?): Boolean {
        val actionName = cc?.actionName
        Log.d(RouteConstant.MINE_COMPONENT, "actionName = $actionName")
        when (cc?.actionName) {
            RouteConstant.ACTION_LOGIN -> {
                return login(cc)
            }
            RouteConstant.GET_MINEFRAGMENT -> {
                return getMineFragment(cc)
            }
            RouteConstant.GET_USER_ID -> {
                return getUserId(cc)
            }
            else -> CC.sendCCResult(cc?.callId, CCResult.errorUnsupportedActionName())
        }
        return false
    }

    private fun login(cc: CC) : Boolean{
        val mineLocalDataSource = EntryPointAccessors.fromApplication(cc.context, MineProvider::class.java).getMineLocalDataSource()
        val userName = cc.params[RouteConstant.KEY_USERNAME] as String
        mineLocalDataSource.storeUserName(userName)
        CC.sendCCResult(cc.callId, CCResult.successWithNoKey(CODE_SUCCESS))
        return true
    }

    private fun getMineFragment(cc: CC) : Boolean{
        CC.sendCCResult(cc.callId, CCResult.success(RouteConstant.KEY_MINEFRAGMENT, MineFragment.newInstance()))
        return false
    }

    private fun getUserId(cc: CC) : Boolean{
        val mineLocalDataSource = EntryPointAccessors.fromApplication(cc.context, MineProvider::class.java).getMineLocalDataSource()
        val mineRemoteDataSource = EntryPointAccessors.fromApplication(cc.context, MineProvider::class.java).getMineRemoteDataSource()
        val storeUserId = mineLocalDataSource.getUserId()
        if (storeUserId != 0L){
            CC.sendCCResult(cc.callId, CCResult.success(RouteConstant.KEY_USER_ID,storeUserId))
        } else {
            mScope.launch {
                try {
                    val userName = mineLocalDataSource.getUserName()
                    if (!TextUtils.isEmpty(userName)){
                        val userInfo = mineRemoteDataSource.fetchUserInfo(userName!!)
                        mineLocalDataSource.storeUserId(userInfo.id)
                        CC.sendCCResult(cc.callId, CCResult.success(RouteConstant.KEY_USER_ID, userInfo.id))
                    } else {
                        CC.sendCCResult(cc.callId, CCResult.error(RouteConstant.KEY_USER_ID,"error,当前无用户名，未登录"))
                    }
                } catch (throwable : Throwable){
                    CC.sendCCResult(cc.callId, CCResult.error(RouteConstant.KEY_USER_ID,throwable))
                }
            }
        }
        return true
    }

}