package com.zhlw.component.explore

import android.util.Log
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.zhlw.component.explore.network.ExploreEngine
import com.zhlw.component.explore.network.TrendingEngine
import com.zhlw.component.explore.ui.fragment.ExploreFragment
import com.zhlw.module.common.constant.RouteConstant
import com.zhlw.module.common.constant.RouteConstant.PARAM_EXPLORE_URL
import com.zhlw.module.common.constant.RouteConstant.PARAM_TRENDING_URL
import com.zhlw.module.common.network.crawler.ParseEngine

/**
 * @Desc:
 * @Author: zlw
 * @Date: 2022/5/12
 */
class ExploreComponent : IComponent{

    override fun getName() = RouteConstant.EXPLORE_COMPONENT

    override fun onCall(cc: CC?): Boolean {
        val actionName = cc?.actionName
        Log.d(RouteConstant.EXPLORE_COMPONENT, "actionName = $actionName")
        when (cc?.actionName) {
            RouteConstant.GET_EXPLOREFRAGMENT -> {
                return getExploreFragment(cc)
            }
            RouteConstant.GET_TRENDING_DATA -> {
                return getTrendingDataRemote(cc)
            }
            RouteConstant.GET_EXPLORE_DATA -> {
                return getExploreDataRemote(cc)
            }
            else -> CC.sendCCResult(cc?.callId, CCResult.errorUnsupportedActionName())
        }
        return false
    }

    private fun getExploreFragment(cc: CC) : Boolean{
        CC.sendCCResult(cc.callId, CCResult.success(RouteConstant.KEY_EXPLOREFRAGMENT, ExploreFragment.newInstance()))
        return false
    }

    private fun getTrendingDataRemote(cc: CC) : Boolean{
        val parseEngine = ParseEngine(TrendingEngine(cc.callId))
        parseEngine.init()
        val url = cc.getParamItem<String>(PARAM_TRENDING_URL)
        parseEngine.startParse(url)
        return true
    }

    private fun getExploreDataRemote(cc: CC) : Boolean{
        val parseEngine = ParseEngine(ExploreEngine(cc.callId))
        parseEngine.init()
        val url = cc.getParamItem<String>(PARAM_EXPLORE_URL)
        parseEngine.startParse(url)
        return true
    }

}